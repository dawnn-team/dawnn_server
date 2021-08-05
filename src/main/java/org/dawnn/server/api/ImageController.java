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
import java.math.BigInteger;
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
        // FIXME: Bad data error thrown.


        List<AnnotateImageRequest> requests = new ArrayList<>();

        byte[] decode = Base64.decodeBase64(image.getBase64());
        String binaryStr = new BigInteger(1, decode).toString(2);

        ByteString input = ByteString.copyFromUtf8(binaryStr);
        com.google.cloud.vision.v1.Image requestImage = com.google.cloud.vision.v1.Image.newBuilder().setContent(input).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build();
        requests.add(AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(requestImage).build());

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                SafeSearchAnnotation annotation = res.getSafeSearchAnnotation();
                logger.info(
                        "adult: %s%nmedical: %s%nspoofed: %s%nviolence: %s%nracy: %s%n",
                        annotation.getAdult(),
                        annotation.getMedical(),
                        annotation.getSpoof(),
                        annotation.getViolence(),
                        annotation.getRacy());
            }
        }

//        String response = FirebaseMessaging.getInstance().send(message);
//        logger.info("Sent test message, response was: " + response);

//        Message message = Message.builder().setNotification(Notification.builder().setTitle("Test Notification from the Java server!").build()).setTopic("dev").build();


        imageRepository.save(image);
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
}
