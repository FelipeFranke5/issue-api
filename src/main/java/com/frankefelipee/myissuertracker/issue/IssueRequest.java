package com.frankefelipee.myissuertracker.issue;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IssueRequest(
        @NotNull(message = "salesForce cannot be null")
        @NotEmpty(message = "please provide the salesForce")
        @Size(message = "salesForce should be between 6-10 digits", min = 6, max = 10)
        String salesForce,

        @NotNull(message = "The ticket cannot be null")
        @NotEmpty(message = "The ticket cannot be empty")
        @Size(message = "The ticket should be between 4-15 characters", min = 4, max = 15)
        String ticket,

        @NotNull(message = "The description cannot be null")
        @NotEmpty(message = "The description cannot be empty")
        @Size(message = "The description should be between 10-100 characters", min = 10, max = 100)
        String description
) {

}
