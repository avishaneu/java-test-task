package com.avishaneu.testtasks.tls.controller;

import com.avishaneu.testtasks.tls.Application;
import com.avishaneu.testtasks.tls.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by avishaneu on 7/25/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationControllerTest {

    static final String URL_PREFIX = "/locations";

    private static Location location = new Location("Morlaw", 10.0500, 33.1210);
    private static int locationId;

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        HttpEntity<Location> entity = new HttpEntity<>(location, headers);

        ResponseEntity<Location> response = restTemplate.exchange(getEndpoint() + "/",
                HttpMethod.POST, entity, Location.class);

        locationId = response.getBody().getId();
    }

    @Test
    public void locationCreate() {

        HttpEntity<Location> entity = new HttpEntity<>(location, headers);

        ResponseEntity<Location> response = restTemplate.exchange(getEndpoint() + "/",
                HttpMethod.POST, entity, Location.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Location responseLocation = response.getBody();

        assertEquals(location.getName(), responseLocation.getName());
        assertEquals(location.getX(), responseLocation.getX());
        assertEquals(location.getY(), responseLocation.getY());
    }

    @Test
    public void locationGet() {
        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Location> response = restTemplate.exchange(getEndpoint() + "/" + locationId,
                HttpMethod.GET, entity, Location.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(locationId, (int) response.getBody().getId());
    }

    @Test
    public void locationUpdate() {
        HttpEntity<Location> entity = new HttpEntity<>(location, headers);

        ResponseEntity<Void> response = restTemplate.exchange(getEndpoint() + "/" + locationId,
                HttpMethod.PUT, entity, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    public void locationDelete() {
        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Void> response = restTemplate.exchange(getEndpoint() + "/" + locationId,
                HttpMethod.DELETE, entity, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void invalidLocationCreate() {
        HttpEntity<Location> entity = new HttpEntity<>(new Location("", 10.0500, null), headers);

        ResponseEntity<Location> response = restTemplate.exchange(getEndpoint() + "/",
                HttpMethod.POST, entity, Location.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }


    private String getEndpoint() {
        return "http://localhost:" + port + URL_PREFIX;
    }
}
