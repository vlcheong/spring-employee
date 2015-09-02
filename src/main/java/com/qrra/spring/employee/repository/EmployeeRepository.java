package com.qrra.spring.employee.repository;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.qrra.spring.employee.domain.Address;
import com.qrra.spring.employee.domain.Employee;
import com.qrra.spring.employee.model.Page;
import com.qrra.spring.employee.repository.mapper.EmployeeMapper;
import com.qrra.spring.employee.repository.support.Select;
import com.qrra.spring.employee.repository.support.SqlValue;
import com.qrra.spring.employee.web.form.EmployeeSearchForm;

@Repository
public class EmployeeRepository extends BaseRepository<Employee> {

    @Override
    public String getFindSQL() {
        return EmployeeMapper.FIND_SQL;
    }

    @Override
    public ResultSetExtractor<Employee> getResultSetExtractor() {
        return EmployeeMapper.getResultSetExtractor();
    }

    @Override
    public String getCountSQL() {
        return EmployeeMapper.COUNT_SQL;
    }

    @Override
    public String getExistsSQL() {
        return EmployeeMapper.EXISTS_SQL;
    }

    @Override
    public String getSaveSQL() {
        return EmployeeMapper.INSERT_SQL;
    }

    @Override
    public Object[] getSaveValues(Employee employee) {
        final Address address = employee.getAddress();
        return new Object[] {
                SqlValue.value(employee.getFirstName()),
                SqlValue.value(employee.getLastName()),
                SqlValue.value(employee.getGender()),
                SqlValue.value(employee.getBirthDate()),
                SqlValue.value(employee.getMobile()),
                SqlValue.value(employee.getEmail()),
                SqlValue.value(employee.getHomePhone()),
                SqlValue.value( address == null ? EMPTY : address.getStreet() ),
                SqlValue.value( address == null ? EMPTY : address.getCity() ),
                SqlValue.value( address == null ? EMPTY : address.getZip() ),
                SqlValue.value( address == null ? EMPTY : address.getState() ),
                SqlValue.value(employee.getHireDate()),
                SqlValue.value(employee.getDescription()),
                SqlValue.value(employee.getCreatedAt()),
                SqlValue.value(employee.getCreatedBy()),
                SqlValue.value(employee.getUpdatedAt()),
                SqlValue.value(employee.getUpdatedBy()),
                SqlValue.value(employee.getVersion())
        };
    }

    @Override
    public String getUpdateSQL() {
        return EmployeeMapper.UPDATE_SQL;
    }

    @Override
    public Object[] getUpdateValues(Employee employee) {
        final Address address = employee.getAddress();
        return new Object[] {
                SqlValue.value(employee.getFirstName()),
                SqlValue.value(employee.getLastName()),
                SqlValue.value(employee.getGender()),
                SqlValue.value(employee.getBirthDate()),
                SqlValue.value(employee.getMobile()),
                SqlValue.value(employee.getEmail()),
                SqlValue.value(employee.getHomePhone()),
                SqlValue.value( address == null ? EMPTY : address.getStreet() ),
                SqlValue.value( address == null ? EMPTY : address.getCity() ),
                SqlValue.value( address == null ? EMPTY : address.getZip() ),
                SqlValue.value( address == null ? EMPTY : address.getState() ),
                SqlValue.value(employee.getHireDate()),
                SqlValue.value(employee.getDescription()),
                SqlValue.value(employee.getUpdatedAt()),
                SqlValue.value(employee.getUpdatedBy()),
                SqlValue.value(employee.getVersion()),
                SqlValue.value(employee.getId())
        };
    }

    @Override
    public String getDeleteSQL() {
        return EmployeeMapper.DELETE_SQL;
    }

    @Override
    public String getKeyExistsSQL() {
        return EmployeeMapper.KEY_EXISTS_SQL;
    }

    @Override
    public long save(Employee employee) throws DataAccessException {
        final long id = super.save(employee);
        jdbcTemplate.update(
                "INSERT INTO COMPANY_EMPLOYEE(COMPANY_ID, EMPLOYEE_ID)VALUES(?, ?)",
                SqlValue.value(employee.getCompany().getId()),
                SqlValue.value(id));
        return id;
    }

    @Override
    public int update(Employee employee) throws DataAccessException {
        final int count = super.update(employee);
        if (count > 0) {
            jdbcTemplate
            .update(
                    "UPDATE COMPANY_EMPLOYEE SET COMPANY_ID=? WHERE EMPLOYEE_ID=?",
                    SqlValue.value(employee.getCompany().getId()),
                    SqlValue.value(employee.getId()) );
        }
        return count;
    }

    @Override
    public int delete(long id) throws DataAccessException {
        jdbcTemplate.update(
                "DELETE FROM COMPANY_EMPLOYEE WHERE EMPLOYEE_ID=?",
                SqlValue.value(id));
        return super.delete(id);
    }

    public void batchDelete(long[] ids) throws DataAccessException {
        List<Object[]> batchArgs = new ArrayList<Object[]>(ids.length);
        for (int i = 0, len = ids.length; i < len; i++) {
            batchArgs.add(new Object[] {
                    SqlValue.value(ids[i])
            });
        }
        jdbcTemplate
        .batchUpdate(
                "DELETE FROM COMPANY_EMPLOYEE WHERE EMPLOYEE_ID=?",
                batchArgs);
        jdbcTemplate
        .batchUpdate(
                EmployeeMapper.DELETE_SQL,
                batchArgs);
    }

    public Page<Map<Integer, Object>> findByPage(EmployeeSearchForm form, int p) throws DataAccessException {
        Select select =
                Select.builder()
                .selection("E.ID,E.FIRST_NAME,E.LAST_NAME,E.GENDER,E.BIRTH_DATE,E.MOBILE,E.EMAIL")
                .from("EMPLOYEE E");

        if ( StringUtils.isNotBlank(form.getFirstName()) ) {
            if ( StringUtils.equals(form.getInclFirstName(), "Y") ) {
                select.criteria("E.FIRST_NAME LIKE ?", "%" + form.getFirstName() + "%");
            } else {
                select.criteria("E.FIRST_NAME LIKE ?", form.getFirstName() + "%");
            }
        }
        if ( StringUtils.isNotBlank(form.getLastName()) ) {
            if ( StringUtils.equals(form.getInclLastName(), "Y") ) {
                select.criteria("E.LAST_NAME LIKE ?", "%" + form.getLastName() + "%");
            } else {
                select.criteria("E.LAST_NAME LIKE ?", form.getLastName() + "%");
            }
        }
        if ( StringUtils.isNotBlank(form.getEmail()) ) {
            select.criteria("E.EMAIL=?", form.getEmail());
        }
        if ( StringUtils.isNotBlank(form.getGender()) ) {
            select.criteria("E.GENDER=?", form.getGender());
        }
        if (form.getFromHireDate() != null) {
            select.criteria("E.HIRE_DATE >= ?", form.getFromHireDate());
        }
        if (form.getToHireDate() != null) {
            select.criteria("E.HIRE_DATE <= ?", form.getToHireDate());
        }
        if ( form.getCompany() > 0L ) {
            select.from("EMPLOYEE E INNER JOIN COMPANY_EMPLOYEE CE ON E.ID=CE.EMPLOYEE_ID")
            .criteria("CE.COMPANY_ID=?", form.getCompany());
        }
        select.order("E.FIRST_NAME, E.LAST_NAME, E.BIRTH_DATE")
        .offset(Page.PAGE_SIZE * (p - 1))
        .limit(Page.PAGE_SIZE);

        return formPage(select, p);
    }
}