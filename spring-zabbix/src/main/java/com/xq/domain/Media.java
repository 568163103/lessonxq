package com.xq.domain;

public class Media {
    private Long mediaid;

    private Long userid;

    private Long mediatypeid;

    private String sendto;

    private Integer active;

    private Integer severity;

    private String period;

    public Long getMediaid() {
        return mediaid;
    }

    public void setMediaid(Long mediaid) {
        this.mediaid = mediaid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}