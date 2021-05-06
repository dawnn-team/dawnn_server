package org.dawnn.server.api;

import org.dawnn.server.dao.ImageRepository;
import org.dawnn.server.model.Image;
import org.dawnn.server.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API layer between between the client and the database
 */
@RequestMapping("api/v1/image")
@RestController
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ImageRepository imageRepository;

    /**
     * Creates an Image from the data received from the client and adds it to the imageService
     *
     * @param image json used to create an Image
     */
    @PostMapping(consumes = "application/json")
    public void addImage(@RequestBody Image image) {
        logger.info("Received image post.");
        imageRepository.save(image);
    }

    /**
     * Request images according to user's location.
     *
     * @param user The user requesting images.
     */
    @PostMapping(consumes = "application/json", value = "request")
    public List<Image> requestImages(@RequestBody User user) {
        logger.info("Received image request.");
        List<Image> images = imageRepository.findByLocation(user.getLocation());
        for (Image image : images) {
            image.eraseHwid();
            image.eraseId();
        }

        return images;
    }
}
