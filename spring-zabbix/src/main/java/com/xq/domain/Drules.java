package com.xq.domain;

public class Drules {
    private Long druleid;

    private Long proxyHostid;

    private String name;

    private String iprange;

    private Integer delay;

    private Integer nextcheck;

    private Integer status;

    public Long getDruleid() {
        return druleid;
    }

    public void setDruleid(Long druleid) {
        this.druleid = druleid;
    }

    public Long getProxyHostid() {
        return proxyHostid;
    }

    public void setProxyHostid(Long proxyHostid) {
        this.proxyHostid = proxyHostid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIprange() {
        return iprange;
    }

    public void setIprange(String iprange) {
        this.iprange = iprange;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getNextcheck() {
        return nextcheck;
    }

    public void setNextcheck(Integer nextcheck) {
        this.nextcheck = nextcheck;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}