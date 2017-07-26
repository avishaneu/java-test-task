package com.avishaneu.testtasks.tls.core.tsp.implementation;

import com.avishaneu.testtasks.tls.Application;
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
 * Created by tkalnitskaya on 26.07.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class SimulatedAnnealingAlgorithmTest {

    private List<Location> locations = new ArrayList<>();

    @Autowired
    SimulatedAnnealingAlgorithm algorithm;

    @Before
    public void setUp(){
        locations.add(new Location("", 0.0, 0.0));
        locations.add(new Location("", 2.0, 0.0));
        locations.add(new Location("", 4.0, 0.0));
        locations.add(new Location("", 4.0, 1.0));
        locations.add(new Location("", 2.0, 1.0));
        locations.add(new Location("", 0.0, 1.0));
        Collections.shuffle(locations);
    }

    @Test
    public void algorithmTest() {
       assertEquals(7.0, algorithm.solve(locations).getCost(), 0.0);
    }
}
