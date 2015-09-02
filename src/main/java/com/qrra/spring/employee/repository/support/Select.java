package com.qrra.spring.employee.repository.support;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.SqlParameterValue;

import com.qrra.spring.employee.model.Page;

public final class Select {

    public static final String SELECT_SQL =
            // ORACLE
            "SELECT * FROM " + "(SELECT ROWNUM AS R_IDX, a.* FROM ({0}) a) " +
            "WHERE R_IDX > ? AND ROWNUM <= ?";
    // MYSQL
    /*"SELECT a.* FROM " + "(SELECT NULL AS R_IDX, T.* FROM ({0}) T) a "
                            + "LIMIT ?,?";*/

    private final StringBuilder sql;

    private final List<String> where;

    private String selectSql;

    private final List<SqlParameterValue> sqlValues;

    private String countSql;

    private final List<SqlParameterValue> countValues;

    private String from;

    private String order;

    private int offset;

    private int limit;

    private Select() {
        this.sql = new StringBuilder("SELECT ");
        this.where = new ArrayList<String>();
        this.sqlValues = new ArrayList<SqlParameterValue>();
        this.countValues = new ArrayList<SqlParameterValue>();
    }

    public static Select builder() {
        return new Select();
    }

    public Select selection(String str) {
        sql.append(StringUtils.trim(str));
        return this;
    }

    public Select from(String from) {
        this.from = StringUtils.trim(from);
        return this;
    }

    public Select order(String order) {
        this.order = StringUtils.trim(order);
        return this;
    }

    public Select criteria(String criterion, Object... params) {
        where.add(StringUtils.trim(criterion));
        if (params != null) {
            for (Object obj : params) {
                final SqlParameterValue val = SqlValue.value(obj);
                sqlValues.add(val);
                countValues.add(val);
            }
        }
        return this;
    }

    public Select offset(int offset) {
        this.offset = offset;
        return this;
    }

    public Select limit(int limit) {
        this.limit = limit;
        return this;
    }

    public String countSql() {
        if (StringUtils.isBlank(countSql)) {
            StringBuilder count = new StringBuilder("SELECT COUNT(*) FROM ")
            .append(from);
            if (!where.isEmpty()) {
                count.append(" WHERE ")
                .append(StringUtils.join(where, " AND "));
            }
            this.countSql = count.toString();
            //System.out.println(countSql);
        }
        return countSql;
    }

    public Object[] countValues() {
        return countValues.toArray();
    }

    public Object[] sqlValues() {
        return sqlValues.toArray();
    }

    public String selectSql() {
        if (StringUtils.isBlank(selectSql)) {
            sql.append(" FROM ").append(from);
            if (!where.isEmpty()) {
                sql.append(" WHERE ").append(StringUtils.join(where, " AND "));
            }
            if (StringUtils.isNotBlank(order)) {
                sql.append(StringUtils.SPACE);
                if (!StringUtils.startsWithIgnoreCase(order, "ORDER BY")) {
                    sql.append("ORDER BY ");
                }
                sql.append(order);
            }
            this.selectSql = MessageFormat.format(SELECT_SQL, sql.toString());
            sqlValues.add(SqlValue.value(offset));
            if (limit > 0) {
                sqlValues.add(SqlValue.value(limit));
            } else {
                sqlValues.add(SqlValue.value(Page.PAGE_SIZE));
            }
            //System.out.println(selectSql);
        }
        return selectSql;
    }
}