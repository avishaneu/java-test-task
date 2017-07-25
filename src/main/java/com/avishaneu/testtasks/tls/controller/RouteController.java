package com.avishaneu.testtasks.tls.controller;

import com.avishaneu.testtasks.tls.model.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by avishaneu on 7/25/2017.
 */
@RestController
@RequestMapping("/routes")
public class RouteController {

    private static final Logger log = LoggerFactory.getLogger(RouteController.class);


    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Route createRoute(@Valid @RequestBody Route route){
        log.debug("Route creation requested. Route to be created: " + route);
        route.setId(1);
        return route;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Route getRoute(@PathVariable Integer id){
        log.debug("Route details requested. Route id: " + id);
        return new Route(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoute(@PathVariable Integer id, @RequestBody Route route){
        log.debug("Route update requested. Route id: " + id + " . New route details: " + route);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable Integer id){
        log.debug("Route removal requested. Route id: " + id);
    }


    @RequestMapping(path = "/{id}/plan", method = RequestMethod.GET)
    public ResponseEntity<Object> getRoutePlan(@PathVariable Integer id) throws URISyntaxException {
        log.debug("Route plan requested. Route id: " + id);
        log.debug("Route plan requested. Route id: " + id);
        //return ResponseEntity.ok().body(new RoutePlan(new Integer[]{1, 2, 3, 4, 5, 6}));
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(new URI("/routePlanQueue/100500")).body(null);
    }

}
