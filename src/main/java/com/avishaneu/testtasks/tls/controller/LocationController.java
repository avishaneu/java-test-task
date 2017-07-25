package com.avishaneu.testtasks.tls.controller;

import com.avishaneu.testtasks.tls.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by avishaneu on 7/25/2017.
 */
@RestController
@RequestMapping("/locations")
public class LocationController {

    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Location createLocation(@Valid @RequestBody Location location){
        log.debug("Location creation requested. Location to be created: " + location);
        location.setId(1);
        return location;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Location getLocation(@PathVariable Integer id){
        log.debug("Location details requested. Location id: " + id);
        return new Location(id);

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLocation(@PathVariable Integer id, @RequestBody Location location){
        log.debug("Location update requested. Location id: " + id + " . New location details: " + location);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable Integer id){
        log.debug("Location removal requested. Location id: " + id);
    }
}
