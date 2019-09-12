package com.xq.domain;

public class Escalations {
    private Long escalationid;

    private Long actionid;

    private Long triggerid;

    private Long eventid;

    private Long rEventid;

    private Integer nextcheck;

    private Integer escStep;

    private Integer status;

    private Long itemid;

    public Long getEscalationid() {
        return escalationid;
    }

    public void setEscalationid(Long escalationid) {
        this.escalationid = escalationid;
    }

    public Long getActionid() {
        return actionid;
    }

    public void setActionid(Long actionid) {
        this.actionid = actionid;
    }

    public Long getTriggerid() {
        return triggerid;
    }

    public void setTriggerid(Long triggerid) {
        this.triggerid = triggerid;
    }

    public Long getEventid() {
        return eventid;
    }

    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    public Long getrEventid() {
        return rEventid;
    }

    public void setrEventid(Long rEventid) {
        this.rEventid = rEventid;
    }

    public Integer getNextcheck() {
        return nextcheck;
    }

    public void setNextcheck(Integer nextcheck) {
        this.nextcheck = nextcheck;
    }

    public Integer getEscStep() {
        return escStep;
    }

    public void setEscStep(Integer escStep) {
        this.escStep = escStep;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }
}