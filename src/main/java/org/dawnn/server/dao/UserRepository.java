package org.dawnn.server.dao;

import org.dawnn.server.model.GeoLocation;
import org.dawnn.server.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByGeoLocation(GeoLocation location);

    List<User> findByHWID(String HWID);

}
