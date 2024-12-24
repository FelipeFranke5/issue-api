package com.frankefelipee.myissuertracker.exception;

public class CreateKeyException extends RuntimeException {

    public CreateKeyException(Exception e) {

        super("Could not create a new key. Message: " + e.getMessage());

    }

}
