package com.qrra.spring.employee.repository;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.qrra.spring.employee.domain.BaseEntity;
import com.qrra.spring.employee.model.Page;
import com.qrra.spring.employee.repository.support.JdbcInsertStatementCreator;
import com.qrra.spring.employee.repository.support.Select;
import com.qrra.spring.employee.repository.support.SqlValue;
import com.qrra.spring.employee.util.DateUtils;

public abstract class BaseRepository<T extends BaseEntity> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public T find(long id) throws DataAccessException {
        return jdbcTemplate
                .query(
                        getFindSQL(),
                        getResultSetExtractor(),
                        SqlValue.value(id));
    }

    public long count() throws DataAccessException {
        return jdbcTemplate
                .queryForObject(
                        getCountSQL(),
                        Long.class)
                        .longValue();
    }

    public boolean exists(long id) throws DataAccessException {
        return (jdbcTemplate
                .queryForObject(
                        getExistsSQL(),
                        Integer.class,
                        SqlValue.value(id))
                        .intValue() > 0) ;
    }

    public long save(T entity) throws DataAccessException {
        entity.update();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate
        .update(
                new JdbcInsertStatementCreator(
                        getSaveSQL(),
                        getSaveValues(entity)),
                        keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int update(T entity) throws DataAccessException {
        entity.update();
        return jdbcTemplate
                .update(
                        getUpdateSQL(),
                        getUpdateValues(entity));
    }

    public int delete(long id) throws DataAccessException {
        return jdbcTemplate
                .update(
                        getDeleteSQL(),
                        SqlValue.value(id));
    }

    public boolean isKeyExists(SqlParameterValue... values) throws DataAccessException {
        return (jdbcTemplate
                .queryForObject(
                        getKeyExistsSQL(),
                        Integer.class,
                        (Object[]) values)
                        .intValue() > 0);
    }

    protected Page<Map<Integer, Object>> formPage(Select select, int p) throws DataAccessException {
        final long total =
                jdbcTemplate
                .queryForObject(
                        select.countSql(),
                        Long.class,
                        select.countValues())
                        .longValue();
        final List<Map<Integer, Object>> content =
                jdbcTemplate
                .query(
                        select.selectSql(),
                        PagingMapper.getResultSetExtractor(),
                        select.sqlValues());
        return new Page<Map<Integer, Object>>(p, content, total);
    }

    public static class PagingMapper implements ResultSetExtractor<List<Map<Integer, Object>>> {
        private static final PagingMapper INSTANCE = new PagingMapper();

        private PagingMapper() {
        }

        public static ResultSetExtractor<List<Map<Integer, Object>>> getResultSetExtractor() {
            return INSTANCE;
        }

        @Override
        public List<Map<Integer, Object>> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            final int len = rs.getMetaData().getColumnCount() - 1; // exclude the R_IDX
            final List<Map<Integer, Object>> result = new ArrayList<Map<Integer, Object>>(Page.PAGE_SIZE);

            while (rs.next()) {
                final Map<Integer, Object> data = new TreeMap<Integer, Object>();
                for (int i = 0; i < len; i++) {
                    Object value = rs.getObject(i + 2);
                    if (value == null) {
                        data.put(Integer.valueOf(i), EMPTY);
                    } else if (value instanceof Date) {
                        data.put(Integer.valueOf(i), DateUtils.formatDate((Date) value));
                    } else {
                        data.put(Integer.valueOf(i), value);
                    }
                }
                result.add(data);
            } // while

            return result;
        }
    }

    protected abstract String getFindSQL();

    protected abstract ResultSetExtractor<T> getResultSetExtractor();

    protected abstract String getCountSQL();

    protected abstract String getExistsSQL();

    protected abstract String getSaveSQL();

    protected abstract Object[] getSaveValues(T entity);

    protected abstract String getUpdateSQL();

    protected abstract Object[] getUpdateValues(T entity);

    protected abstract String getDeleteSQL();

    protected abstract String getKeyExistsSQL();
}