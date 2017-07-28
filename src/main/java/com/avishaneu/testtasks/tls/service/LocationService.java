package com.avishaneu.testtasks.tls.service;


import com.avishaneu.testtasks.tls.model.Location;

/**
 * Created by avishaneu on 7/25/17.
 */
public interface LocationService {

    Location createLocation(Location location);

    Location getLocation(Integer id);

    void updateLocation(Integer id, Location location);

    void deleteLocation(Integer id);
}
