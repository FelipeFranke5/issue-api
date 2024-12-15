package com.frankefelipee.myissuertracker.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.frankefelipee.myissuertracker.auth.AuthRequest;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class User {

    public boolean isLoginOk(AuthRequest authRequest, PasswordEncoder passwordEncoder) {

        return passwordEncoder.matches(authRequest.password(), this.getPassword());

    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column(unique = true)
    private String name;

    @Column
    private String password;

}
