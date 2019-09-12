package com.xq.domain;

public class ServicesTimes {
    private Long timeid;

    private Long serviceid;

    private Integer type;

    private Integer tsFrom;

    private Integer tsTo;

    private String note;

    public Long getTimeid() {
        return timeid;
    }

    public void setTimeid(Long timeid) {
        this.timeid = timeid;
    }

    public Long getServiceid() {
        return serviceid;
    }

    public void setServiceid(Long serviceid) {
        this.serviceid = serviceid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTsFrom() {
        return tsFrom;
    }

    public void setTsFrom(Integer tsFrom) {
        this.tsFrom = tsFrom;
    }

    public Integer getTsTo() {
        return tsTo;
    }

    public void setTsTo(Integer tsTo) {
        this.tsTo = tsTo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}