package org.dawnn.server.model;

import com.drew.lang.GeoLocation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.dawnn.server.manager.ImageUtils;

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

    public Image(@JsonProperty("image") String base64Image,
                 @JsonProperty("HWID") String HWIDOrigin) {
        // We probably want to pass this value in explicitly,
        // So we don't have to dig up metadata.
        this.location = ImageUtils.findLocation(base64Image);
        this.base64Image = base64Image;
        this.HWIDOrigin = HWIDOrigin;
        this.uuid = UUID.randomUUID();
    }

}
