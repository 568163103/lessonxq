package com.xq.domain;

import org.springframework.data.annotation.Transient;

public class Events {
    private Long eventid;

    private Integer source;

    private Integer object;

    private Long objectid;

    private Integer clock;

    private Integer value;

    private Integer acknowledged;

    private Integer ns;
    /**
     * 告警开始时间
     */
    @Transient
    private String begin_time;

    /**
     * 告警结束时间
     */
    @Transient
    private String end_time;

    @Transient
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Long getEventid() {
        return eventid;
    }

    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getObject() {
        return object;
    }

    public void setObject(Integer object) {
        this.object = object;
    }

    public Long getObjectid() {
        return objectid;
    }

    public void setObjectid(Long objectid) {
        this.objectid = objectid;
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

    public Integer getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(Integer acknowledged) {
        this.acknowledged = acknowledged;
    }

    public Integer getNs() {
        return ns;
    }

    public void setNs(Integer ns) {
        this.ns = ns;
    }
}