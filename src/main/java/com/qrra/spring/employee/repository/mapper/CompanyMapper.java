package com.qrra.spring.employee.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.qrra.spring.employee.domain.Address;
import com.qrra.spring.employee.domain.Company;

public final class CompanyMapper implements RowMapper<Company>, ResultSetExtractor<Company> {
    public static final CompanyMapper INSTANCE = new CompanyMapper();

    public static final String FIND_SQL =
            "SELECT * FROM COMPANY WHERE ID=?";

    public static final String COUNT_SQL =
            "SELECT COUNT(*) FROM COMPANY";

    public static final String EXISTS_SQL =
            "SELECT COUNT(*) FROM COMPANY WHERE ID=?";

    public static final String INSERT_SQL =
            "INSERT INTO COMPANY(NAME, DESCRIPTION, STREET, CITY, ZIP, STATE, " +
                    "CREATED_AT, CREATED_BY, UPDATED_AT, UPDATED_BY, VERSION)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_SQL =
            "UPDATE COMPANY SET NAME=?, DESCRIPTION=?, STREET=?, CITY=?, ZIP=?, STATE=?, " +
                    "UPDATED_AT=?, UPDATED_BY=?, VERSION=? WHERE ID=?";

    public static final String DELETE_SQL =
            "DELETE FROM COMPANY WHERE ID=?";

    public static final String KEY_EXISTS_SQL =
            "SELECT COUNT(*) FROM COMPANY WHERE NAME=?";

    private CompanyMapper() {
    }

    public static RowMapper<Company> getRowMapper() {
        return INSTANCE;
    }

    public static ResultSetExtractor<Company> getResultSetExtractor() {
        return INSTANCE;
    }

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        return populate(rs);
    }

    @Override
    public Company extractData(ResultSet rs) throws SQLException, DataAccessException {
        return (rs.next() ? populate(rs) : null);
    }

    public Company populate(ResultSet rs) throws SQLException {
        final Company company = new Company();
        company.fill(rs);
        company.setName(rs.getString("NAME"));
        company.setDescription(rs.getString("DESCRIPTION"));

        final Address address = new Address();
        address.setStreet(rs.getString("STREET"));
        address.setCity(rs.getString("CITY"));
        address.setZip(rs.getString("ZIP"));
        address.setState(rs.getString("STATE"));
        company.setAddress(address);

        return company;
    }
}