package com.qrra.spring.employee.service;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qrra.spring.employee.domain.Address;
import com.qrra.spring.employee.domain.Company;
import com.qrra.spring.employee.domain.Employee;
import com.qrra.spring.employee.model.InputResult;
import com.qrra.spring.employee.model.Page;
import com.qrra.spring.employee.repository.CompanyRepository;
import com.qrra.spring.employee.repository.EmployeeRepository;
import com.qrra.spring.employee.web.form.EmployeeSearchForm;

@Service
public class EmployeeService {

    private static final String ADMIN = "ADMIN";

    private final EmployeeRepository employeeRepository;

    private final CompanyRepository companyRepository;

    @Autowired
    public EmployeeService(
            final EmployeeRepository employeeRepository,
            final CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional(readOnly = true)
    public Employee find(long id) {
        return employeeRepository.find(id);
    }

    @Transactional(readOnly = true)
    public Page<Map<Integer, Object>> findByPage(EmployeeSearchForm form, int p) {
        return employeeRepository.findByPage(form, p);
    }

    @Transactional
    public InputResult<Employee> save(final Employee employee) {
        InputResult<Employee> result = new InputResult<Employee>();

        if (StringUtils.isBlank(employee.getFirstName())) {
            result.addFieldError("firstName", "First Name is required");
        }
        if (employee.getBirthDate() == null) {
            result.addFieldError("birthDate", "Birth Date is required");
        }
        if (!StringUtils.containsAny(employee.getGender(), "M", "F")) {
            result.addFieldError("gender", "Gender must be Male or Female");
        }
        if (!EmailValidator.getInstance().isValid(employee.getEmail())) {
            result.addFieldError("email", "Invalid email");
        }
        if (StringUtils.isBlank(employee.getMobile())) {
            result.addFieldError("mobile", "Mobile is required");
        }
        Address address = employee.getAddress();
        if (address == null) {
            result.addFieldError("address", "Address is required");
        } else {
            if (StringUtils.isBlank(address.getStreet())) {
                result.addFieldError("street", "Street is required");
            }
            if (StringUtils.isBlank(address.getCity())) {
                result.addFieldError("city", "City is required");
            }
            if (StringUtils.isBlank(address.getZip())) {
                result.addFieldError("zip", "Zip is required");
            }
        }
        Company company = employee.getCompany();
        if (company == null) {
            result.addFieldError("company", "Company is required");
        } else {
            if (!companyRepository.exists(company.getId())) {
                result.addFieldError("company", "Company does not exist");
            }
        }

        if (!result.hasErrors()) {
            if (employee.getId() > 0L) {
                employee.setUpdatedBy(ADMIN);
                employeeRepository.update(employee);
            } else {
                employee.setHireDate(new Date());
                employee.setCreatedBy(ADMIN);
                employee.setUpdatedBy(ADMIN);
                final long id = employeeRepository.save(employee);
                employee.setId(id);
            }
            result.setObject(employee);
        }
        return result;
    }

    @Transactional
    public void remove(final long id) {
        employeeRepository.delete(id);
    }

    @Transactional
    public void batchRemove(final long[] ids) {
        if (ids != null && ids.length > 0) {
            employeeRepository.batchDelete(ids);
        }
    }
}