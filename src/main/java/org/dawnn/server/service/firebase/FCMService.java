package org.dawnn.server.service.firebase;

import com.google.firebase.messaging.Message;
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

    /**
     * Documentation stub
     * @return FCMService
     */
    public static FCMService getInstance() {
        if (instance == null) {
            instance = new FCMService();
        }
        return instance;
    }

//    public static Message sendMessage() {
//        Message alert = new Message.builder().putData().setToken().build(); //token and whatever data
//    }

}


