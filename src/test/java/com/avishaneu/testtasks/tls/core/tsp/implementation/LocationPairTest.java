package com.avishaneu.testtasks.tls.core.tsp.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by avishaneu on 7/26/17.
 */

@RunWith(SpringRunner.class)
public class LocationPairTest {

    private LocationPair first = new LocationPair(1, 2);
    private LocationPair second = new LocationPair(2, 1);
    private LocationPair third = new LocationPair(3, 0);


    @Test
    public void locationPairEqualsTest() {
        assertEquals(first, second);
        assertEquals(second, first);
        assertEquals(first, first);
        assertNotEquals(first, third);
    }


    @Test
    public void locationPairHashCodeTest() {
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(second.hashCode(), third.hashCode());
    }
}
