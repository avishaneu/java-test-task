package com.avishaneu.testtasks.tls.core;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 */
public interface PlanGenerationExecutor {

    void generateForRoutes(List<Integer> routeId);

    void generateForRoute(Integer routeId);

}
