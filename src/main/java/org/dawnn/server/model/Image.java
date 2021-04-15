package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * Represents an image with a location, its base64 representation,
 * the hwid origin, and unique uuid.
 */
@Data
public class Image {

    @Id
    public String id;

    private final GeoLocation location;
    private final String image;
    private final String HWIDOrigin;
    private final String caption;
    private UUID uuid;

    @JsonCreator
    public Image(@NonNull @JsonProperty("location") GeoLocation location,
                 @NonNull @JsonProperty("image") String image,
                 @NonNull @JsonProperty("HWID") String HWIDOrigin,
                 @JsonProperty("caption") String caption) {
        this.location = new GeoLocation(location.getLatitude(), location.getLongitude());
        this.image = image;
        this.HWIDOrigin = HWIDOrigin;
        this.caption = caption;
        this.uuid = UUID.randomUUID();
    }

}