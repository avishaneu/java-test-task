package com.avishaneu.testtasks.tls.service.implementation;


import com.avishaneu.testtasks.tls.dao.RoutePlanQueueDao;
import com.avishaneu.testtasks.tls.model.RoutePlanGenerationStatus;
import com.avishaneu.testtasks.tls.service.RoutePlanQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by avishaneu on 7/25/17.
 */
@Service
public class RoutePlanQueueServiceBasic implements RoutePlanQueueService {

    private static final Logger log = LoggerFactory.getLogger(RoutePlanQueueServiceBasic.class);

    private RoutePlanQueueDao routePlanQueueDao;

    @Autowired
    public RoutePlanQueueServiceBasic(RoutePlanQueueDao routePlanQueueDao) {
        this.routePlanQueueDao = routePlanQueueDao;
    }

    public RoutePlanGenerationStatus getRoutePlanStatusByRouteId(Integer routeId) {
        return routePlanQueueDao.getRoutePlanStatusByRouteId(routeId);
    }

    public RoutePlanGenerationStatus getRoutePlanStatusByQueueId(Integer queueId) {
        return routePlanQueueDao.getRoutePlanStatusByQueueId(queueId);
    }
}
