package com.qrra.spring.employee.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.qrra.spring.employee.domain.Address;
import com.qrra.spring.employee.domain.Company;
import com.qrra.spring.employee.domain.Employee;

public final class EmployeeMapper implements RowMapper<Employee>, ResultSetExtractor<Employee> {
    public static final EmployeeMapper INSTANCE = new EmployeeMapper();

    public static final String FIND_SQL =
            "SELECT " +
                    "E.ID AS EMPLOYEE_ID, " +
                    "E.FIRST_NAME AS EMPLOYEE_FIRST_NAME, " +
                    "E.LAST_NAME AS EMPLOYEE_LAST_NAME, " +
                    "E.GENDER AS EMPLOYEE_GENDER, " +
                    "E.BIRTH_DATE AS EMPLOYEE_BIRTH_DATE, " +
                    "E.MOBILE AS EMPLOYEE_MOBILE, " +
                    "E.EMAIL AS EMPLOYEE_EMAIL, " +
                    "E.HOME_PHONE AS EMPLOYEE_HOME_PHONE, " +
                    "E.STREET AS EMPLOYEE_STREET, " +
                    "E.CITY AS EMPLOYEE_CITY, " +
                    "E.ZIP AS EMPLOYEE_ZIP, " +
                    "E.STATE AS EMPLOYEE_STATE, " +
                    "E.HIRE_DATE AS EMPLOYEE_HIRE_DATE, " +
                    "E.DESCRIPTION AS EMPLOYEE_DESCRIPTION, " +
                    "E.CREATED_AT AS EMPLOYEE_CREATED_AT, " +
                    "E.CREATED_BY AS EMPLOYEE_CREATED_BY, " +
                    "E.UPDATED_AT AS EMPLOYEE_UPDATED_AT, " +
                    "E.UPDATED_BY AS EMPLOYEE_UPDATED_BY, " +
                    "E.VERSION AS EMPLOYEE_VERSION, " +
                    "C.ID AS COMPANY_ID, " +
                    "C.NAME AS COMPANY_NAME, " +
                    "C.DESCRIPTION AS COMPANY_DESCRIPTION, " +
                    "C.STREET AS COMPANY_STREET, " +
                    "C.CITY AS COMPANY_CITY, " +
                    "C.ZIP AS COMPANY_ZIP, " +
                    "C.STATE AS COMPANY_STATE, " +
                    "C.CREATED_AT AS COMPANY_CREATED_AT, " +
                    "C.CREATED_BY AS COMPANY_CREATED_BY, " +
                    "C.UPDATED_AT AS COMPANY_UPDATED_AT, " +
                    "C.UPDATED_BY AS COMPANY_UPDATED_BY, " +
                    "C.VERSION AS COMPANY_VERSION " +
                    "FROM EMPLOYEE E " +
                    "LEFT JOIN COMPANY_EMPLOYEE CE " +
                    "ON E.ID=CE.EMPLOYEE_ID " +
                    "LEFT JOIN COMPANY C " +
                    "ON C.ID=CE.COMPANY_ID " +
                    "WHERE E.ID=?";

    public static final String COUNT_SQL =
            "SELECT COUNT(*) FROM EMPLOYEE";

    public static final String EXISTS_SQL =
            "SELECT COUNT(*) FROM EMPLOYEE WHERE ID=?";

    public static final String INSERT_SQL =
            "INSERT INTO EMPLOYEE(" +
                    "FIRST_NAME, LAST_NAME, GENDER, BIRTH_DATE, MOBILE, EMAIL, HOME_PHONE, " +
                    "STREET, CITY, ZIP, STATE, HIRE_DATE, DESCRIPTION, " +
                    "CREATED_AT, CREATED_BY, UPDATED_AT, UPDATED_BY, VERSION)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_SQL =
            "UPDATE EMPLOYEE SET " +
                    "FIRST_NAME=?, LAST_NAME=?, GENDER=?, BIRTH_DATE=?, MOBILE=?, EMAIL=?, HOME_PHONE=?, " +
                    "STREET=?, CITY=?, ZIP=?, STATE=?, HIRE_DATE=?, DESCRIPTION=?, " +
                    "UPDATED_AT=?, UPDATED_BY=?, VERSION=? WHERE ID=?";

    public static final String DELETE_SQL =
            "DELETE FROM EMPLOYEE WHERE ID=?";

    public static final String KEY_EXISTS_SQL =
            "SELECT COUNT(*) FROM EMPLOYEE WHERE ID=?";

    private EmployeeMapper() {
    }

    public static RowMapper<Employee> getRowMapper() {
        return INSTANCE;
    }

    public static ResultSetExtractor<Employee> getResultSetExtractor() {
        return INSTANCE;
    }

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return populate(rs);
    }

    @Override
    public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
        return (rs.next() ? populate(rs) : null);
    }

    public Employee populate(ResultSet rs) throws SQLException {
        final Employee employee = new Employee();
        employee.setId(rs.getLong("EMPLOYEE_ID"));
        employee.setFirstName(rs.getString("EMPLOYEE_FIRST_NAME"));
        employee.setLastName(rs.getString("EMPLOYEE_LAST_NAME"));
        employee.setGender(rs.getString("EMPLOYEE_GENDER"));
        employee.setBirthDate(rs.getDate("EMPLOYEE_BIRTH_DATE"));
        employee.setMobile(rs.getString("EMPLOYEE_MOBILE"));
        employee.setEmail(rs.getString("EMPLOYEE_EMAIL"));
        employee.setHomePhone(rs.getString("EMPLOYEE_HOME_PHONE"));
        employee.setHireDate(rs.getDate("EMPLOYEE_HIRE_DATE"));
        employee.setDescription(rs.getString("EMPLOYEE_DESCRIPTION"));
        employee.setCreatedAt(rs.getTimestamp("EMPLOYEE_CREATED_AT"));
        employee.setCreatedBy(rs.getString("EMPLOYEE_CREATED_BY"));
        employee.setUpdatedAt(rs.getTimestamp("EMPLOYEE_UPDATED_AT"));
        employee.setUpdatedBy(rs.getString("EMPLOYEE_UPDATED_BY"));
        employee.setVersion(rs.getLong("EMPLOYEE_VERSION"));

        final Company company = new Company();
        company.setId(rs.getLong("COMPANY_ID"));
        company.setName(rs.getString("COMPANY_NAME"));
        company.setDescription(rs.getString("COMPANY_DESCRIPTION"));

        final Address companyAddress = new Address();
        companyAddress.setStreet(rs.getString("COMPANY_STREET"));
        companyAddress.setCity(rs.getString("COMPANY_CITY"));
        companyAddress.setZip(rs.getString("COMPANY_ZIP"));
        companyAddress.setState(rs.getString("COMPANY_STATE"));
        company.setAddress(companyAddress) ;
        company.setCreatedAt(rs.getTimestamp("COMPANY_CREATED_AT"));
        company.setCreatedBy(rs.getString("COMPANY_CREATED_BY"));
        company.setUpdatedAt(rs.getTimestamp("COMPANY_UPDATED_AT"));
        company.setUpdatedBy(rs.getString("COMPANY_UPDATED_BY"));
        company.setVersion(rs.getLong("COMPANY_VERSION"));
        employee.setCompany(company);

        final Address employeeAddress = new Address();
        employeeAddress.setStreet(rs.getString("EMPLOYEE_STREET"));
        employeeAddress.setCity(rs.getString("EMPLOYEE_CITY"));
        employeeAddress.setZip(rs.getString("EMPLOYEE_ZIP"));
        employeeAddress.setState(rs.getString("EMPLOYEE_STATE"));
        employee.setAddress(employeeAddress);

        return employee;
    }
}