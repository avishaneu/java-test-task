package com.avishaneu.testtasks.tls.core.tsp.implementation;

import com.avishaneu.testtasks.tls.core.tsp.TSPSolvingAlgorithm;
import com.avishaneu.testtasks.tls.model.Location;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 */
@Component
public class TSPSolvingAlgorithmDummy implements TSPSolvingAlgorithm {

    public List<Location> solve (List<Location> input){
        return input;
    }
}
