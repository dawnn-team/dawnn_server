package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.client.model.geojson.Point;
import lombok.Data;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

/**
 * This class represents the location of a {@link User}. The location is
 * represented as a double of latitude and longitude. This class also
 * stores the timestamp of the creation of this location.
 */
@Data
@Deprecated
public class Location {

    // Represents the radius in which we should display
    // images to the client.
    public static final double LOCATION_DISTANCE = 1;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;

    public Location(@JsonProperty(value = "location") Point location) {
        this.location = location;
    }
}
