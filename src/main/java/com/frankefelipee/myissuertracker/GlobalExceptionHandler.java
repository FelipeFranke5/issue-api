package com.frankefelipee.myissuertracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(NoHandlerFoundException e) {

        ResponseEntity<?> response = new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        return response;

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentNotValidException(MethodArgumentNotValidException e) {

        ResponseEntity<?> response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        return response;

    }

}
