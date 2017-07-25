package com.avishaneu.testtasks.tls.controller;

import com.avishaneu.testtasks.tls.model.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by avishaneu on 7/25/2017.
 */
@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage noHandlerFound(NoHandlerFoundException e) {
        log.warn("Nonexistent resource requested: " + e.getHttpMethod() + e.getRequestURL());
        return new ErrorMessage(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage internalServerError(Exception e) {
        log.warn("Internal server error" , e);
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}