package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Random;
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
    @Id
    public String id;
    private UUID uuid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Indexed(expireAfterSeconds = 61)
//    @Field
    private Date time;

    // Apple requires a way to moderate user submitted content.
    // Why not have users give feedback on other users?
    private int likes;
    private int dislikes;

    public Image(User user, String base64, String caption) {
        this.user = user;
        this.base64 = base64;
        this.caption = caption;
        this.uuid = UUID.randomUUID();
        this.time = new Date();
        this.likes = 0;
        this.dislikes = 0;
    }

    /**
     * Scramble the location of this image. Will not change the MongoDB entry,
     * will only effect the instance read from the database. Purely for testing purposes, and also
     * https://github.com/dawnn-team/dawnn_server/issues/10
     */
    @Deprecated
    public void scrambleLocation(Random random) {
        int positiveLat = random.nextInt(7) % 2 == 0 ? 1 : -1;
        int positiveLong = random.nextInt(7) % 2 == 0 ? 1 : -1;
        this.user.setLocation(new Location(random.nextDouble() * 90 * positiveLat,
                random.nextDouble() * 180 * positiveLong));
    }

}