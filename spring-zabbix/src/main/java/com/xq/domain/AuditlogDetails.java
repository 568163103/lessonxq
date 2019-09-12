package com.xq.domain;

public class AuditlogDetails {
    private Long auditdetailid;

    private Long auditid;

    private String tableName;

    private String fieldName;

    private String oldvalue;

    private String newvalue;

    public Long getAuditdetailid() {
        return auditdetailid;
    }

    public void setAuditdetailid(Long auditdetailid) {
        this.auditdetailid = auditdetailid;
    }

    public Long getAuditid() {
        return auditid;
    }

    public void setAuditid(Long auditid) {
        this.auditid = auditid;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }
}