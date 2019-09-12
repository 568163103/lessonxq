package com.xq.domain;

public class HostDiscovery {
    private Long hostid;

    private Long parentHostid;

    private Long parentItemid;

    private String host;

    private Integer lastcheck;

    private Integer tsDelete;

    public Long getHostid() {
        return hostid;
    }

    public void setHostid(Long hostid) {
        this.hostid = hostid;
    }

    public Long getParentHostid() {
        return parentHostid;
    }

    public void setParentHostid(Long parentHostid) {
        this.parentHostid = parentHostid;
    }

    public Long getParentItemid() {
        return parentItemid;
    }

    public void setParentItemid(Long parentItemid) {
        this.parentItemid = parentItemid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getLastcheck() {
        return lastcheck;
    }

    public void setLastcheck(Integer lastcheck) {
        this.lastcheck = lastcheck;
    }

    public Integer getTsDelete() {
        return tsDelete;
    }

    public void setTsDelete(Integer tsDelete) {
        this.tsDelete = tsDelete;
    }
}