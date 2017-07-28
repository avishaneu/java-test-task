package com.avishaneu.testtasks.tls.utils;

/**
 * Created by avishaneu on 7/26/2017.
 */
public class NonexistentObjectRequested extends RuntimeException {

    String objectName;
    Integer objectId;
    Throwable cause;

    public NonexistentObjectRequested(String objectName, Integer objectId, Throwable cause) {
        this.objectName = objectName;
        this.objectId = objectId;
        this.cause = cause;
    }

    @Override
    public String getMessage(){
        return "Unknown " + objectName + " "  + objectId;
    }
}
