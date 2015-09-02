package com.qrra.spring.employee.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Company extends BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7029129125634862983L;

    private String name;

    private String description;

    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonIgnore
    @Override
    public String getTable() {
        return "COMPANY";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .appendSuper(super.toString())
        .append("name", name)
        .append("description", description)
        .append("address", address == null ? "<null>" : address.toString())
        .toString();
    }
}