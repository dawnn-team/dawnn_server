package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Represents a user, with a location and a hwid.
 */
@Data
@Document(collection = "users")
public class User {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String hwid;
//    @Id
//    public String id;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE, name = "location")
    private GeoJsonPoint location;

    // Delete user data to make attackers mad :^)
//    @Indexed(expireAfterSeconds = 61)
//    @Field
    private Date time;

    public User(String hwid, double x, double y) {
        this.hwid = hwid;
        this.location = new GeoJsonPoint(x, y);
        this.time = new Date();
    }

}