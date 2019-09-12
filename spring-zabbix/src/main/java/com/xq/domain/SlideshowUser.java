package com.xq.domain;

public class SlideshowUser {
    private Long slideshowuserid;

    private Long slideshowid;

    private Long userid;

    private Integer permission;

    public Long getSlideshowuserid() {
        return slideshowuserid;
    }

    public void setSlideshowuserid(Long slideshowuserid) {
        this.slideshowuserid = slideshowuserid;
    }

    public Long getSlideshowid() {
        return slideshowid;
    }

    public void setSlideshowid(Long slideshowid) {
        this.slideshowid = slideshowid;
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