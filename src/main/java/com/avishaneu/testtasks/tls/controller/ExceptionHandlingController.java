package com.avishaneu.testtasks.tls.controller;

import com.avishaneu.testtasks.tls.model.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorMessage validationFailedException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        log.warn("Validation failed: " + e.getMessage());
        return new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY,
                "Validation failed for " + bindingResult.getFieldErrors()
                        .stream()
                        .map(error -> error.getObjectName() + "." + error.getField())
                        .collect(Collectors.joining(", ")));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage jsonParseException(HttpMessageNotReadableException e) {
        log.warn("Unreadable input: "  + e.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST, "Invalid input data");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage dataIntegrityViolationException(DataIntegrityViolationException e) {
        log.warn("Data integrity violation: " +  e.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST, "Data integrity violation");
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage internalServerError(Exception e) {
        log.warn("Internal server error" , e);
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}