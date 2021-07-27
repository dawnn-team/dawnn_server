package org.dawnn.server.dao;

import org.dawnn.server.model.User;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.*;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    GeoResults<User> findByLocationNear(Point point, Distance distance);

    List<User> findByHWID(String HWID);

}
