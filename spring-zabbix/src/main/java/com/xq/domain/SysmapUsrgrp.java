package com.xq.domain;

public class SysmapUsrgrp {
    private Long sysmapusrgrpid;

    private Long sysmapid;

    private Long usrgrpid;

    private Integer permission;

    public Long getSysmapusrgrpid() {
        return sysmapusrgrpid;
    }

    public void setSysmapusrgrpid(Long sysmapusrgrpid) {
        this.sysmapusrgrpid = sysmapusrgrpid;
    }

    public Long getSysmapid() {
        return sysmapid;
    }

    public void setSysmapid(Long sysmapid) {
        this.sysmapid = sysmapid;
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