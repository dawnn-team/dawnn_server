package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Represents an image with a location, its base64 representation,
 * the hwid origin, and unique uuid.
 */
@Data
@Document(collection = "images")
public class Image {

    private final Location location;
    private final String image;
    private final String caption;
    @Id
    public String id;
    private String hwid;
    private UUID uuid;

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
    }

    /**
     * Anonymize the sender by setting hwid to null.
     * Required to call this before sending any data back to client.
     */
    public void anonymizeSender() {
        this.hwid = null;
    }

}