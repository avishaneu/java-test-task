package com.avishaneu.testtasks.tls.core.tsp.implementation;

import com.avishaneu.testtasks.tls.model.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by avishaneu on 7/26/17.
 */
public class CostMap {

    private Map<LocationPair, Double> adjacencyMap = new HashMap<>();
    private Map<List<Location>, Double> costMap = new HashMap<>();

    public CostMap(List<Location> locations) {
        createAdjacencyMap(locations);
    }

    private void createAdjacencyMap(List<Location> locations) {
        for (int i = 0; i < locations.size(); i++) {
            for (int j = i; j < locations.size(); j++) {
                Location first = locations.get(i);
                Location second = locations.get(j);
                if (i == j) {
                    adjacencyMap.put(new LocationPair(first, second), 0.0);
                } else {
                    adjacencyMap.put(new LocationPair(first, second), distance(first, second));
                }
            }
        }
    }

    private double distance(Location first, Location second) {
        return
                Math.sqrt(Math.pow((first.getX() - second.getX()), 2)
                        + Math.pow((first.getY() - second.getY()), 2));
    }

    public double getCost(List<Location> locations) {
        if (!costMap.containsKey(locations)) {
            double cost = 0.0;
            Location previousLocation = null;
            for (Location location : locations) {
                if (previousLocation == null) {
                    previousLocation = location;
                    continue;
                }
                cost += adjacencyMap.get(new LocationPair(previousLocation, location));
                previousLocation = location;
            }
            costMap.put(locations, cost);
        }
        return costMap.get(locations);
    }

    public double getCost(LocationPair pair) {
        return adjacencyMap.get(pair);
    }
}
