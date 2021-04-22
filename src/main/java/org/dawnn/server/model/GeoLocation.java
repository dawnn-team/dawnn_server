package org.dawnn.server.model;

import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.geom.Point2D;

@Data
@Document(collection = "geolocation")
public class GeoLocation {

    private final double latitude;
    private final double longitude;
    private final DateTime time;
    // TODO Grid the world


    public GeoLocation(double latitude, double longitude, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = ISODateTimeFormat.dateTime().parseDateTime(time);
    }

    /**
     * Check whether a location is within range of another location
     *
     * @param location Location to check against.
     * @param range    The desired range.
     * @return True if within range, false otherwise.
     */
    public boolean isWithinRange(GeoLocation location, double range) {
        return distanceFrom(location) <= range;
    }

    /**
     * Find the distance from another {@link GeoLocation}.
     *
     * @param location Location from which the distance is calculated
     * @return Distance in doubles.
     */
    public double distanceFrom(GeoLocation location) {
        return Point2D.distance(this.latitude, this.longitude, location.latitude, location.longitude);
    }

}
