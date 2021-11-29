package org.dawnn.server.api;

import com.google.api.client.util.Base64;
import com.google.cloud.vision.v1.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.protobuf.ByteString;
import org.dawnn.server.dao.ImageRepository;
import org.dawnn.server.dao.UserRepository;
import org.dawnn.server.model.Image;
import org.dawnn.server.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.dawnn.server.DawnnServerApplication.USER_VIEW_DISTANCE;

/**
 * API layer between between the client and the database.
 */
@RequestMapping("api/v1/image")
@RestController
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private CloudVisionTemplate cloudVisionTemplate;

    /**
     * Parse and save an Image from the data sent by the client.
     *
     * @param image json representation of {@link Image}.
     */
    @PostMapping(consumes = "application/json")
    public void addImage(@RequestBody Image image) throws FirebaseMessagingException, IOException {
        logger.info("Received image post.");

        // Before we save and make this image available to the public,
        // Let's run it through Google's SafeSearch Detection service
        if (!isExplicit(image)) {
            imageRepository.save(image);
        } else {
            // TODO: Flag user
        }

        // Since user and image properties are identical at the time of sending,
        // let's just update the user from the image object.
        User user = new User(image.getAuthorHWID(), image.getLocation().getX(), image.getLocation().getY());
        updateUser(user);
    }

    /**
     * Request images according to user's location.
     *
     * @param user The user requesting images.
     */
    @PostMapping(consumes = "application/json", value = "request", produces = "application/json")
    public Object[] requestImages(@RequestBody User user) {
        logger.info("Received image request.");

        GeoJsonPoint loc = user.getLocation();
        updateUser(user);

        return imageRepository.findByLocationNear(new Point(loc.getX(), loc.getY()),
                new Distance(USER_VIEW_DISTANCE, Metrics.KILOMETERS)).getContent().toArray();
    }

    /**
     * Update the user based on hwid. If a user with the same hwid exists, the existing user
     * is deleted, the new {@link User} is saved. Null safe.
     *
     * @param user The user to update.
     */
    private void updateUser(User user) {
        if (user == null) {
            return;
        }
        if (!userRepository.findByHWID(user.getHWID()).isEmpty()) {
            logger.info("Updating existing user.");
            // Delete existing record.
            userRepository.delete(userRepository.findByHWID(user.getHWID()).stream().findFirst().get());
        } else {
            logger.info("Adding new user.");
        }
        userRepository.save(user);
    }


    /**
     * Check an image for explicit content using Google's SafeSearch API.
     *
     * @param image The image which to check.
     * @return True if the image is explicit, false otherwise.
     * @throws IOException thrown if creation of {@link ImageAnnotatorClient} fails.
     */
    private boolean isExplicit(Image image) throws IOException {
        // FIXME: Bad data error thrown.

        // Create new list of requests - better for batch, even though we only do one image at a time...
        List<AnnotateImageRequest> requests = new ArrayList<>();

        // Convert from base64 to bytes
        byte[] decode = Base64.decodeBase64(image.getBase64());
        ByteString input = ByteString.copyFrom(decode);

        // Build image and feature request
        com.google.cloud.vision.v1.Image requestImage = com.google.cloud.vision.v1.Image.newBuilder().setContent(input).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build();

        requests.add(AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(requestImage).build());

        // Create the client for requests
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            // TODO Cache client, in order to avoid recreating for every request?

            // Annotate images and get responses
            List<AnnotateImageResponse> responses = client.batchAnnotateImages(requests).getResponsesList();
            for (AnnotateImageResponse response : responses) {
                if (response.hasError()) {
                    System.out.format("Error: %s%n", response.getError().getMessage());
                    return false;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                SafeSearchAnnotation annotation = response.getSafeSearchAnnotation();
                logger.info("adult: {} medical: {} spoofed: {} violence: {} racy: {}",
                        annotation.getAdult(),
                        annotation.getMedical(),
                        annotation.getSpoof(),
                        annotation.getViolence(),
                        annotation.getRacy());
                if (annotation.getAdultConfidence() > 0.95) {
                    return true;
                }
            }
        }


        return true;
    }
}
