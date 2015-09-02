package com.qrra.spring.employee.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

public enum DateUtils {
    ;
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String DATE_TIME_FORMAT =
            String.format("%1$s %2$s", DATE_FORMAT, TIME_FORMAT);

    public static final String DATE_COMPARE_PATTERN = "yyyyMMdd";

    public static final String STRING_DATE_FORMAT = "%1$td/%1$tm/%1$tY %1$tH:%1$tM:%1$tS";

    public static String formatDate(Timestamp timestamp) {
        return (timestamp == null
                ? StringUtils.EMPTY
                        : DateFormatUtils.format(timestamp, DATE_FORMAT));
    }
    public static String formatDate(Date date) {
        return (date == null
                ? StringUtils.EMPTY
                        : DateFormatUtils.format(date, DATE_FORMAT));
    }
    public static String formatTime(Date date) {
        return (date == null
                ? StringUtils.EMPTY
                        : DateFormatUtils.format(date, TIME_FORMAT));
    }
    public static String formatDateTime(Date date) {
        return (date == null
                ? StringUtils.EMPTY
                        : DateFormatUtils.format(date, DATE_TIME_FORMAT));
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(pattern)) {
            return formatDate(date);
        }
        return DateFormatUtils.format(date, pattern);
    }
    public static String formatTime(Date date, String pattern) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(pattern)) {
            return formatTime(date);
        }
        return DateFormatUtils.format(date, pattern);
    }
    public static String formatDateTime(Date date, String pattern) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(pattern)) {
            return formatDateTime(date);
        }
        return DateFormatUtils.format(date, pattern);
    }

    public static String formatDate(long millis) {
        return DateFormatUtils.format(millis, DATE_FORMAT);
    }
    public static String formatTime(long millis) {
        return DateFormatUtils.format(millis, TIME_FORMAT);
    }
    public static String formatDateTime(long millis) {
        return DateFormatUtils.format(millis, DATE_TIME_FORMAT);
    }

    public static String formatDate(long millis, String pattern) {
        return (StringUtils.isBlank(pattern)
                ? formatDate(millis)
                        : DateFormatUtils.format(millis, pattern));
    }
    public static String formatTime(long millis, String pattern) {
        return (StringUtils.isBlank(pattern)
                ? formatTime(millis)
                        : DateFormatUtils.format(millis, pattern));
    }
    public static String formatDateTime(long millis, String pattern) {
        return (StringUtils.isBlank(pattern)
                ? formatDateTime(millis)
                        : DateFormatUtils.format(millis, pattern));
    }

    public static long getTimeMillis() {
        return System.currentTimeMillis();
    }

    public static Date parseDate(String source) {
        try {
            return FastDateFormat.getInstance(DATE_FORMAT).parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static Time parseTime(String source) {
        try {
            return new Time(FastDateFormat.getInstance(TIME_FORMAT).parse(source).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static Date parseDateTime(String source) {
        try {
            return FastDateFormat.getInstance(DATE_TIME_FORMAT).parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseDate(String source, String pattern) {
        try {
            return (StringUtils.isBlank(pattern)
                    ? parseDate(source)
                            : FastDateFormat.getInstance(pattern).parse(source));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static Time parseTime(String source, String pattern) {
        try {
            return (StringUtils.isBlank(pattern)
                    ? parseTime(source)
                            : new Time(FastDateFormat.getInstance(pattern).parse(source).getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static Date parseDateTime(String source, String pattern) {
        try {
            return (StringUtils.isBlank(pattern)
                    ? parseDateTime(source)
                            : FastDateFormat.getInstance(pattern).parse(source));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date toDate(java.sql.Date sqlDate) {
        return (sqlDate == null ? null : new Date(sqlDate.getTime()));
    }

    public static java.sql.Date toSqlDate(Date date) {
        return (date == null ? null : new java.sql.Date(date.getTime()));
    }

    public static Timestamp toTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Timestamp beginTimestamp(Timestamp timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return new Timestamp(cal.getTimeInMillis());
    }

    public static Timestamp endTimestamp(Timestamp timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return new Timestamp(cal.getTimeInMillis());
    }

    public static Date today() {
        return new Date();
    }

    public static Date yesterday() {
        return org.apache.commons.lang3.time.DateUtils.addDays(new Date(), -1);
    }

    public static String todayString() {
        return formatDate(today());
    }

    public static String yesterdayString() {
        return formatDate(yesterday());
    }

    public static Timestamp todayTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static int compareDate(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return 0;
        }
        return (NumberUtils.toInt(formatDate(date1, DATE_COMPARE_PATTERN)) -
                NumberUtils.toInt(formatDate(date2, DATE_COMPARE_PATTERN)));
    }

    public static String timeNow() {
        return formatTime(new Date());
    }

    public static String dateAndTimeNow() {
        return formatDateTime(new Date());
    }
}