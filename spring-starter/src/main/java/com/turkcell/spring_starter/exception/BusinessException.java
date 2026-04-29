package com.turkcell.spring_starter.exception;

public abstract class BusinessException extends RuntimeException {
    
    private final String title;
    private final String type;

    protected BusinessException(String title, String type, String message) {
        super(message);
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
}
