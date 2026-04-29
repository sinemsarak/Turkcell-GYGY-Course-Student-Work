package com.turkcell.spring_starter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.turkcell.spring_starter.dto.LoginRequest;
import com.turkcell.spring_starter.dto.RegisterRequest;
import com.turkcell.spring_starter.entity.User;
import com.turkcell.spring_starter.exception.InvalidCredentialsException;
import com.turkcell.spring_starter.exception.UserAlreadyExistsException;
import com.turkcell.spring_starter.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    @DisplayName("registerUser metodu")
    class RegisterUserTests {

        @Test
        @DisplayName("E-posta zaten kayıtlıysa UserAlreadyExistsException fırlatmalı")
        void registerUser_whenEmailExists_shouldThrowUserAlreadyExistsException() {
            RegisterRequest request = new RegisterRequest();
            request.setEmail("existing@example.com");
            request.setPassword("password123");

            User existingUser = new User();
            existingUser.setEmail("existing@example.com");

            when(userRepository.findByEmail("existing@example.com"))
                .thenReturn(Optional.of(existingUser));

            UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.registerUser(request)
            );

            assertEquals("USER_ALREADY_EXISTS", exception.getType());
            assertTrue(exception.getMessage().contains("existing@example.com"));
            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        @DisplayName("E-posta mevcut değilse kullanıcı kaydedilmeli")
        void registerUser_whenEmailNotExists_shouldSaveUser() {
            RegisterRequest request = new RegisterRequest();
            request.setEmail("new@example.com");
            request.setPassword("password123");

            when(userRepository.findByEmail("new@example.com"))
                .thenReturn(Optional.empty());
            when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPassword");

            assertDoesNotThrow(() -> userService.registerUser(request));

            verify(userRepository).save(any(User.class));
            verify(passwordEncoder).encode("password123");
        }
    }

    @Nested
    @DisplayName("login metodu")
    class LoginTests {

        @Test
        @DisplayName("E-posta bulunamazsa InvalidCredentialsException fırlatmalı")
        void login_whenEmailNotFound_shouldThrowInvalidCredentialsException() {
            LoginRequest request = new LoginRequest();
            request.setEmail("notfound@example.com");
            request.setPassword("password123");

            when(userRepository.findByEmail("notfound@example.com"))
                .thenReturn(Optional.empty());

            InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> userService.login(request)
            );

            assertEquals("INVALID_CREDENTIALS", exception.getType());
        }

        @Test
        @DisplayName("Şifre yanlışsa InvalidCredentialsException fırlatmalı")
        void login_whenPasswordNotMatch_shouldThrowInvalidCredentialsException() {
            LoginRequest request = new LoginRequest();
            request.setEmail("user@example.com");
            request.setPassword("wrongPassword");

            User user = new User();
            user.setEmail("user@example.com");
            user.setPassword("encodedCorrectPassword");

            when(userRepository.findByEmail("user@example.com"))
                .thenReturn(Optional.of(user));
            when(passwordEncoder.matches("wrongPassword", "encodedCorrectPassword"))
                .thenReturn(false);

            InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> userService.login(request)
            );

            assertEquals("INVALID_CREDENTIALS", exception.getType());
        }

        @Test
        @DisplayName("Doğru bilgilerle giriş başarılı olmalı")
        void login_withCorrectCredentials_shouldReturnSuccessMessage() {
            LoginRequest request = new LoginRequest();
            request.setEmail("user@example.com");
            request.setPassword("correctPassword");

            User user = new User();
            user.setEmail("user@example.com");
            user.setPassword("encodedPassword");

            when(userRepository.findByEmail("user@example.com"))
                .thenReturn(Optional.of(user));
            when(passwordEncoder.matches("correctPassword", "encodedPassword"))
                .thenReturn(true);

            String result = userService.login(request);

            assertEquals("Giriş başarılı", result);
        }
    }
}
