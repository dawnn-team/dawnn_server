package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
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
    private final User user;
    //    @Id
//    public String id;
    private UUID uuid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Indexed(expireAfterSeconds = 61)
//    @Field
    private Date time;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE, name = "location")
    private GeoJsonPoint location;

    // Apple requires a way to moderate user submitted content.
    // Why not have users give feedback on images?
    private int likes;
    private int dislikes;

    public Image(User user, String base64, String caption) {
        this.user = user;
        this.base64 = base64;
        this.caption = caption;
        this.uuid = UUID.randomUUID();
        this.time = new Date();
        this.location = user.getLocation();
        this.likes = 0;
        this.dislikes = 0;
    }

}