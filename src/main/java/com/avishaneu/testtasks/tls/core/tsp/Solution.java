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

    public Solution(List<Location> locations, Double solutionCost) {
        this(locations);
        this.solutionCost = solutionCost;
    }

    public double getCost(){
        if (solutionCost == null) {
            solutionCost = 0.0;
            Location previousLocation = null;
            for (Location location : locations) {
                if (previousLocation == null) {
                    previousLocation = location;
                    continue;
                }
                solutionCost += distance(previousLocation, location);
                previousLocation = location;
            }
        }
        return solutionCost;
    }

    public List<Location> getLocations() {
        return locations;
    }

    private double distance(Location first, Location second) {
        return
                Math.sqrt(Math.pow((first.getX() - second.getX()), 2)
                        + Math.pow((first.getY() - second.getY()), 2));
    }
}
