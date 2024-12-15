package com.frankefelipee.myissuertracker;

import org.springframework.validation.FieldError;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

public record InvalidArguentsDetail(
        String message,
        int errorsCount,
        List<HashMap<String, String>> errors,
        ZonedDateTime time
) {}
