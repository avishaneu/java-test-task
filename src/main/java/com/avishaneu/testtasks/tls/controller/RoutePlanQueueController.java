package com.avishaneu.testtasks.tls.controller;

import com.avishaneu.testtasks.tls.model.RoutePlanGenerationStatus;
import com.avishaneu.testtasks.tls.service.RoutePlanQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by avishaneu on 7/25/17.
 */
@RestController
@RequestMapping("/routePlanQueue")
public class RoutePlanQueueController {

    private static final Logger log = LoggerFactory.getLogger(RoutePlanQueueController.class);

    private RoutePlanQueueService routePlanQueueService;

    @Autowired
    public RoutePlanQueueController(RoutePlanQueueService routePlanQueueService) {
        this.routePlanQueueService = routePlanQueueService;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRoutePlanStatus(@PathVariable Integer id) throws URISyntaxException {
        log.debug("Route plan generation status requested. Plan generation id: " + id);

        RoutePlanGenerationStatus status = routePlanQueueService.getRoutePlanStatusByQueueId(id);
        if (RoutePlanGenerationStatus.Status.COMPLETED.equals(status.getStatus())) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .location(new URI("/routes/" + status.getRouteId() + "/plan"))
                    .body(null);
        } else {
            return ResponseEntity.ok().body(status);
        }
    }
}
