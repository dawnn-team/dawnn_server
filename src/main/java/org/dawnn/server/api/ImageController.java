package org.dawnn.server.api;

import org.dawnn.server.dao.ImageRepository;
import org.dawnn.server.model.Image;
import org.dawnn.server.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API layer between between the client and the database
 */
@RequestMapping("api/v1/image")
@RestController
public class ImageController {

    private final ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Creates an Image from the data received from the client and adds it to the imageService
     *
     * @param image json used to create an Image
     */
    @PostMapping(consumes = "application/json")
    public void addImage(@RequestBody Image image) {
        imageService.addImage(image);
        imageRepository.save(image);
    }

    /**
     * Returns all Image objects in the database
     *
     * @return List<Image> A List of all Image objects contained in the database
     */
    @GetMapping
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

}
