package com.qrra.spring.employee.repository;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.qrra.spring.employee.domain.Address;
import com.qrra.spring.employee.domain.Company;
import com.qrra.spring.employee.repository.mapper.CompanyMapper;
import com.qrra.spring.employee.repository.support.SqlValue;

@Repository
public class CompanyRepository extends BaseRepository<Company> {

    @Override
    public String getFindSQL() {
        return CompanyMapper.FIND_SQL;
    }

    @Override
    public ResultSetExtractor<Company> getResultSetExtractor() {
        return CompanyMapper.getResultSetExtractor();
    }

    @Override
    public String getCountSQL() {
        return CompanyMapper.COUNT_SQL;
    }

    @Override
    public String getExistsSQL() {
        return CompanyMapper.EXISTS_SQL;
    }

    @Override
    public String getSaveSQL() {
        return CompanyMapper.INSERT_SQL;
    }

    @Override
    public Object[] getSaveValues(Company company) {
        final Address address = company.getAddress();
        return new Object[] {
                SqlValue.value(company.getName()),
                SqlValue.value(company.getDescription()),
                SqlValue.value( address == null ? EMPTY : address.getStreet() ),
                SqlValue.value( address == null ? EMPTY : address.getCity() ),
                SqlValue.value( address == null ? EMPTY : address.getZip() ),
                SqlValue.value( address == null ? EMPTY : address.getState() ),
                SqlValue.value(company.getCreatedAt()),
                SqlValue.value(company.getCreatedBy()),
                SqlValue.value(company.getUpdatedAt()),
                SqlValue.value(company.getUpdatedBy()),
                SqlValue.value(company.getVersion())
        };
    }

    @Override
    public String getUpdateSQL() {
        return CompanyMapper.UPDATE_SQL;
    }

    @Override
    public Object[] getUpdateValues(Company company) {
        final Address address = company.getAddress();
        return new Object[] {
                SqlValue.value(company.getName()),
                SqlValue.value(company.getDescription()),
                SqlValue.value( address == null ? EMPTY : address.getStreet() ),
                SqlValue.value( address == null ? EMPTY : address.getCity() ),
                SqlValue.value( address == null ? EMPTY : address.getZip() ),
                SqlValue.value( address == null ? EMPTY : address.getState() ),
                SqlValue.value(company.getUpdatedAt()),
                SqlValue.value(company.getUpdatedBy()),
                SqlValue.value(company.getVersion()),
                SqlValue.value(company.getId())
        };
    }

    @Override
    public String getDeleteSQL() {
        return CompanyMapper.DELETE_SQL;
    }

    @Override
    public String getKeyExistsSQL() {
        return CompanyMapper.KEY_EXISTS_SQL;
    }

    public List<Company> findAll() throws DataAccessException {
        return jdbcTemplate
                .query(
                        "SELECT * FROM COMPANY ORDER BY NAME",
                        CompanyMapper.getRowMapper());
    }
}