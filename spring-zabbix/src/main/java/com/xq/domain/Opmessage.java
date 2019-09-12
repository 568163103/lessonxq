package com.xq.domain;

public class Opmessage {
    private Long operationid;

    private Integer defaultMsg;

    private String subject;

    private String message;

    private Long mediatypeid;

    public Long getOperationid() {
        return operationid;
    }

    public void setOperationid(Long operationid) {
        this.operationid = operationid;
    }

    public Integer getDefaultMsg() {
        return defaultMsg;
    }

    public void setDefaultMsg(Integer defaultMsg) {
        this.defaultMsg = defaultMsg;
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

    public Long getMediatypeid() {
        return mediatypeid;
    }

    public void setMediatypeid(Long mediatypeid) {
        this.mediatypeid = mediatypeid;
    }
}