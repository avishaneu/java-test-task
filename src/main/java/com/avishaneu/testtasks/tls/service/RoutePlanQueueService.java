package com.avishaneu.testtasks.tls.service;


import com.avishaneu.testtasks.tls.model.RoutePlanGenerationStatus;

/**
 * Created by avishaneu on 7/25/17.
 */
public interface RoutePlanQueueService {

    RoutePlanGenerationStatus getRoutePlanStatusByRouteId(Integer routeId);

    RoutePlanGenerationStatus getRoutePlanStatusByQueueId(Integer queueId);
}
