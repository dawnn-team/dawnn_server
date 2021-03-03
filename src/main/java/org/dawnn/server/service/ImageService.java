package org.dawnn.server.service;

import org.dawnn.server.dao.ImageDAO;
import org.dawnn.server.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private final ImageDAO imageDAO;

    @Autowired
    public ImageService(@Qualifier("fakeDao") ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    public void addImage(Image image) {
        imageDAO.addImage(image);
    }

    // Purely for testing purposes -- not for deployment.
    public List<Image> getAllImages() {
        return imageDAO.selectAllImages();
    }
}
