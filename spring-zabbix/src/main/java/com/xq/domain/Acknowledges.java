package com.xq.domain;

public class Acknowledges {
    private Long acknowledgeid;

    private Long userid;

    private Long eventid;

    private Integer clock;

    private String message;

    public Long getAcknowledgeid() {
        return acknowledgeid;
    }

    public void setAcknowledgeid(Long acknowledgeid) {
        this.acknowledgeid = acknowledgeid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getEventid() {
        return eventid;
    }

    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    public Integer getClock() {
        return clock;
    }

    public void setClock(Integer clock) {
        this.clock = clock;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}