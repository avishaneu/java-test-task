package com.avishaneu.testtasks.tls.core;

import com.avishaneu.testtasks.tls.core.tsp.TSPSolvingAlgorithm;
import com.avishaneu.testtasks.tls.dao.RouteDao;
import com.avishaneu.testtasks.tls.dao.RoutePlanQueueDao;
import com.avishaneu.testtasks.tls.model.Location;
import com.avishaneu.testtasks.tls.model.RoutePlanGenerationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by avishaneu on 7/25/17.
 */
@Component
public class PlanGenerationLocalExecutor implements PlanGenerationExecutor {

    private static final Logger log = LoggerFactory.getLogger(PlanGenerationTask.class);

    private ThreadPoolTaskExecutor taskExecutor;
    private RouteDao routeDao;
    private RoutePlanQueueDao routePlanQueueDao;
    private TSPSolvingAlgorithm algorithm;

    private AtomicInteger queueIds = new AtomicInteger(0);

    private ConcurrentHashMap<Integer, Future> routePlanTasks = new ConcurrentHashMap<>();

    @Autowired
    public PlanGenerationLocalExecutor(ThreadPoolTaskExecutor taskExecutor,
                                       RouteDao routeDao, RoutePlanQueueDao routePlanQueueDao,
                                       TSPSolvingAlgorithm algorithm) {
        this.taskExecutor = taskExecutor;
        this.routeDao = routeDao;
        this.routePlanQueueDao = routePlanQueueDao;
        this.algorithm = algorithm;
    }

    public void generateForRoutes(List<Integer> routeIds) {
        for (int routeId : routeIds) {
            generateForRoute(routeId);
        }
    }

    public void generateForRoute(Integer routeId) {
        routePlanQueueDao.addRouteToGenerationQueue(routeId, queueIds.getAndIncrement());

        Future previousRouteTask = routePlanTasks.put(routeId,
                taskExecutor.submit(new PlanGenerationTask(routeId)));

        if (previousRouteTask != null)
        previousRouteTask.cancel(true);
    }

    public class PlanGenerationTask implements Runnable {

        private final Integer routeId;

        PlanGenerationTask(Integer routeId) {
            this.routeId = routeId;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            routePlanQueueDao.updateRouteGenerationStatus(routeId, RoutePlanGenerationStatus.Status.PROCESSING);
            List<Location> locations = routeDao.getRouteLocations(routeId);
            log.info("Started execution for route: " + routeId);

            locations = algorithm.solve(locations).getLocations();

            log.info("Completed execution for route: " + routeId +
                    ", with time: " + (System.currentTimeMillis() - start));
            routeDao.saveRoutePlan(locations);
            routePlanQueueDao.updateRouteGenerationStatus(routeId, RoutePlanGenerationStatus.Status.COMPLETED);
        }
    }
}
