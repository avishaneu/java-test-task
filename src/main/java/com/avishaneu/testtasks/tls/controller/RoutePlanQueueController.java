package com.avishaneu.testtasks.tls.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by avishaneu on 7/25/17.
 */
@RestController
@RequestMapping("/routePlanQueue")
public class RoutePlanQueueController {

    private static final Logger log = LoggerFactory.getLogger(RoutePlanQueueController.class);

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRoutePlanStatus(@PathVariable Integer id){
        log.debug("Route plan generation status requested. Plan generation id: " + id);
        return ResponseEntity.accepted().body("asdasdasdasd");
    }
}
