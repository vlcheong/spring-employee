package com.qrra.spring.employee.web.support;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

public final class ResponseInfo<T> {

    private final Builder<T> builder;

    private ResponseInfo(Builder<T> builder) {
        this.builder = builder;
    }

    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    public static class Builder<T> {
        private StatusCode statusCode;
        private Collection<String> messages;
        private T entity;

        public Builder() {
            messages = new LinkedHashSet<String>();
        }

        public Builder<T> statusCode(StatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<T> message(String msg) {
            messages.add(msg);
            return this;
        }

        public Builder<T> messages(Collection<String> messages) {
            this.messages.addAll(messages);
            return this;
        }

        public Builder<T> entity(T entity) {
            this.entity = entity;
            return this;
        }

        public ResponseInfo<T> build() {
            return new ResponseInfo<T>(this);
        }
    }

    public StatusCode getStatusCode() {
        return builder.statusCode;
    }

    public Collection<String> getMessages() {
        return (builder.messages == null
                ? Collections.<String>emptyList()
                        : Collections.unmodifiableCollection(builder.messages));
    }

    public T getEntity() {
        return builder.entity;
    }
}