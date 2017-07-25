package com.avishaneu.testtasks.tls.service.implementation;

import com.avishaneu.testtasks.tls.model.Route;
import com.avishaneu.testtasks.tls.model.RoutePlan;
import com.avishaneu.testtasks.tls.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by avishaneu on 7/25/17.
 */
@Service
public class RouteServiceDummy implements RouteService {

    private static final Logger log = LoggerFactory.getLogger(RouteServiceDummy.class);

    public Route createRoute(Route route){
        route.setId(1);
        return route;
    }

    public Route getRoute(Integer id){
        return new Route(id);
    }

    public void updateRoute(Integer id, Route route){
        route.setId(id);
    }

    public void deleteRoute(Integer id){}

    public RoutePlan getRoutePlan(Integer id) {
        return new RoutePlan();
    }

}
