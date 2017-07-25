package com.avishaneu.testtasks.tls.service.implementation;


import com.avishaneu.testtasks.tls.model.RoutePlanGenerationStatus;
import com.avishaneu.testtasks.tls.service.RoutePlanQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.avishaneu.testtasks.tls.model.RoutePlanGenerationStatus.Status.PENDING;

/**
 * Created by avishaneu on 7/25/17.
 */
@Service
public class RoutePlanQueueServiceDummy implements RoutePlanQueueService {

    private static final Logger log = LoggerFactory.getLogger(RoutePlanQueueServiceDummy.class);

    public RoutePlanGenerationStatus getRoutePlanStatusByRouteId(Integer routeId) {
        return new RoutePlanGenerationStatus(PENDING);
    }

    public RoutePlanGenerationStatus getRoutePlanStatusByQueueId(Integer queueId) {
        return new RoutePlanGenerationStatus(PENDING);
    }
}
