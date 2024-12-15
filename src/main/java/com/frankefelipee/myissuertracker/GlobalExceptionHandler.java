package com.frankefelipee.myissuertracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(NoHandlerFoundException e) {

        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentNotValidException(MethodArgumentNotValidException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        return new ResponseEntity<>(
                new GlobalErrorResponse(
                        "Payload is required",
                        "Please provide a request body in order to proceed.",
                        ZonedDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );

    }

}
