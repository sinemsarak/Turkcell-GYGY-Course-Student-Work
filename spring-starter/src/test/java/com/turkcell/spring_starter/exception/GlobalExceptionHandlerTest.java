package com.turkcell.spring_starter.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("UserAlreadyExistsException 409 CONFLICT döndürmeli")
    void handleUserAlreadyExistsException_shouldReturn409() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("test@example.com");
        
        ResponseEntity<ErrorResponse> response = handler.handleUserAlreadyExistsException(exception);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("USER_ALREADY_EXISTS", response.getBody().getType());
        assertEquals("Kullanıcı Zaten Mevcut", response.getBody().getTitle());
    }

    @Test
    @DisplayName("InvalidCredentialsException 401 UNAUTHORIZED döndürmeli")
    void handleInvalidCredentialsException_shouldReturn401() {
        InvalidCredentialsException exception = new InvalidCredentialsException();
        
        ResponseEntity<ErrorResponse> response = handler.handleInvalidCredentialsException(exception);
        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INVALID_CREDENTIALS", response.getBody().getType());
        assertEquals("Geçersiz Kimlik Bilgileri", response.getBody().getTitle());
    }

    @Test
    @DisplayName("ResourceNotFoundException 404 NOT_FOUND döndürmeli")
    void handleResourceNotFoundException_shouldReturn404() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Kullanıcı", 1L);
        
        ResponseEntity<ErrorResponse> response = handler.handleResourceNotFoundException(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("RESOURCE_NOT_FOUND", response.getBody().getType());
        assertEquals("Kaynak Bulunamadı", response.getBody().getTitle());
    }

    @Test
    @DisplayName("RuntimeException 500 INTERNAL_SERVER_ERROR döndürmeli")
    void handleRuntimeException_shouldReturn500() {
        RuntimeException exception = new RuntimeException("Beklenmeyen bir hata oluştu");
        
        ResponseEntity<ErrorResponse> response = handler.handleRuntimeException(exception);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INTERNAL_ERROR", response.getBody().getType());
        assertEquals("Beklenmeyen Hata", response.getBody().getTitle());
    }

    @Test
    @DisplayName("MethodArgumentNotValidException 400 BAD_REQUEST döndürmeli")
    void handleValidationException_shouldReturn400() {
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        
        FieldError fieldError1 = new FieldError("object", "email", "E-posta boş olamaz");
        FieldError fieldError2 = new FieldError("object", "password", "Şifre çok kısa");
        
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        
        ResponseEntity<ValidationErrorResponse> response = handler.handleValidationException(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("VALIDATION_ERROR", response.getBody().getType());
        
        Map<String, List<String>> errors = response.getBody().getErrors();
        assertTrue(errors.containsKey("email"));
        assertTrue(errors.containsKey("password"));
    }

    @Test
    @DisplayName("Aynı field için birden fazla hata mesajı gruplanmalı")
    void handleValidationException_shouldGroupMultipleErrorsForSameField() {
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        
        FieldError fieldError1 = new FieldError("object", "password", "Şifre çok kısa");
        FieldError fieldError2 = new FieldError("object", "password", "Şifre özel karakter içermeli");
        
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        
        ResponseEntity<ValidationErrorResponse> response = handler.handleValidationException(exception);
        
        Map<String, List<String>> errors = response.getBody().getErrors();
        assertEquals(1, errors.size());
        assertEquals(2, errors.get("password").size());
    }
}
