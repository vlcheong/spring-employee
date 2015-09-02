package com.qrra.spring.employee.web.support;

public enum ActionCode {
    NEW("New"),
    VIEW("View"),
    EDIT("Edit");

    public static final String ACTION = "action";
    public static final String ERRORS = "errors";

    private final String name;

    ActionCode(String name) {
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