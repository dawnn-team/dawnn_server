package org.dawnn.server.dao;

import org.dawnn.server.model.Image;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements ImageDAO to access the Image from the database
 */
@Repository("fakeDao")
public class FakeImageDataAccessService implements ImageDAO {

    // This is temporary
    private List<Image> DB = new ArrayList<>();

    @Override
    public int insertImage(Image image) {
        return 0;
    }

    @Override
    public int addImage(Image image) {
        DB.add(image);
        return 1;
    }

    @Override
    public List<Image> selectAllImages() {
        return DB;
    }
}
