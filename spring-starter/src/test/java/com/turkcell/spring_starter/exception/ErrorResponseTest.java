package com.turkcell.spring_starter.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorResponseTest {

    @Test
    @DisplayName("ErrorResponse doğru şekilde oluşturulmalı")
    void errorResponse_shouldBeCreatedCorrectly() {
        String title = "Test Title";
        String type = "TEST_ERROR";
        String message = "Test message";
        
        ErrorResponse response = new ErrorResponse(title, type, message);
        
        assertEquals(title, response.getTitle());
        assertEquals(type, response.getType());
        assertEquals(message, response.getMessage());
        assertNotNull(response.getTimestamp());
        assertTrue(response.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    @DisplayName("ErrorResponse setter'ları çalışmalı")
    void errorResponse_setters_shouldWork() {
        ErrorResponse response = new ErrorResponse("Old", "OLD_TYPE", "Old message");
        
        response.setTitle("New Title");
        response.setType("NEW_TYPE");
        response.setMessage("New message");
        LocalDateTime newTime = LocalDateTime.now();
        response.setTimestamp(newTime);
        
        assertEquals("New Title", response.getTitle());
        assertEquals("NEW_TYPE", response.getType());
        assertEquals("New message", response.getMessage());
        assertEquals(newTime, response.getTimestamp());
    }

    @Test
    @DisplayName("ValidationErrorResponse doğru şekilde oluşturulmalı")
    void validationErrorResponse_shouldBeCreatedCorrectly() {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("email", List.of("E-posta boş olamaz"));
        errors.put("password", List.of("Şifre en az 6 karakter olmalı", "Şifre özel karakter içermeli"));
        
        ValidationErrorResponse response = new ValidationErrorResponse(errors);
        
        assertEquals("Doğrulama Hatası", response.getTitle());
        assertEquals("VALIDATION_ERROR", response.getType());
        assertEquals(2, response.getErrors().size());
        assertEquals(1, response.getErrors().get("email").size());
        assertEquals(2, response.getErrors().get("password").size());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("ValidationErrorResponse setter'ları çalışmalı")
    void validationErrorResponse_setters_shouldWork() {
        Map<String, List<String>> initialErrors = new HashMap<>();
        ValidationErrorResponse response = new ValidationErrorResponse(initialErrors);
        
        Map<String, List<String>> newErrors = new HashMap<>();
        newErrors.put("field", List.of("error"));
        
        response.setTitle("New Title");
        response.setType("NEW_TYPE");
        response.setErrors(newErrors);
        LocalDateTime newTime = LocalDateTime.now();
        response.setTimestamp(newTime);
        
        assertEquals("New Title", response.getTitle());
        assertEquals("NEW_TYPE", response.getType());
        assertEquals(newErrors, response.getErrors());
        assertEquals(newTime, response.getTimestamp());
    }
}
