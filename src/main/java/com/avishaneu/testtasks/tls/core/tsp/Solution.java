package com.avishaneu.testtasks.tls.core.tsp;

import com.avishaneu.testtasks.tls.model.Location;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 */
public class Solution {

    protected List<Location> locations;
    protected Double cost;

    public Solution(List<Location> locations) {
        this.locations = locations;
    }

    public Solution(List<Location> locations, Double cost) {
        this(locations);
        this.cost = cost;
    }


    public List<Location> getLocations() {
        return locations;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
