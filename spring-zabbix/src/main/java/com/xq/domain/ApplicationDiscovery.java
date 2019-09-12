package com.xq.domain;

public class ApplicationDiscovery {
    private Long applicationDiscoveryid;

    private Long applicationid;

    private Long applicationPrototypeid;

    private String name;

    private Integer lastcheck;

    private Integer tsDelete;

    public Long getApplicationDiscoveryid() {
        return applicationDiscoveryid;
    }

    public void setApplicationDiscoveryid(Long applicationDiscoveryid) {
        this.applicationDiscoveryid = applicationDiscoveryid;
    }

    public Long getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(Long applicationid) {
        this.applicationid = applicationid;
    }

    public Long getApplicationPrototypeid() {
        return applicationPrototypeid;
    }

    public void setApplicationPrototypeid(Long applicationPrototypeid) {
        this.applicationPrototypeid = applicationPrototypeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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