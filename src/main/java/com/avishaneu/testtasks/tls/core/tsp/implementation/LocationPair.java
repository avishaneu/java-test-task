package com.avishaneu.testtasks.tls.core.tsp.implementation;

import com.avishaneu.testtasks.tls.model.Location;

/**
 * Created by avishaneu on 7/26/17.
 */
public class LocationPair {

    private Integer firstLocationId;
    private Integer secondLocationId;

    LocationPair(Location firstLocation, Location secondLocation) {
        this.firstLocationId = firstLocation.getId();
        this.secondLocationId = secondLocation.getId();
    }


    LocationPair(Integer firstLocationId, Integer secondLocationId) {
        this.firstLocationId = firstLocationId;
        this.secondLocationId = secondLocationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationPair that = (LocationPair) o;

        if (firstLocationId.equals(that.firstLocationId) &&
                secondLocationId.equals(that.secondLocationId))
            return true;

        return firstLocationId.equals(that.secondLocationId) &&
                secondLocationId.equals(that.firstLocationId);

    }

    @Override
    public int hashCode() {
        return firstLocationId.hashCode() * firstLocationId.hashCode() +
                secondLocationId.hashCode() * secondLocationId.hashCode();
    }
}
