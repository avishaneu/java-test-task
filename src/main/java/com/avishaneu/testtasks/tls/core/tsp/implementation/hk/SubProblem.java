package com.avishaneu.testtasks.tls.core.tsp.implementation.hk;

import java.util.BitSet;

/**
 * Created by avishaneu on 7/27/17.
 */
public class SubProblem {
    
    Integer currentLocation;
    BitSet visitedLocations;

    SubProblem(Integer currentLocation, BitSet visitedLocations) {
        this.currentLocation = currentLocation;
        this.visitedLocations = visitedLocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubProblem that = (SubProblem) o;

        return currentLocation.equals(that.currentLocation) && visitedLocations.equals(that.visitedLocations);
    }

    @Override
    public int hashCode() {
        int result = currentLocation.hashCode();
        result = 31 * result + visitedLocations.hashCode();
        return result;
    }
}
