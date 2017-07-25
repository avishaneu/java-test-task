package com.avishaneu.testtasks.tls.service.implementation;

import com.avishaneu.testtasks.tls.dao.LocationDao;
import com.avishaneu.testtasks.tls.model.Location;
import com.avishaneu.testtasks.tls.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 */
@Service
public class LocationServiceDummy implements LocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationServiceDummy.class);

    private LocationDao locationDao;

    @Autowired
    public LocationServiceDummy(@Qualifier("locationDaoH2") LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    public Location createLocation(Location location){
        return locationDao.createLocation(location);
    }

    public Location getLocation(Integer id){
        return locationDao.getLocation(id);
    }

    public void updateLocation(Integer id, Location location){
        List<Integer> routes = locationDao.getLocationRoutes(id);
        location.setId(id);
        locationDao.updateLocation(location);
        //todo generate plan
    }

    public void deleteLocation(Integer id){
        List<Integer> routes = locationDao.getLocationRoutes(id);
        locationDao.deleteLocation(id);
        //todo generate plan
    }
}
