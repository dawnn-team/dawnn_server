package org.dawnn.server.model;

import com.drew.lang.GeoLocation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

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
                 @JsonProperty("location") GeoLocation location) {
        this.location = location;
        this.base64Image = base64Image;
        this.HWIDOrigin = HWIDOrigin;
        this.uuid = UUID.randomUUID();
    }

}