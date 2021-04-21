package org.dawnn.server.api;

import org.dawnn.server.dao.UserRepository;
import org.dawnn.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/location")
@RestController
public class UserController {

    @Autowired
    private UserRepository imageRepository;

    @PostMapping(consumes = "application/json")
    public void updateLocation(@RequestBody User user) {
        imageRepository.save(user);
    }
}
