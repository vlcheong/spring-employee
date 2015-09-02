package com.qrra.spring.employee.model;

public class FieldError {

    private final String field;

    private final String message;

    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return field + " - " + message;
    }
}