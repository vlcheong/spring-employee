package com.qrra.spring.employee.repository.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class JdbcInsertStatementCreator implements PreparedStatementCreator {

    private final String sql;

    private final ArgumentPreparedStatementSetter newArgPreparedStatementSetter;

    private static final String[] PK = new String[] {"ID"};

    public JdbcInsertStatementCreator(String sql, Object... args) {
        this.sql = sql;
        this.newArgPreparedStatementSetter =
                new ArgumentPreparedStatementSetter(args);
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql, PK);
        //pstmt.setPoolable(true);
        newArgPreparedStatementSetter.setValues(pstmt);
        return pstmt;
    }
}