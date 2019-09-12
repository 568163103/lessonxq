package com.xq.domain;

public class Housekeeper {
    private Long housekeeperid;

    private String tablename;

    private String field;

    private Long value;

    public Long getHousekeeperid() {
        return housekeeperid;
    }

    public void setHousekeeperid(Long housekeeperid) {
        this.housekeeperid = housekeeperid;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}