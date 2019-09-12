package com.xq.domain;

public class Dhosts {
    private Long dhostid;

    private Long druleid;

    private Integer status;

    private Integer lastup;

    private Integer lastdown;

    public Long getDhostid() {
        return dhostid;
    }

    public void setDhostid(Long dhostid) {
        this.dhostid = dhostid;
    }

    public Long getDruleid() {
        return druleid;
    }

    public void setDruleid(Long druleid) {
        this.druleid = druleid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLastup() {
        return lastup;
    }

    public void setLastup(Integer lastup) {
        this.lastup = lastup;
    }

    public Integer getLastdown() {
        return lastdown;
    }

    public void setLastdown(Integer lastdown) {
        this.lastdown = lastdown;
    }
}