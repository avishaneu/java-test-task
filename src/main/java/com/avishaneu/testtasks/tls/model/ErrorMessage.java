package com.avishaneu.testtasks.tls.model;

import org.springframework.http.HttpStatus;

/**
 * Created by avishaneu on 7/25/2017.
 */
public class ErrorMessage {

    private int code;
    private String message;

    public ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorMessage(HttpStatus status){
        this(status.value(), status.getReasonPhrase());
    }

    public ErrorMessage(HttpStatus status, String message){
        this(status.value(), message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
