package org.dawnn.server.service.firebase;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.dawnn.server.model.MessageContent;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

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

    /**
     * Send a message to all users at specified topic.
     *
     * @param messageContent The content of the message to send.
     * @param topic          The topic to send the message to.
     * @return The ID of the message.
     */
    public String sendMessage(@NotNull MessageContent messageContent,
                              @NotNull String topic) throws ExecutionException, InterruptedException {

        // TODO Build notification in relation to the user platform?

        // Construct client notification first
        Notification notification = Notification.builder().setBody(messageContent.getContent())
                .setBody(messageContent.getContent())
                .build();

        // Now construct the message to be sent
        Message message = Message.builder()
                .setTopic(topic)
                .setNotification(notification)
                .putAllData(messageContent.getData())
                .build();

        ApiFuture<String> result = firebaseMessaging.sendAsync(message);
        return result.get();
    }

}


