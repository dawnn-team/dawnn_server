package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

/**
 * Represents an image with a location, its base64 representation,
 * the hwid origin, and unique uuid.
 */
@Data
@Document(collection = "images")
public class Image {

    private final String base64;
    private final String caption;

    // Write only fields mean we can't read them and return them to the user.
    // This is used for sensitive or unused data, such as the authors hwid.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String authorHWID;
    private UUID uuid;

    // Expire after 3 hours.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Indexed(expireAfterSeconds = 10800)
    private Date time;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE, name = "location")
    private GeoJsonPoint location;

    private double x;
    private double y;

    // Apple requires a way to moderate user submitted content.
    // Why not have users give feedback on images?
    private int likes;
    private int dislikes;

    public Image(String authorHWID, String base64, String caption, double x, double y) {
        this.base64 = base64;
        this.caption = caption;
        this.authorHWID = authorHWID;
        this.uuid = UUID.randomUUID();
        this.time = new Date();
        this.location = new GeoJsonPoint(x, y);
        this.x = x;
        this.y = y;
        this.likes = 0;
        this.dislikes = 0;
    }

}