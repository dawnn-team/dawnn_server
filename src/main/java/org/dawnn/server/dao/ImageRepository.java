package org.dawnn.server.dao;

import org.dawnn.server.model.Location;
import org.dawnn.server.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends MongoRepository<Image, String> {

    List<Image> findByLocation(Location location);

    List<Image> findByHWIDOrigin(String hwid);

    Image findByuuid(UUID location);

    // Create Grid class
//    List<Image> findByGrid();

}
