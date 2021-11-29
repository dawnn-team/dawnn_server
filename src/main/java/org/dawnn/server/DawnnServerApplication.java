package org.dawnn.server;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.ByteArrayInputStream;
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
    private static final Logger logger = LoggerFactory.getLogger(DawnnServerApplication.class);
    public static final boolean IS_PRODUCTION = !Boolean.parseBoolean(System.getenv("CI"));

    public DawnnServerApplication() throws IOException {
        String authData;

        if (!IS_PRODUCTION) {
            logger.info("Building auth data for Firebase app on a GitHub runner.");
            // CI env variable is always true on GitHub runners
            // We need to build auth data manually
            authData = new JSONObject()
                    .put("type", System.getenv("type"))
                    .put("project_id", System.getenv("project_id"))
                    .put("private_key_id", System.getenv("private_key_id"))
                    .put("private_key", System.getenv("private_key"))
                    .put("client_email", System.getenv("client_email"))
                    .put("client_id", System.getenv("client_id"))
                    .put("auth_uri", System.getenv("auth_uri"))
                    .put("token_uri", System.getenv("token_uri"))
                    .put("auth_provider_x509_cert_url", System.getenv("auth_provider_x509_cert_url"))
                    .put("client_x509_cert_url", System.getenv("client_x509_cert_url"))
                    .toString();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials
                            .fromStream(new ByteArrayInputStream(authData.getBytes())
                            ))
                    .build();
            FirebaseApp.initializeApp(options);
        } else {
            FirebaseApp.initializeApp();
        }


        logger.info("Initialized FirebaseApp.");
    }

    public static void main(String[] args) {
        SpringApplication.run(DawnnServerApplication.class, args);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        return FirebaseMessaging.getInstance();
    }
}

