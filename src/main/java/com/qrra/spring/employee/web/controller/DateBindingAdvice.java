package com.qrra.spring.employee.web.controller;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.qrra.spring.employee.web.support.DatePropertyEditor;

@ControllerAdvice
public class DateBindingAdvice {

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DatePropertyEditor());
    }
}