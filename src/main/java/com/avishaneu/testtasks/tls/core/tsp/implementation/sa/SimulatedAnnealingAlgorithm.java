package com.avishaneu.testtasks.tls.core.tsp.implementation.sa;

import com.avishaneu.testtasks.tls.core.tsp.Solution;
import com.avishaneu.testtasks.tls.core.tsp.TSPSolvingAlgorithm;
import com.avishaneu.testtasks.tls.core.tsp.implementation.CostMap;
import com.avishaneu.testtasks.tls.model.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 * based on http://katrinaeg.com/simulated-annealing.html
 * and https://en.wikipedia.org/wiki/Simulated_annealing
 */
@Component(value = "sa")
public class SimulatedAnnealingAlgorithm implements TSPSolvingAlgorithm {

    @Value("${algorithms.sa.initial-temperature}")
    private double initialTemperature;

    @Value("${algorithms.sa.cooling-rate}")
    private double coolingRate;

    public Solution solve(List<Location> input) {
        double currentTemperature = initialTemperature;

        CostMap costMap = new CostMap(input);

        PossibleSolution currentSolution = new PossibleSolution(input, costMap.getCost(input));
        PossibleSolution bestSolutionSoFar = currentSolution.copy();

        while (currentTemperature > 1) {
            PossibleSolution newSolution = currentSolution.copy();
            newSolution.shuffle();
            newSolution.setCost(costMap.getCost(newSolution.getLocations()));

            if (acceptNewSolution(newSolution.getCost(), currentSolution.getCost(), currentTemperature)) {
                currentSolution = newSolution.copy();
            }

            if (currentSolution.getCost() < bestSolutionSoFar.getCost()) {
                bestSolutionSoFar = currentSolution.copy();
            }
            currentTemperature *= coolingRate;
        }
        return bestSolutionSoFar;
    }

    private boolean acceptNewSolution(double newSolutionEnergy, double currentSolutionEnergy,
                                             double currentTemperature) {

        return newSolutionEnergy < currentSolutionEnergy ||
                Math.exp((newSolutionEnergy - currentSolutionEnergy) / currentTemperature) > Math.random();
    }
}
