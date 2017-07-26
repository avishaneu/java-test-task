package com.avishaneu.testtasks.tls.dao;

import com.avishaneu.testtasks.tls.model.Location;
import com.avishaneu.testtasks.tls.model.Route;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 */
public interface RouteDao {

    Route createRoute(Route route);

    Route getRoute(Integer id);

    void updateRoute(Route route);

    void deleteRoute(Integer id);

    List<Integer> getRoutePlan(Integer id);

    List<Location> getRouteLocations(Integer id);

    void saveRoutePlan(Integer id, List<Location> locations);
}
