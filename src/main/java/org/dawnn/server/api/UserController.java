package org.dawnn.server.api;

import org.dawnn.server.dao.UserRepository;
import org.dawnn.server.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/user")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping(consumes = "application/json")
    public void updateLocation(@RequestBody User user) {
        logger.info("Received user update.");

        // Update user location instead of inserting new document
        if (!userRepository.findByhwid(user.getHwid()).isEmpty()) {
            logger.info("Updating existing user.");
            // Delete existing record and write new data.
            userRepository.delete(userRepository.findByhwid(user.getHwid()).stream().findFirst().get());
            userRepository.save(user);
        } else {
            userRepository.save(user);
        }
    }
}
