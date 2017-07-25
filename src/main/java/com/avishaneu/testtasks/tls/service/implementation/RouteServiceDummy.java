package com.avishaneu.testtasks.tls.service.implementation;

import com.avishaneu.testtasks.tls.dao.RouteDao;
import com.avishaneu.testtasks.tls.model.Route;
import com.avishaneu.testtasks.tls.model.RoutePlan;
import com.avishaneu.testtasks.tls.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by avishaneu on 7/25/17.
 */
@Service
public class RouteServiceDummy implements RouteService {

    private static final Logger log = LoggerFactory.getLogger(RouteServiceDummy.class);

    private RouteDao routeDao;

    @Autowired
    public RouteServiceDummy(RouteDao routeDao) {
        this.routeDao = routeDao;
    }

    public Route createRoute(Route route){
        route = routeDao.createRoute(route);
        //todo generate plan
        return route;
    }

    public Route getRoute(Integer id){
        return  routeDao.getRoute(id);
    }

    public void updateRoute(Integer id, Route route){
        route.setId(id);
        routeDao.updateRoute(route);
        //todo generate plan
    }

    public void deleteRoute(Integer id){
        routeDao.deleteRoute(id);
    }

    public RoutePlan getRoutePlan(Integer id) {
        return new RoutePlan(routeDao.getRoutePlan(id));
    }

}
