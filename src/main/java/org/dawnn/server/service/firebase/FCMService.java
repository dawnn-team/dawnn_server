package org.dawnn.server.service.firebase;

import org.springframework.stereotype.Service;

/**
 * This class will be used for sending messages to the client
 * and getting responses.
 */
@Service
public class FCMService {

    private static FCMService instance;

    private FCMService() {

    }

    public static FCMService getInstance() {
        if (instance == null) {
            instance = new FCMService();
        }
        return instance;
    }

}


