package org.dawnn.server;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Uses Spring to run the Dawn server
 */
@SpringBootApplication
public class DawnServerApplication {

    @Value("${app.firebase-configuration-file-var}")
    private String firebaseConfigPath;

    public static void main(String[] args) {
        SpringApplication.run(DawnServerApplication.class, args);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        // TODO Move this method somewhere else?

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(System.getenv(firebaseConfigPath))))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);

        return FirebaseMessaging.getInstance(app);
    }
}

