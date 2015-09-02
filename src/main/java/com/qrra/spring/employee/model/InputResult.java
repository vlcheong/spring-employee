package com.qrra.spring.employee.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class InputResult<T> {

    public static final String FIELD_ERROR = "fieldErrors";

    public static final String GLOBAL_ERROR = "globalErrors";

    public static final String ERROR = "errors";

    private T entity;

    private List<FieldError> fieldErrors;

    private List<String> globalErrors;

    public InputResult<T> addFieldError(String field, String message) {
        if (StringUtils.isNotBlank(field) &&
                StringUtils.isNotBlank(message)) {
            if (fieldErrors == null) {
                fieldErrors = new ArrayList<FieldError>();
            }
            fieldErrors.add(new FieldError(field, message));
        }
        return this;
    }

    public List<FieldError> getFieldErrors() {
        return (fieldErrors == null
                ? Collections.<FieldError>emptyList()
                        : Collections.unmodifiableList(fieldErrors));
    }

    public InputResult<T> addGlobalError(String message) {
        if (StringUtils.isNotBlank(message)) {
            if (globalErrors == null) {
                globalErrors = new ArrayList<String>();
            }
            globalErrors.add(message);
        }
        return this;
    }

    public List<String> getGlobalErrors() {
        return (globalErrors == null
                ? Collections.<String>emptyList()
                        : Collections.unmodifiableList(globalErrors));
    }

    public boolean hasFieldErrors() {
        return !CollectionUtils.isEmpty(fieldErrors);
    }

    public boolean hasGlobalErrors() {
        return !CollectionUtils.isEmpty(globalErrors);
    }

    public boolean hasErrors() {
        return (hasFieldErrors() || hasGlobalErrors());
    }

    public List<String> getAllMessages() {
        final int fieldErrorCount = fieldErrors != null ? fieldErrors.size() : 0;
        final int globalErrorCount = globalErrors != null ? globalErrors.size() : 0;
        final List<String> all = new ArrayList<String>(fieldErrorCount + globalErrorCount);
        if (fieldErrorCount > 0) {
            for (int i = 0; i < fieldErrorCount; i++) {
                all.add(fieldErrors.get(i).toString());
            }
        }
        if (globalErrorCount > 0) {
            for (int i = 0; i < globalErrorCount; i++) {
                all.add(globalErrors.get(i));
            }
        }
        return Collections.unmodifiableList(all);
    }

    public void setObject(T entity) {
        this.entity = entity;
    }

    public T getObject() {
        return entity;
    }
}