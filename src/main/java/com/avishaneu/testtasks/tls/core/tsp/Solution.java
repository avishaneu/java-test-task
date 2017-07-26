package com.avishaneu.testtasks.tls.core.tsp;

import com.avishaneu.testtasks.tls.model.Location;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 */
public class Solution {

    protected List<Location> locations;
    protected Double solutionCost;

    public Solution(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public Double getSolutionCost() {
        return solutionCost;
    }

    public void setSolutionCost(Double solutionCost) {
        this.solutionCost = solutionCost;
    }
}
