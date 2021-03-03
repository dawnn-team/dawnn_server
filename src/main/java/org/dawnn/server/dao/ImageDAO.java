package org.dawnn.server.dao;

import org.dawnn.server.model.Image;

import java.util.List;

public interface ImageDAO {


    int insertImage(Image image);

    default int addImage(Image image) {
        return insertImage(image);
    }

    List<Image> selectAllImages();

}
