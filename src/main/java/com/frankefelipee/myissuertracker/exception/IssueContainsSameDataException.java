package com.frankefelipee.myissuertracker.exception;

public class IssueContainsSameDataException extends RuntimeException {

    public IssueContainsSameDataException() {

        super("You need to change at least one field.");

    }

}
