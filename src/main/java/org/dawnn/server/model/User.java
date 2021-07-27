package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Represents a user, with a location and a hwid.
 */
@Data
@Document(collection = "users")
public class User {

    // Never give this back to our users.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String HWID;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE, name = "location")
    private GeoJsonPoint location;

    // Delete user data to make attackers mad :^)
    // Expire after 3 hours.
    @Indexed(expireAfterSeconds = 10800)
    private Date time;

    public User(String HWID, double longitude, double latitude) {
        this.HWID = HWID;
        this.location = new GeoJsonPoint(longitude, latitude);
        this.time = new Date();
    }

}