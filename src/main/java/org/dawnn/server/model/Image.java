package org.dawnn.server.model;

import com.drew.lang.GeoLocation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

/**
 * Represents an image with its location, its base 64 representation, the hardware from which it originated, and a UUID
 */
public class Image {

    @Getter
    private final GeoLocation location;

    @Getter
    private final String base64Image;

    @Getter
    private final String HWIDOrigin;

    @Getter
    private final UUID uuid;

    @JsonCreator
    public Image(@JsonProperty("image") String base64Image,
                 @JsonProperty("HWID") String HWIDOrigin,
                 @JsonProperty("longitude") double longitude,
                 @JsonProperty("latitude") double latitude) {
        this.location = new GeoLocation(latitude, longitude);
        this.base64Image = base64Image;
        this.HWIDOrigin = HWIDOrigin;
        this.uuid = UUID.randomUUID();
    }

}