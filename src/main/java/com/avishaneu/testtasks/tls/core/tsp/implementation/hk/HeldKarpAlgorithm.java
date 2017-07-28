package com.avishaneu.testtasks.tls.core.tsp.implementation.hk;

import com.avishaneu.testtasks.tls.core.tsp.Solution;
import com.avishaneu.testtasks.tls.core.tsp.TSPSolvingAlgorithm;
import com.avishaneu.testtasks.tls.core.tsp.implementation.CostMap;
import com.avishaneu.testtasks.tls.core.tsp.implementation.LocationPair;
import com.avishaneu.testtasks.tls.model.Location;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by avishaneu on 7/27/2017.
 *
 * Held–Karp algorithm
 * based on: https://en.wikipedia.org/wiki/Held-Karp_algorithm
 * and: https://www.youtube.com/watch?v=-JjA4BLQyqE
 */
@Component(value = "hk")
public class HeldKarpAlgorithm implements TSPSolvingAlgorithm {

    public Solution solve(List<Location> locations) {
        CachedData cache = new CachedData(locations, new CostMap(locations));

        BitSet visitedLocations = new BitSet(cache.locationsCount);

        visitedLocations.flip(0); //head location of the route

        SubProblem rootProblem = new SubProblem(0, visitedLocations);
        recursiveStep(rootProblem, cache);

        List<Location> result = restoreOptimalPath(rootProblem, cache);

        return new Solution(result, cache.costMap.getCost(result));
    }

    private double recursiveStep(SubProblem subProblem, CachedData cache) {

        int n = cache.locationsCount;

        if (cache.costs.containsKey(subProblem))
            return cache.costs.get(subProblem);

        Double minCost = null;
        Integer optimalNext = null;

        for (int nextLocation = 0; nextLocation < n; nextLocation++) {

            if (subProblem.visitedLocations.get(nextLocation))
                continue;

            BitSet newSet = (BitSet) subProblem.visitedLocations.clone();
            newSet.flip(nextLocation);

            double pathLength = recursiveStep(new SubProblem(nextLocation, newSet), cache)
                    + getCost(nextLocation, subProblem.currentLocation, cache);

            if (minCost == null || pathLength < minCost) {
                minCost = pathLength;
                optimalNext = nextLocation;
            }
        }
        if (optimalNext != null) {
            cache.costs.put(subProblem, minCost);
            cache.next.put(subProblem, optimalNext);
            return minCost;
        }
        return 0;
    }

    private double getCost(int i, int j, CachedData cache) {
        return cache.costMap.getCost(new LocationPair(cache.locations.get(i), cache.locations.get(j)));
    }

    private List<Location> restoreOptimalPath(SubProblem subProblem, CachedData cache) {
        List<Location> result = new ArrayList<>();
        if (cache.next.containsKey(subProblem)) {
            int nextLocation = cache.next.get(subProblem);
            result.add(cache.locations.get(subProblem.currentLocation));
            BitSet newSet = (BitSet) subProblem.visitedLocations.clone();
            newSet.flip(nextLocation);
            result.addAll(restoreOptimalPath(new SubProblem(nextLocation, newSet), cache));
            return result;
        } else {
            result.add(cache.locations.get(subProblem.currentLocation));
        }
        return result;
    }

    private class CachedData {
        int locationsCount;
        List<Location> locations;
        CostMap costMap;
        Map<SubProblem, Double> costs = new HashMap<>();
        Map<SubProblem, Integer> next = new HashMap<>();

        CachedData(List<Location> locations, CostMap costMap) {
            this.locations = locations;
            this.locationsCount = locations.size();
            this.costMap = costMap;
        }
    }
}