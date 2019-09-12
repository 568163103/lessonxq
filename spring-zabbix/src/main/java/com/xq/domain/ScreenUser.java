package com.xq.domain;

public class ScreenUser {
    private Long screenuserid;

    private Long screenid;

    private Long userid;

    private Integer permission;

    public Long getScreenuserid() {
        return screenuserid;
    }

    public void setScreenuserid(Long screenuserid) {
        this.screenuserid = screenuserid;
    }

    public Long getScreenid() {
        return screenid;
    }

    public void setScreenid(Long screenid) {
        this.screenid = screenid;
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