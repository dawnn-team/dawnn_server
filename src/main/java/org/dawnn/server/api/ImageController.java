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

/**
 * API layer between between the client and the database
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
     * Creates an Image from the data received from the client and saves it.
     *
     * @param image json used to create an Image
     */
    @PostMapping(consumes = "application/json")
    public void addImage(@RequestBody Image image) {
        logger.info("Received image post.");
        imageRepository.save(image);

        // This will have to be reconstructed from the data in image
        // soon.
        updateUser(image.getUser());
        userRepository.save(image.getUser());
    }

    /**
     * Request images according to user's location.
     *
     * @param user The user requesting images.
     */
    @PostMapping(consumes = "application/json", value = "request", produces = "application/json")
    public List<Image> requestImages(@RequestBody User user) {
        logger.info("Received image request.");

        GeoJsonPoint loc = user.getLocation();

        // TODO Use symbolic constant.
        GeoResults<Image> images = imageRepository.findByUser_LocationNear(new Point(loc.getX(), loc.getY()),
                new Distance(1, Metrics.KILOMETERS));

        updateUser(user);
        // FIXME Lazy; increases runtime.
        var listImages = new ArrayList<Image>();
        for (GeoResult<Image> image : images) {
            listImages.add(image.getContent());
        }

        return listImages;
    }

    /**
     * Update the user based on hwid. If a user with the same hwid exists, the data is deleted.
     * The new {@link User} is saved. Null safe.
     *
     * @param user The user to update.
     */
    private void updateUser(User user) {
        if (user == null) {
            return;
        }
        if (!userRepository.findByhwid(user.getHwid()).isEmpty()) {
            logger.info("Updating existing user.");
            // Delete existing record and write new data.
            userRepository.delete(userRepository.findByhwid(user.getHwid()).stream().findFirst().get());
        }
        userRepository.save(user);
    }
}
