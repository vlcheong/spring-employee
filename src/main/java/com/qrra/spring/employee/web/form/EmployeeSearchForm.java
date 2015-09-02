package com.qrra.spring.employee.web.form;

import java.util.Date;

public class EmployeeSearchForm {

    private String firstName;

    private String inclFirstName;

    private String lastName;

    private String inclLastName;

    private String email;

    private String gender;

    private long company;

    private Date fromHireDate;

    private Date toHireDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setInclFirstName(String inclFirstName) {
        this.inclFirstName = inclFirstName;
    }

    public String getInclFirstName() {
        return inclFirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setInclLastName(String inclLastName) {
        this.inclLastName = inclLastName;
    }

    public String getInclLastName() {
        return inclLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getCompany() {
        return company;
    }

    public void setCompany(long company) {
        this.company = company;
    }

    public Date getFromHireDate() {
        return fromHireDate;
    }

    public void setFromHireDate(Date fromHireDate) {
        this.fromHireDate = fromHireDate;
    }

    public Date getToHireDate() {
        return toHireDate;
    }

    public void setToHireDate(Date toHireDate) {
        this.toHireDate = toHireDate;
    }
}