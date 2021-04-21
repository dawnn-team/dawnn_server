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

    private final GeoLocation location;
    private final String image;
    private final String HWIDOrigin;
    private final String caption;
    @Id
    public String id;
    private UUID uuid;

    @JsonCreator
    public Image(@NonNull @JsonProperty("location") GeoLocation location,
                 @NonNull @JsonProperty("base64") String image,
                 @NonNull @JsonProperty("hwid") String HWIDOrigin,
                 @JsonProperty("caption") String caption) {
        this.location = location;
        this.image = image;
        this.HWIDOrigin = HWIDOrigin;
        this.caption = caption;
        this.uuid = UUID.randomUUID();
    }

}