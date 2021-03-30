package org.dawnn.server.service.firebase;

import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.dawnn.server.model.Platform;
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

    /**
     * Send a message to a user at specified token.
     *
     * @param messageContent The content of the message to send.
     * @param token          The recipient of the message.
     * @param platform       The target users' platform.
     * @return The ID of the message.
     * @throws FirebaseMessagingException - If an error occurs during sending.
     */
    public String sendMessage(@NotNull MessageContent messageContent,
                              @NotNull String token, @Nullable Platform platform) throws FirebaseMessagingException {
        // TODO Build notification in relation to the user platform?

        // Construct client notification first
        Notification notification = Notification.builder().setBody(messageContent.getContent())
                .setBody(messageContent.getContent())
                .build();

        // Now construct the message to be sent
        Message message = Message.builder().setToken(token)
                .setNotification(notification)
                .putAllData(messageContent.getData())
                .build();

        return firebaseMessaging.send(message);
    }

}


