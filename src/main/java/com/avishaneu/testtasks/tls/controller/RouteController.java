package com.avishaneu.testtasks.tls.controller;

import com.avishaneu.testtasks.tls.model.Route;
import com.avishaneu.testtasks.tls.model.RoutePlanGenerationStatus;
import com.avishaneu.testtasks.tls.service.RoutePlanQueueService;
import com.avishaneu.testtasks.tls.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private RouteService routeService;
    private RoutePlanQueueService routePlanQueueService;

    @Autowired
    public RouteController(RouteService routeService, RoutePlanQueueService routePlanQueueService) {
        this.routeService = routeService;
        this.routePlanQueueService = routePlanQueueService;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Route createRoute(@Valid @RequestBody Route route){
        log.debug("Route creation requested. Route to be created: " + route);
        return routeService.createRoute(route);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Route getRoute(@PathVariable Integer id){
        log.debug("Route details requested. Route id: " + id);
        return routeService.getRoute(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoute(@PathVariable Integer id, @RequestBody Route route){
        log.debug("Route update requested. Route id: " + id + " . New route details: " + route);
        routeService.updateRoute(id, route);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable Integer id){
        log.debug("Route removal requested. Route id: " + id);
        routeService.deleteRoute(id);
    }


    @RequestMapping(path = "/{id}/plan", method = RequestMethod.GET)
    public ResponseEntity<Object> getRoutePlan(@PathVariable Integer id) throws URISyntaxException {
        log.debug("Route plan requested. Route id: " + id);
        RoutePlanGenerationStatus status = routePlanQueueService.getRoutePlanStatusByRouteId(id);
        if (RoutePlanGenerationStatus.Status.COMPLETED.equals(status.getStatus())) {
            return ResponseEntity.ok().body(routeService.getRoutePlan(id));
        } else {
            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .location(new URI("/routePlanQueue/" + status.getQueueId()))
                    .body(null);
        }
    }
}
