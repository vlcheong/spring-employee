package com.qrra.spring.employee.web.support;

public enum StatusCode {
    OK("OK"),
    ERROR("ERROR");

    private final String name;

    StatusCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}