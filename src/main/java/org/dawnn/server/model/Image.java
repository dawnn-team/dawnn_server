package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
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
    private final String base64Image;
    private final String HWIDOrigin;
    private UUID uuid;

    @JsonCreator
    public Image(@JsonProperty("image") String base64Image,
                 @JsonProperty("HWID") String HWIDOrigin,
                 @JsonProperty("location") GeoLocation location) {
        this.location = new GeoLocation(location.getLatitude(), location.getLongitude());
        this.base64Image = base64Image;
        this.HWIDOrigin = HWIDOrigin;
        this.uuid = UUID.randomUUID();
    }

}