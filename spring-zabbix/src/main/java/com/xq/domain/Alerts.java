package com.xq.domain;

public class Alerts {
    private Long alertid;

    private Long actionid;

    private Long eventid;

    private Long userid;

    private Integer clock;

    private Long mediatypeid;

    private String sendto;

    private String subject;

    private String message;

    private Integer status;

    private Integer retries;

    private String error;

    private Integer escStep;

    private Integer alerttype;

    public Long getAlertid() {
        return alertid;
    }

    public void setAlertid(Long alertid) {
        this.alertid = alertid;
    }

    public Long getActionid() {
        return actionid;
    }

    public void setActionid(Long actionid) {
        this.actionid = actionid;
    }

    public Long getEventid() {
        return eventid;
    }

    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getClock() {
        return clock;
    }

    public void setClock(Integer clock) {
        this.clock = clock;
    }

    public Long getMediatypeid() {
        return mediatypeid;
    }

    public void setMediatypeid(Long mediatypeid) {
        this.mediatypeid = mediatypeid;
    }

    public String getSendto() {
        return sendto;
    }

    public void setSendto(String sendto) {
        this.sendto = sendto;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getEscStep() {
        return escStep;
    }

    public void setEscStep(Integer escStep) {
        this.escStep = escStep;
    }

    public Integer getAlerttype() {
        return alerttype;
    }

    public void setAlerttype(Integer alerttype) {
        this.alerttype = alerttype;
    }
}