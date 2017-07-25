package com.avishaneu.testtasks.tls.controller;

import com.avishaneu.testtasks.tls.Application;
import com.avishaneu.testtasks.tls.model.Location;
import com.avishaneu.testtasks.tls.model.Route;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by avishaneu on 7/25/17.
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteControllerTest {

    private static final String URL_PREFIX = "/routes";

    private static Route route;
    private static int routeId;

    private static Location location = new Location("Morlaw", 10.0500, 33.1210);

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp(){
        createLocation();
        route = new Route("Interstate 60", location.getId(), Arrays.asList(location.getId()));
        HttpEntity<Route> entity = new HttpEntity<>(route, headers);

        ResponseEntity<Route> response = restTemplate.exchange(getEndpoint() + "/",
                HttpMethod.POST, entity, Route.class);

        routeId = response.getBody().getId();
    }

    @Test
    public void routeCreate() {

        HttpEntity<Route> entity = new HttpEntity<>(route, headers);

        ResponseEntity<Route> response = restTemplate.exchange(getEndpoint() + "/",
                HttpMethod.POST, entity, Route.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Route responseRoute = response.getBody();

        assertEquals(route.getName(), responseRoute.getName());
        assertEquals(route.getHead(), responseRoute.getHead());
        assertEquals(route.getLocations(), responseRoute.getLocations());
    }

    @Test
    public void routeGet() {
        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Route> response = restTemplate.exchange(getEndpoint() + "/" + routeId,
                HttpMethod.GET, entity, Route.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(routeId, (int)response.getBody().getId());
    }

    @Test
    public void routeUpdate() {
        HttpEntity<Route> entity = new HttpEntity<>(route, headers);

        ResponseEntity<Void> response = restTemplate.exchange(getEndpoint() + "/" + routeId,
                HttpMethod.PUT, entity, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    public void routeDelete() {
        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Void> response = restTemplate.exchange(getEndpoint() + "/" + routeId,
                HttpMethod.DELETE, entity, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void invalidRouteCreate() {
        HttpEntity<Route> entity = new HttpEntity<>(
                new Route("Interstate 60", 1, new ArrayList<>()), headers);

        ResponseEntity<Route> response = restTemplate.exchange(getEndpoint() + "/",
                HttpMethod.POST, entity, Route.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }


    private String getEndpoint(String urlPrefix){
        return  "http://localhost:" + port + urlPrefix;
    }

    private String getEndpoint(){
        return  getEndpoint(URL_PREFIX);
    }

    private void createLocation(){
        HttpEntity<Location> locationEntity = new HttpEntity<>(location, headers);

        ResponseEntity<Location> locationResponse = restTemplate.exchange(
                getEndpoint(LocationControllerTest.URL_PREFIX) + "/",
                HttpMethod.POST, locationEntity, Location.class);

        location.setId(locationResponse.getBody().getId());
    }
}
