package com.engrkirky.moonlight.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage badRequestExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage notFoundExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(), request.getDescription(false));
    }
}
