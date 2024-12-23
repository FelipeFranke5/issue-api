package com.frankefelipee.myissuertracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.frankefelipee.myissuertracker.request.IssueRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Entity
@Table(name = "issues")
public class Issue {

    public static IssueRequest mapIssueToRequest(Issue issue) {

        return new IssueRequest(issue.getSalesForce(), issue.getTicket(), issue.getDescription());

    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "salesForce cannot be null")
    @NotEmpty(message = "please provide the salesForce")
    @Size(message = "salesForce should be between 6-10 digits", min = 6, max = 10)
    private String salesForce;

    @NotNull(message = "The ticket cannot be null")
    @NotEmpty(message = "The ticket cannot be empty")
    @Size(message = "The ticket should be between 4-15 characters", min = 4, max = 15)
    private String ticket;

    @NotNull(message = "The description cannot be null")
    @NotEmpty(message = "The description cannot be empty")
    @Size(message = "The description should be between 10-100 characters", min = 10, max = 100)
    private String description;

    private boolean done = false;

}
