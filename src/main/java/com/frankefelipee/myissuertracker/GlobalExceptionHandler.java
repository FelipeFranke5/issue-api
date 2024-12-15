package com.frankefelipee.myissuertracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(NoHandlerFoundException e) {

        return new ResponseEntity<>(
                new GlobalErrorResponse(
                        "No Resource Found",
                        e.getMessage(),
                        ZonedDateTime.now()
                ),
                HttpStatus.NOT_FOUND
        );

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentNotValidException(MethodArgumentNotValidException e) {

        List<HashMap<String, String>> errors = new ArrayList<>();

        for (FieldError fieldError : e.getFieldErrors()) {

            HashMap<String, String> field = new HashMap<>();
            field.put("field", fieldError.getField());
            field.put("message", fieldError.getDefaultMessage());
            errors.add(field);

        }

        return new ResponseEntity<>(
                new InvalidArguentsDetail(
                        "Invalid Request Body",
                        e.getErrorCount(),
                        errors,
                        ZonedDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );

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

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<?> handleTransactionSystemException(TransactionSystemException e) {

        return new ResponseEntity<>(
                new GlobalErrorResponse(
                        "Database Level Validation Error",
                        "Could not proceed with persistance at DataBase level.",
                        ZonedDateTime.now()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

    }

}
