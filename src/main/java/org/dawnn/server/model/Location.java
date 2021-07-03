package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.awt.geom.Point2D;
import java.time.Instant;

/**
 * This class represents the location of a {@link User}. The location is
 * represented as a double of latitude and longitude. This class also
 * stores the timestamp of the creation of this location.
 */
@Data
public class Location {

    // Represents the radius in which we should display
    // images to the client.
    private static final double LOCATION_DISTANCE = 0.01;
    private final double latitude;
    private final double longitude;

    // User probably doesn't need to know when an image will expire.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long time;

    // TODO Use MongoDB spatial indices

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = Instant.now().getEpochSecond();
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

    /**
     * Evaluates if this Location is equal to another.
     * Equality is defined as being an instance of Location
     * and the distance between the two locations being less than 1.11 KM.
     * To be redesigned.
     *
     * @param other The other object to compare against.
     * @return True if distance < 1.11 KM, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        // FIXME This is a poor implementation.
        return other instanceof Location && this.isWithinRange((Location) other, LOCATION_DISTANCE);
    }

}
