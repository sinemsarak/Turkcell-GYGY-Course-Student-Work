package com.turkcell.spring_cqrs.application.features.user.rule;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.turkcell.spring_cqrs.core.security.exception.AuthenticationException;
import com.turkcell.spring_cqrs.domain.User;
import com.turkcell.spring_cqrs.persistence.repository.UserRepository;

@Component
public class UserBusinessRules {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserBusinessRules(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void userWithSameEmailMustNotExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("A user with this email already exists");
        }
    }

    public User findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Invalid credentials"));
    }

    public void passwordMustMatch(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new AuthenticationException("Invalid credentials");
        }
    }
}
