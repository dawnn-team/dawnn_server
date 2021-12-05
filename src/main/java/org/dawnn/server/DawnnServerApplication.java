package org.dawnn.server;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Uses Spring to run the Dawnn server
 */
@SpringBootApplication
public class DawnnServerApplication {

    /**
     * This is how far the user should be able to see markers around
     * themselves, in kilometers.
     */
    public static final double USER_VIEW_DISTANCE = 1.0;
    private static final Logger logger = LoggerFactory.getLogger(DawnnServerApplication.class);

    public DawnnServerApplication() {
        FirebaseApp.initializeApp();
        logger.info("Initialized Firebase App.");
        logger.info("Initialized Dawnn Server.");
    }

    public static void main(String[] args) {
        SpringApplication.run(DawnnServerApplication.class, args);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }
}

