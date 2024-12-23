package com.frankefelipee.myissuertracker.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {

        super("Could not find any User with given ID " + id);

    }

}
