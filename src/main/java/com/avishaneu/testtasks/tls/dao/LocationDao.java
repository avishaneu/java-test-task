package com.avishaneu.testtasks.tls.dao;


import com.avishaneu.testtasks.tls.model.Location;
import com.avishaneu.testtasks.tls.utils.NonexistentObjectRequested;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 */
public interface LocationDao {

    Location createLocation(Location location);

    Location getLocation(Integer id);

    void updateLocation(Location location);

    void deleteLocation(Integer id);

    List<Integer> getLocationRoutes(Integer id);
}
