package com.avishaneu.testtasks.tls.dao;

import com.avishaneu.testtasks.tls.model.RoutePlanGenerationStatus;

/**
 * Created by avishaneu on 7/25/17.
 */
public interface RoutePlanQueueDao {

    RoutePlanGenerationStatus getRoutePlanStatusByRouteId(Integer routeId);

    RoutePlanGenerationStatus getRoutePlanStatusByQueueId(Integer queueId);

    void addRouteToGenerationQueue(Integer routeID, Integer queueId);

    void updateRouteGenerationStatus(Integer routeId, RoutePlanGenerationStatus.Status status);
}
