package com.xq.domain;

public class GroupDiscovery {
    private Long groupid;

    private Long parentGroupPrototypeid;

    private String name;

    private Integer lastcheck;

    private Integer tsDelete;

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Long getParentGroupPrototypeid() {
        return parentGroupPrototypeid;
    }

    public void setParentGroupPrototypeid(Long parentGroupPrototypeid) {
        this.parentGroupPrototypeid = parentGroupPrototypeid;
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