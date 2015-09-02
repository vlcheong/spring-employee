package com.qrra.spring.employee.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qrra.spring.employee.util.DateUtils;

public class Employee extends BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6177839850035943123L;

    private String firstName;

    private String lastName;

    private String gender;

    private Date birthDate;

    private String mobile;

    private String homePhone;

    private String email;

    private Address address;

    private Company company;

    private Date hireDate;

    private String description;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    @Override
    public String getTable() {
        return "EMPLOYEE";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .appendSuper(super.toString())
        .append("firstName", firstName)
        .append("lastName", lastName)
        .append("gender", gender)
        .append("birthDate",  birthDate == null ? "<null>" : DateUtils.formatDate(birthDate))
        .append("mobile", mobile)
        .append("homePhone", homePhone)
        .append("email", email)
        .append("address", address == null ? "<null>" : address.toString())
        .append("company", company == null ? "<null>" : company.toString())
        .append("hireDate", hireDate == null ? "<null>" : DateUtils.formatDate(hireDate))
        .append("description", description)
        .toString();
    }
}