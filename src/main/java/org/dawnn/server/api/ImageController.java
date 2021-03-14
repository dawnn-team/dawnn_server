package org.dawnn.server.api;

import org.dawnn.server.model.Image;
import org.dawnn.server.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/image")
@RestController
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(consumes = "application/json")
    public void addImage(@RequestBody Image image) {
        imageService.addImage(image);
    }

    @GetMapping
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

}
