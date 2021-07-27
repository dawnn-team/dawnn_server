package org.dawnn.server.dao;

import org.dawnn.server.model.Image;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {

    GeoResults<Image> findByLocationNear(Point point, Distance distance);

}
