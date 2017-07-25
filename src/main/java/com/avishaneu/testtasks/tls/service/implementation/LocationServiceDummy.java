package com.avishaneu.testtasks.tls.service.implementation;

import com.avishaneu.testtasks.tls.model.Location;
import com.avishaneu.testtasks.tls.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by avishaneu on 7/25/17.
 */
@Service
public class LocationServiceDummy implements LocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationServiceDummy.class);

    public Location createLocation(Location location){
        location.setId(1);
        return location;
    }

    public Location getLocation(Integer id){
        return new Location(id);

    }

    public void updateLocation(Integer id, Location location){
        location.setId(id);
    }

    public void deleteLocation(Integer id){}
}
