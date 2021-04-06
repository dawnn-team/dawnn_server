package org.dawnn.server.dao;

import org.dawnn.server.model.GeoLocation;
import org.dawnn.server.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends MongoRepository<Image, String> {

    List<Image> findByGeoLocation(GeoLocation location);

    List<Image> findByHWIDOrigin();

    Image findByuuid(UUID location);

    // Create Grid class
//    List<Image> findByGrid();

}
