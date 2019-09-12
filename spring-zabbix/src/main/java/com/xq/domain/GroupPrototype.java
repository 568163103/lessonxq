package com.xq.domain;

public class GroupPrototype {
    private Long groupPrototypeid;

    private Long hostid;

    private String name;

    private Long groupid;

    private Long templateid;

    public Long getGroupPrototypeid() {
        return groupPrototypeid;
    }

    public void setGroupPrototypeid(Long groupPrototypeid) {
        this.groupPrototypeid = groupPrototypeid;
    }

    public Long getHostid() {
        return hostid;
    }

    public void setHostid(Long hostid) {
        this.hostid = hostid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Long getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Long templateid) {
        this.templateid = templateid;
    }
}