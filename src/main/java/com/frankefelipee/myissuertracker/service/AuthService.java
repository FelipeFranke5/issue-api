package com.frankefelipee.myissuertracker.service;

import java.time.Instant;
import java.util.Optional;

import com.frankefelipee.myissuertracker.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.frankefelipee.myissuertracker.entity.User;
import com.frankefelipee.myissuertracker.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    private User getValidUser(AuthRequest request, BCryptPasswordEncoder bCryptEncoder) {

        Optional<User> user = userRepository.findByName(request.name());

        if ((user.isEmpty()) || (!user.get().isLoginOk(request, bCryptEncoder))) {

            return null;

        }

        return user.get();

    }

    public String getValidToken(AuthRequest request, JwtEncoder encoder, BCryptPasswordEncoder bCryptEncoder) {

        User user = this.getValidUser(request, bCryptEncoder);
        Instant now = Instant.now();
        long expiresIn = 500L;

        if (user == null) {

            return null;

        }

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("myissuertracker")
                .subject(user.getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

}
