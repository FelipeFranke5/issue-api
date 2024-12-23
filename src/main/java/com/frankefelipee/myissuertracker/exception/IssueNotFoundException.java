package com.frankefelipee.myissuertracker.exception;

public class IssueNotFoundException extends RuntimeException {

    public IssueNotFoundException(String id) {

        super("Could not find any Issue with given ID " + id);

    }
}
