package org.dawnn.server.service;

import lombok.Data;

import java.util.Map;

/**
 * This class represents the content of a message, to be used
 * by the service.firebase classes.
 */
@Data
public class MessageContent {

    private final String subject;
    private final String content;
    private final String image;
    private final Map<String, String> data;

}
