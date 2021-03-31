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
 * Uses Spring to run the Dawn server
 */
@SpringBootApplication
public class DawnnServerApplication {

    @Value("${app.firebase-configuration-file-var}")
    private String firebaseAuthPath;

    public static void main(String[] args) {
        SpringApplication.run(DawnnServerApplication.class, args);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        // TODO Move this method somewhere else?

        // GitHub runners shouldn't use JSON env vars,
        // so let's check if AUTH_HOME (JSON env var) exists
        String authData = null;
        if (System.getenv(firebaseAuthPath) == null) {
            // It doesn't exist - we're on a GitHub runner.
            // Let's build auth data manually.

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
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials
                        .fromStream(
                                authData == null ?
                                        new FileInputStream(System.getenv(firebaseAuthPath)) :
                                        new ByteArrayInputStream(authData.getBytes())
                        ))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);

        return FirebaseMessaging.getInstance(app);
    }
}

