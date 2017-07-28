package com.avishaneu.testtasks.tls.core.tsp.implementation.sa;

import com.avishaneu.testtasks.tls.core.tsp.Solution;
import com.avishaneu.testtasks.tls.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by avishaneu on 7/25/17.
 */
class PossibleSolution extends Solution {

    PossibleSolution(List<Location> locations, Double cost) {
        super(locations, cost);
    }

    PossibleSolution copy() {
        return new PossibleSolution(new ArrayList<>(this.locations), this.cost);
    }

    void shuffle() {
        this.cost = null;

        int firstIndex = ThreadLocalRandom.current().nextInt(1, locations.size());
        int secondIndex = ThreadLocalRandom.current().nextInt(1, locations.size());

        Collections.swap(locations, firstIndex, secondIndex);
    }
}
