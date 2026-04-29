package com.turkcell.spring_starter.exception;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException(String email) {
        super(
            "Kullanıcı Zaten Mevcut",
            "USER_ALREADY_EXISTS",
            "Bu e-posta adresi zaten kayıtlı: " + email
        );
    }
}
