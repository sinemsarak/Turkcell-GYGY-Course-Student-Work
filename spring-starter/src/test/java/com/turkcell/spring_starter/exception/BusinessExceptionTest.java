package com.turkcell.spring_starter.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BusinessExceptionTest {

    @Test
    @DisplayName("UserAlreadyExistsException doğru değerlerle oluşturulmalı")
    void userAlreadyExistsException_shouldHaveCorrectValues() {
        String email = "test@example.com";
        
        UserAlreadyExistsException exception = new UserAlreadyExistsException(email);
        
        assertEquals("Kullanıcı Zaten Mevcut", exception.getTitle());
        assertEquals("USER_ALREADY_EXISTS", exception.getType());
        assertTrue(exception.getMessage().contains(email));
    }

    @Test
    @DisplayName("InvalidCredentialsException doğru değerlerle oluşturulmalı")
    void invalidCredentialsException_shouldHaveCorrectValues() {
        InvalidCredentialsException exception = new InvalidCredentialsException();
        
        assertEquals("Geçersiz Kimlik Bilgileri", exception.getTitle());
        assertEquals("INVALID_CREDENTIALS", exception.getType());
        assertEquals("E-posta veya şifre hatalı", exception.getMessage());
    }

    @Test
    @DisplayName("ResourceNotFoundException kaynak adı ve id ile oluşturulmalı")
    void resourceNotFoundException_withResourceNameAndId_shouldHaveCorrectValues() {
        String resourceName = "Kullanıcı";
        Long id = 123L;
        
        ResourceNotFoundException exception = new ResourceNotFoundException(resourceName, id);
        
        assertEquals("Kaynak Bulunamadı", exception.getTitle());
        assertEquals("RESOURCE_NOT_FOUND", exception.getType());
        assertTrue(exception.getMessage().contains(resourceName));
        assertTrue(exception.getMessage().contains(id.toString()));
    }

    @Test
    @DisplayName("ResourceNotFoundException sadece kaynak adı ile oluşturulmalı")
    void resourceNotFoundException_withOnlyResourceName_shouldHaveCorrectValues() {
        String resourceName = "Ürün";
        
        ResourceNotFoundException exception = new ResourceNotFoundException(resourceName);
        
        assertEquals("Kaynak Bulunamadı", exception.getTitle());
        assertEquals("RESOURCE_NOT_FOUND", exception.getType());
        assertTrue(exception.getMessage().contains(resourceName));
    }

    @Test
    @DisplayName("BusinessException alt sınıfları RuntimeException'dan türemeli")
    void businessExceptions_shouldExtendRuntimeException() {
        assertTrue(RuntimeException.class.isAssignableFrom(BusinessException.class));
        assertTrue(BusinessException.class.isAssignableFrom(UserAlreadyExistsException.class));
        assertTrue(BusinessException.class.isAssignableFrom(InvalidCredentialsException.class));
        assertTrue(BusinessException.class.isAssignableFrom(ResourceNotFoundException.class));
    }
}
