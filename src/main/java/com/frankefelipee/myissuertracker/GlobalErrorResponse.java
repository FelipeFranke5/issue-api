package com.frankefelipee.myissuertracker;

import java.time.ZonedDateTime;

public record GlobalErrorResponse(String message, String detail, ZonedDateTime time) {
}
