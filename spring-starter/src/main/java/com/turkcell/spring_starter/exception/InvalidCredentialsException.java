package com.turkcell.spring_starter.exception;

public class InvalidCredentialsException extends BusinessException {

    public InvalidCredentialsException() {
        super(
            "Geçersiz Kimlik Bilgileri",
            "INVALID_CREDENTIALS",
            "E-posta veya şifre hatalı"
        );
    }
}
