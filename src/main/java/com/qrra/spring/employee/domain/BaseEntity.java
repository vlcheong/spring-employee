package com.qrra.spring.employee.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qrra.spring.employee.util.DateUtils;

public abstract class BaseEntity implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5858624481871104835L;

    protected long id;

    protected Timestamp createdAt;

    protected String createdBy;

    protected Timestamp updatedAt;

    protected String updatedBy;

    protected long version;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @JsonIgnore
    public String getIdString() {
        return Long.toString(id);
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedAtString() {
        return DateUtils.formatDate(createdAt);
    }
    public String toCreatedAtString() {
        return (createdAt == null ? StringUtils.EMPTY : createdAt.toString());
    }

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String toCreatedByString() {
        return StringUtils.defaultString(createdBy);
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getUpdatedAtString() {
        return DateUtils.formatDate(updatedAt);
    }
    public String toUpdatedAtString() {
        return (updatedAt == null ? StringUtils.EMPTY : updatedAt.toString());
    }

    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public String toUpdatedByString() {
        return StringUtils.defaultString(updatedBy);
    }

    public long getVersion() {
        return version;
    }
    public void setVersion(long version) {
        this.version = version;
    }
    @JsonIgnore
    public String getVersionString() {
        return Long.toString(version);
    }

    public void update() {
        this.version = System.currentTimeMillis();
        this.updatedAt = new Timestamp(this.version);
        this.createdAt = this.updatedAt;
    }

    public void fill(ResultSet rs) throws SQLException {
        this.id = rs.getLong("ID");
        this.createdAt = rs.getTimestamp("CREATED_AT");
        this.createdBy = rs.getString("CREATED_BY");
        this.updatedAt = rs.getTimestamp("UPDATED_AT");
        this.updatedBy = rs.getString("UPDATED_BY");
        this.version = rs.getLong("VERSION");
    }

    public abstract String getTable();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("createdAt", createdAt == null ? "<null>" : String.format(DateUtils.STRING_DATE_FORMAT, createdAt))
        .append("createdBy", createdBy)
        .append("updatedAt", updatedAt == null ? "<null>" : String.format(DateUtils.STRING_DATE_FORMAT, updatedAt))
        .append("updatedBy", updatedBy)
        .append("version", version)
        .toString();
    }
}