package org.dawnn.server.service.firebase;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.dawnn.server.service.MessageContent;
import org.springframework.stereotype.Service;

/**
 * This class will be used for sending messages to the client
 * and getting responses.
 */
@Service
public class FCMService {

    private final FirebaseMessaging firebaseMessaging;

    public FCMService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public String sendMessage(MessageContent messageContent, String token) {

        // Construct client notification first
        Notification notification = Notification.builder().setBody(messageContent.getContent())
                .setBody(messageContent.getContent())
                .build();

        // Now construct the message to be sent
        Message message = Message.builder().setToken(token)
                .setNotification(notification)
                .putAllData(messageContent.getData())
                .build();

        return firebaseMessaging.sendNotification()
    }

}


