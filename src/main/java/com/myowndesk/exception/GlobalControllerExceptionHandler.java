package com.myowndesk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.myowndesk.domain.GlobalError;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalError constraintViolationException(ConstraintViolationException ex) {
        return new GlobalError(HttpStatus.BAD_REQUEST, "We can't process this request due to Bad Request", ex.getMessage());
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GlobalError noHandlerFoundException(Exception ex) {
        return new GlobalError(HttpStatus.NOT_FOUND,"Request Not Found, So please try again after some time", ex.getMessage());
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GlobalError unknownException(Exception ex) {
        return new GlobalError(HttpStatus.INTERNAL_SERVER_ERROR,"We can't process this request due to Server under maintenance", ex.getMessage());
    }
}