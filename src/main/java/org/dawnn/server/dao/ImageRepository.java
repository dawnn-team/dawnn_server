package org.dawnn.server.dao;

import org.dawnn.server.model.GeoLocation;
import org.dawnn.server.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends MongoRepository<Image, String> {

    Image findByuuid(UUID location);

    List<Image> findByLocation(GeoLocation location);

    // Create Grid class
//    List<Image> findByGrid();

    List<Image> findByHWIDOrigin();
}
