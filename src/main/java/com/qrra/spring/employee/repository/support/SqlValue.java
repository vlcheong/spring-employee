package com.qrra.spring.employee.repository.support;

import com.qrra.spring.employee.util.DateUtils;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.RowId;
import java.sql.Timestamp;
import java.sql.Types;

import org.springframework.jdbc.core.SqlParameterValue;

public enum SqlValue {
    ;

    private static final Class<String> STRING = String.class;
    private static final Class<Double> DOUBLE = Double.class;
    private static final Class<Long> LONG = Long.class;
    private static final Class<Float> FLOAT = Float.class;
    private static final Class<Integer> INT = Integer.class;

    private static final Class<java.util.Date> DATE = java.util.Date.class;
    private static final Class<Date> SQL_DATE = Date.class;
    private static final Class<Timestamp> TIMESTAMP = Timestamp.class;
    private static final Class<Character> CHAR = Character.class;
    private static final Class<Boolean> BOOL = Boolean.class;
    private static final Class<Clob> CLOB = Clob.class;
    private static final Class<Blob> BLOB = Blob.class;
    private static final Class<RowId> ROW_ID = RowId.class;

    public static SqlParameterValue value(String str) {
        return new SqlParameterValue(Types.VARCHAR, str);
    }

    public static SqlParameterValue value(int val) {
        return value(Integer.valueOf(val));
    }
    public static SqlParameterValue value(Integer val) {
        return new SqlParameterValue(Types.INTEGER, val);
    }

    public static SqlParameterValue value(float val) {
        return value(Float.valueOf(val));
    }
    public static SqlParameterValue value(Float val) {
        return new SqlParameterValue(Types.FLOAT, val);
    }

    public static SqlParameterValue value(long val) {
        return value(Long.valueOf(val));
    }
    public static SqlParameterValue value(Long val) {
        return new SqlParameterValue(Types.BIGINT, val);
    }

    public static SqlParameterValue value(double val) {
        return value(Double.valueOf(val));
    }
    public static SqlParameterValue value(Double val) {
        return new SqlParameterValue(Types.DOUBLE, Double.valueOf(val));
    }

    public static SqlParameterValue value(java.util.Date date) {
        return value(DateUtils.toSqlDate(date));
    }
    public static SqlParameterValue value(Date date) {
        return new SqlParameterValue(Types.DATE, date);
    }

    public static SqlParameterValue value(Timestamp timestamp) {
        return new SqlParameterValue(Types.TIMESTAMP, timestamp);
    }

    public static SqlParameterValue value(boolean bool) {
        return value(Boolean.valueOf(bool));
    }
    public static SqlParameterValue value(Boolean bool) {
        return new SqlParameterValue(Types.BIT, bool);
    }

    public static SqlParameterValue value(RowId rowId) {
        return new SqlParameterValue(Types.ROWID, rowId);
    }

    public static SqlParameterValue value(Object obj) {
        if (null == obj) {
            return value((String) null);
        }
        final Class<?> clazz = obj.getClass();
        if (clazz == STRING) {
            return value((String) obj);
        }
        if (clazz == DOUBLE) {
            return value((Double) obj);
        }
        if (clazz == LONG) {
            return value((Long) obj);
        }
        if (clazz == FLOAT) {
            return value((Float) obj);
        }
        if (clazz == INT) {
            return value((Integer) obj);
        }
        if (clazz == DATE) {
            return value((java.util.Date) obj);
        }
        if (clazz == SQL_DATE) {
            return value((Date) obj);
        }
        if (clazz == TIMESTAMP) {
            return value((Timestamp) obj);
        }
        if (clazz == CHAR) {
            return value((Character) obj);
        }
        if (clazz == BOOL) {
            return value((Boolean) obj);
        }
        if (clazz == CLOB) {
            return value((Clob) obj);
        }
        if (clazz == BLOB) {
            return value((Blob) obj);
        }
        if (clazz == ROW_ID) {
            return value((RowId) obj);
        }
        throw new IllegalArgumentException("Unsupported class " + clazz);
    }
}