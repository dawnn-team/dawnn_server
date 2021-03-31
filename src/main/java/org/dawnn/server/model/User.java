package org.dawnn.server.model;

import lombok.Getter;

/**
 * Represents a user.
 */
public class User {

    @Getter
    private final String HWID;
    @Getter
    private final Platform platform;
    @Getter
    private final String token;

    public User(String HWID, Platform platform, String token) {
        this.HWID = HWID;
        this.platform = platform;
        this.token = token;
    }
}

