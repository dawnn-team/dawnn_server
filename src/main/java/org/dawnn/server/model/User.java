package org.dawnn.server.model;

import lombok.Data;

/**
 * Represents a user.
 */
@Data
public class User {

    private final String HWID;
    private final String token;

}

