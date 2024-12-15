package com.frankefelipee.myissuertracker.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "salesForce cannot be null")
    @NotEmpty(message = "please provide the salesForce")
    @Size(message = "salesForce should be between 6-10 digits", min = 6, max = 10)
    private String salesForce;

    @NotNull(message = "The ticket cannot be null")
    @NotEmpty
    @Size(message = "The ticket should be between 4-15 characters", min = 4, max = 15)
    private String ticket;

    @NotNull(message = "The description cannot be null")
    @NotEmpty
    @Size(message = "The description should be between 10-100 characters", min = 10, max = 100)
    private String description;

    private boolean done = false;

}
