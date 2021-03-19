package org.dawnn.server.dao;

import org.dawnn.server.model.Image;

import java.util.List;

/**
 * Image Data Access Object accesses the Image from the server's database
 */
public interface ImageDAO {


    int insertImage(Image image);

    default int addImage(Image image) {
        return insertImage(image);
    }

    List<Image> selectAllImages();

}
