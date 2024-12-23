package com.frankefelipee.myissuertracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.frankefelipee.myissuertracker.request.AuthRequest;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
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
