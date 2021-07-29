package org.dawnn.server.api;

import org.dawnn.server.dao.ImageRepository;
import org.dawnn.server.dao.UserRepository;
import org.dawnn.server.model.Image;
import org.dawnn.server.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * Parse and save an Image from the data sent by the client.
     *
     * @param image json representation of {@link Image}.
     */
    @PostMapping(consumes = "application/json")
    public void addImage(@RequestBody Image image) {
        logger.info("Received image post.");
        imageRepository.save(image);

        // Since user and image properties are identical at the time of sending,
        // let's just update the user from the image image object.
        User user = new User(image.getAuthorHWID(), image.getLocation().getX(), image.getLocation().getY());
        updateUser(user);
    }

    /**
     * Request images according to user's location.
     *
     * @param user The user requesting images.
     */
    @PostMapping(consumes = "application/json", value = "request", produces = "application/json")
    public GeoResults<Image> requestImages(@RequestBody User user) {
        logger.info("Received image request.");

        GeoJsonPoint loc = user.getLocation();

        GeoResults<Image> images = imageRepository.findByLocationNear(new Point(loc.getX(), loc.getY()),
                new Distance(USER_VIEW_DISTANCE, Metrics.KILOMETERS));

        updateUser(user);
        // FIXME Lazy; increases runtime.
        var listImages = new ArrayList<Image>();
        for (GeoResult<Image> image : images) {
            listImages.add(image.getContent());
        }

        return images;
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
