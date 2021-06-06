package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Random;
import java.util.UUID;

/**
 * Represents an image with a location, its base64 representation,
 * the hwid origin, and unique uuid.
 */
@Data
@Document(collection = "images")
public class Image {

    private final String image;
    private final String caption;
    @Id
    public String id;
    // TODO Make location final
    private Location location;
    private String hwid;
    private UUID uuid;

    // Apple requires a way to moderate user submitted content.
    // Why not have users give feedback on other users?
    private int likes;
    private int dislikes;

    @JsonCreator
    public Image(@NonNull @JsonProperty("location") Location location,
                 @NonNull @JsonProperty("base64") String image,
                 @NonNull @JsonProperty("hwid") String hwid,
                 @JsonProperty("caption") String caption) {
        this.location = location;
        this.image = image;
        this.hwid = hwid;
        this.caption = caption;
        this.uuid = UUID.randomUUID();
        this.likes = 0;
        this.dislikes = 0;
    }

    /**
     * Anonymize the sender by setting hwid to null.
     * Required to call this before sending any data back to client.
     */
    public void eraseHwid() {
        this.hwid = null;
    }

    /**
     * Scramble the location of this image. Will not change the MongoDB entry,
     * will only effect the instance read from the database. Purely for testing purposes, and also
     * https://github.com/dawnn-team/dawnn_server/issues/10
     */
    public void scrambleLocation(Random random) {
        int positiveLat = random.nextInt(7) % 2 == 0 ? 1 : -1;
        int positiveLong = random.nextInt(7) % 2 == 0 ? 1 : -1;
        this.location = new Location(random.nextDouble() * 90 * positiveLat,
                random.nextDouble() * 180 * positiveLong,
                location.getTime());
    }

}