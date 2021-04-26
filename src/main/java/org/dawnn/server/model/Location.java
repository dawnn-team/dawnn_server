package org.dawnn.server.model;

import lombok.Data;

import java.awt.geom.Point2D;

/**
 * This class represents the location of a {@link User}. The location is
 * represented as a double of latitude and longitude. This class also
 * stores the timestamp of the creation of this location.
 */
@Data
public class Location {

    private final double latitude;
    private final double longitude;
    private final Long time;
    // TODO Grid the world ?


    public Location(double latitude, double longitude, Long time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    /**
     * Check whether a location is within range of another location
     *
     * @param location Location to check against.
     * @param range    The desired range.
     * @return True if within range, false otherwise.
     */
    public boolean isWithinRange(Location location, double range) {
        return distanceFrom(location) <= range;
    }

    /**
     * Find the distance from another {@link Location}.
     *
     * @param location Location from which the distance is calculated
     * @return Distance in doubles.
     */
    public double distanceFrom(Location location) {
        return Point2D.distance(this.latitude, this.longitude, location.latitude, location.longitude);
    }

}
