package com.frankefelipee.myissuertracker.exception;

import java.time.ZonedDateTime;

public record GlobalErrorResponse(String message, String detail, ZonedDateTime time) {
}
