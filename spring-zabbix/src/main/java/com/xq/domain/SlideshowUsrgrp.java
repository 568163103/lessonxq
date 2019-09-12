package com.xq.domain;

public class SlideshowUsrgrp {
    private Long slideshowusrgrpid;

    private Long slideshowid;

    private Long usrgrpid;

    private Integer permission;

    public Long getSlideshowusrgrpid() {
        return slideshowusrgrpid;
    }

    public void setSlideshowusrgrpid(Long slideshowusrgrpid) {
        this.slideshowusrgrpid = slideshowusrgrpid;
    }

    public Long getSlideshowid() {
        return slideshowid;
    }

    public void setSlideshowid(Long slideshowid) {
        this.slideshowid = slideshowid;
    }

    public Long getUsrgrpid() {
        return usrgrpid;
    }

    public void setUsrgrpid(Long usrgrpid) {
        this.usrgrpid = usrgrpid;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }
}