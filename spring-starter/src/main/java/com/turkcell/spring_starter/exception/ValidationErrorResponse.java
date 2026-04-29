package com.turkcell.spring_starter.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ValidationErrorResponse {
    
    private String title;
    private String type;
    private Map<String, List<String>> errors;
    private LocalDateTime timestamp;

    public ValidationErrorResponse(Map<String, List<String>> errors) {
        this.title = "Doğrulama Hatası";
        this.type = "VALIDATION_ERROR";
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
