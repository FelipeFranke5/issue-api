package com.frankefelipee.myissuertracker.issue;

public class IssueAlreadyDoneException extends RuntimeException {

    public IssueAlreadyDoneException() {

        super("This Issue is already marked as done.");

    }
}
