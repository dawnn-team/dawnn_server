package org.dawnn.server.model;

import lombok.Data;

/**
 * Represents a user, with a location and a hwid.
 */
@Data
public class User {

    private final String HWID;
    private final GeoLocation geoLocation;

}

