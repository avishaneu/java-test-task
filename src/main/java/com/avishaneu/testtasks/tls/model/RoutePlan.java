package com.avishaneu.testtasks.tls.model;

import java.util.List;

/**
 * Created by avishaneu on 7/25/17.
 */
public class RoutePlan {

    private List<Integer> plan;


    public RoutePlan() {
    }

    public RoutePlan(List<Integer> plan) {
        this.plan = plan;
    }

    public List<Integer> getPlan() {
        return plan;
    }

    public void setPlan(List<Integer> plan) {
        this.plan = plan;
    }

}
