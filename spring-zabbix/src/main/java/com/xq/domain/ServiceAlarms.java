package com.xq.domain;

public class ServiceAlarms {
    private Long servicealarmid;

    private Long serviceid;

    private Integer clock;

    private Integer value;

    public Long getServicealarmid() {
        return servicealarmid;
    }

    public void setServicealarmid(Long servicealarmid) {
        this.servicealarmid = servicealarmid;
    }

    public Long getServiceid() {
        return serviceid;
    }

    public void setServiceid(Long serviceid) {
        this.serviceid = serviceid;
    }

    public Integer getClock() {
        return clock;
    }

    public void setClock(Integer clock) {
        this.clock = clock;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}