package org.dawnn.server.dao;

import org.dawnn.server.model.Location;
import org.dawnn.server.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByLocation(Location location);

    List<User> findByhwid(String HWID);

}
