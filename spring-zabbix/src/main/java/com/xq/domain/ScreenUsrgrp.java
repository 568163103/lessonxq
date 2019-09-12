package com.xq.domain;

public class ScreenUsrgrp {
    private Long screenusrgrpid;

    private Long screenid;

    private Long usrgrpid;

    private Integer permission;

    public Long getScreenusrgrpid() {
        return screenusrgrpid;
    }

    public void setScreenusrgrpid(Long screenusrgrpid) {
        this.screenusrgrpid = screenusrgrpid;
    }

    public Long getScreenid() {
        return screenid;
    }

    public void setScreenid(Long screenid) {
        this.screenid = screenid;
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