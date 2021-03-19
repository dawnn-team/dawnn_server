package org.dawnn.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Uses Spring to run the Dawn server
 */
@SpringBootApplication
public class DawnServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DawnServerApplication.class, args);
    }

}

