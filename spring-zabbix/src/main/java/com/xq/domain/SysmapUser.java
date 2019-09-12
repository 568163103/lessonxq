package com.xq.domain;

public class SysmapUser {
    private Long sysmapuserid;

    private Long sysmapid;

    private Long userid;

    private Integer permission;

    public Long getSysmapuserid() {
        return sysmapuserid;
    }

    public void setSysmapuserid(Long sysmapuserid) {
        this.sysmapuserid = sysmapuserid;
    }

    public Long getSysmapid() {
        return sysmapid;
    }

    public void setSysmapid(Long sysmapid) {
        this.sysmapid = sysmapid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }
}