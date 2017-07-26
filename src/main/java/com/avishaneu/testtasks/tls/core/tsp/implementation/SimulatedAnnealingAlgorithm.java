package com.avishaneu.testtasks.tls.core.tsp.implementation;

import com.avishaneu.testtasks.tls.core.tsp.TSPSolvingAlgorithm;
import com.avishaneu.testtasks.tls.model.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 * based on http://katrinaeg.com/simulated-annealing.html
 * and https://en.wikipedia.org/wiki/Simulated_annealing
 */
@Component
public class SimulatedAnnealingAlgorithm implements TSPSolvingAlgorithm {

    @Value("${algorithms.sa.initial-temperature}")
    private double initialTemperature;

    @Value("${algorithms.sa.cooling-rate}")
    private double coolingRate;

    public PossibleSolution solve(List<Location> input) {
        double currentTemperature = initialTemperature;

        PossibleSolution currentSolution = new PossibleSolution(input);
        PossibleSolution bestSolutionSoFar = currentSolution.copy();

        while (currentTemperature > 1) {
            PossibleSolution newSolution = currentSolution.copy();
            newSolution.shuffle();

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
