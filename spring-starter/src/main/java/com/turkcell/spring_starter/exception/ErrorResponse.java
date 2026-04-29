package com.turkcell.spring_starter.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    
    private String title;
    private String type;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String title, String type, String message) {
        this.title = title;
        this.type = type;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
