package org.dawnn.server;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

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

    @Value("${app.firebase-configuration-file-var}")
    private String firebaseAuthPath;

    public static void main(String[] args) {
        FirebaseApp.initializeApp();
        SpringApplication.run(DawnnServerApplication.class, args);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        return FirebaseMessaging.getInstance();
    }
}

