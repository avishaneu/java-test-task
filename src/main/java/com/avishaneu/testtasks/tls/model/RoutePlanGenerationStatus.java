package com.avishaneu.testtasks.tls.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by avishaneu on 7/25/17.
 */
public class RoutePlanGenerationStatus {

    private Integer routeId;
    private Status status;
    private Integer queueId;

    public RoutePlanGenerationStatus() {
    }

    public RoutePlanGenerationStatus(Status status) {
        this.status = status;
    }

    public RoutePlanGenerationStatus(Integer routeId, Status status, Integer queueId) {
        this.routeId = routeId;
        this.status = status;
        this.queueId = queueId;
    }

    public RoutePlanGenerationStatus(Integer routeId, int statusCode, Integer queueId) {
        this(routeId, Status.fromCode(statusCode), queueId);
    }

    @Transient
    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Transient
    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public enum Status {
        @JsonProperty("unknown")
        UNKNOWN(0),
        @JsonProperty("pending")
        PENDING(1),
        @JsonProperty("processing")
        PROCESSING(2),
        @JsonProperty("done")
        COMPLETED(3);

        private int code;

        Status(int code){
            this.code = code;
        }

        static Map<Integer, Status> lookup = new HashMap<>();

        static {
            for (Status s : Status.values()) {
                lookup.put(s.code, s);
            }
        }

        public static Status fromCode(int code){
            if (lookup.containsKey(code))
                return lookup.get(code);
            return UNKNOWN;
        }

        public int getCode(){
            return code;
        }
    }
}


