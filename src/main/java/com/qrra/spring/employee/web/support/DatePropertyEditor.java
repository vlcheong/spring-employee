package com.qrra.spring.employee.web.support;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import com.qrra.spring.employee.util.DateUtils;

public class DatePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (isNotBlank(text)) {
            try {
                setValue(DateUtils.parseDate(text));
            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to parse date '" + text + "' with pattern '" + DateUtils.DATE_FORMAT + "'", e);
            }
        }
    }

    @Override
    public String getAsText() {
        Object obj = getValue();
        if (obj != null &&
                obj instanceof Date) {
            return DateUtils.formatDate((Date) obj);
        }
        return EMPTY;
    }
}