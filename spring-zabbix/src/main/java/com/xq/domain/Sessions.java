package com.xq.domain;

public class Sessions {
    private String sessionid;

    private Long userid;

    private Integer lastaccess;

    private Integer status;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getLastaccess() {
        return lastaccess;
    }

    public void setLastaccess(Integer lastaccess) {
        this.lastaccess = lastaccess;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}