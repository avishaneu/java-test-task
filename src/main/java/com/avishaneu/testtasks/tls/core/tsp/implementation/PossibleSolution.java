package com.avishaneu.testtasks.tls.core.tsp.implementation;

import com.avishaneu.testtasks.tls.core.tsp.Solution;
import com.avishaneu.testtasks.tls.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by avishaneu on 7/25/17.
 */
public class PossibleSolution extends Solution {

    PossibleSolution(List<Location> locations) {
        super(locations);
    }

    PossibleSolution copy() {
        return new PossibleSolution(new ArrayList<>(this.locations));
    }

    public void shuffle() {
        this.solutionCost = null;

        int firstIndex = ThreadLocalRandom.current().nextInt(1, locations.size());
        int secondIndex = ThreadLocalRandom.current().nextInt(1, locations.size());

        Location firstLocation = locations.get(firstIndex);
        Location secondLocation = locations.get(secondIndex);

        locations.set(firstIndex, secondLocation);
        locations.set(secondIndex, firstLocation);

    }
}
