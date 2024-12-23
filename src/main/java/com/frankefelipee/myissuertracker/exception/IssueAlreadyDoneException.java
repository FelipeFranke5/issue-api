package com.frankefelipee.myissuertracker.exception;

public class IssueAlreadyDoneException extends RuntimeException {

    public IssueAlreadyDoneException() {

        super("This Issue is already marked as done.");

    }
}
