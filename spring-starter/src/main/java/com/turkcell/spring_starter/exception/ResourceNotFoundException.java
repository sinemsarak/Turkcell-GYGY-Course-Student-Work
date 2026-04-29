package com.turkcell.spring_starter.exception;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(
            "Kaynak Bulunamadı",
            "RESOURCE_NOT_FOUND",
            resourceName + " bulunamadı: " + identifier
        );
    }

    public ResourceNotFoundException(String resourceName) {
        super(
            "Kaynak Bulunamadı",
            "RESOURCE_NOT_FOUND",
            resourceName + " bulunamadı"
        );
    }
}
