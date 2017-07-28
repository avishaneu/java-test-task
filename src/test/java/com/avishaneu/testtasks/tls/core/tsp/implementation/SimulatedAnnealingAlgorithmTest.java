package com.avishaneu.testtasks.tls.core.tsp.implementation;

import com.avishaneu.testtasks.tls.Application;
import com.avishaneu.testtasks.tls.core.tsp.implementation.sa.SimulatedAnnealingAlgorithm;
import com.avishaneu.testtasks.tls.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by avishaneu on 7/26/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SimulatedAnnealingAlgorithmTest {

    private List<Location> locations = new ArrayList<>();

    @Autowired
    private SimulatedAnnealingAlgorithm algorithm;

    @Before
    public void setUp() {
        locations.add(new Location(2, 2.0, 2.0));
        locations.add(new Location(3, 4.0, 2.0));
        locations.add(new Location(4, 4.0, 3.0));
        locations.add(new Location(5, 2.0, 3.0));
        locations.add(new Location(6, 0.0, 3.0));
        Collections.shuffle(locations);
        locations.add(0, new Location(1, 0.0, 2.0));
    }

    @Test
    public void algorithmTest() {
        assertEquals(7.0, algorithm.solve(locations).getCost(), 1.0);
    }
}
