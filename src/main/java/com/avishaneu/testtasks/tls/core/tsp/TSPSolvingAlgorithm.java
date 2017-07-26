package com.avishaneu.testtasks.tls.core.tsp;

import com.avishaneu.testtasks.tls.model.Location;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 */
public interface TSPSolvingAlgorithm {

    Solution solve(List<Location> input);
}
