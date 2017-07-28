package com.avishaneu.testtasks.tls.service;

import com.avishaneu.testtasks.tls.model.Route;
import com.avishaneu.testtasks.tls.model.RoutePlan;

/**
 * Created by avishaneu on 7/25/17.
 */
public interface RouteService {

    Route createRoute(Route route);

    Route getRoute(Integer id);

    void updateRoute(Integer id, Route route);

    void deleteRoute(Integer id);

    RoutePlan getRoutePlan(Integer id);
}