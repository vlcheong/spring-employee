package com.qrra.spring.employee.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class Page<T> {

    public static final int PAGE_SIZE = 10;

    private final int number; // Page number start from 1

    private final long total;

    private final List<T> content = new ArrayList<T>();

    public Page(int number, List<T> content, long total) {
        this.number = number;
        this.total = total;
        if (!CollectionUtils.isEmpty(content)) {
            this.content.addAll(content);
        }
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    public int getSize() {
        return PAGE_SIZE;
    }

    public long getTotal() {
        return total;
    }

    public int getCurrentPage() {
        return number;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) total / (double) PAGE_SIZE);
    }

    public boolean isFirst() {
        return number <= 1;
    }

    public boolean isLast() {
        return number >= getTotalPages();
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }
}