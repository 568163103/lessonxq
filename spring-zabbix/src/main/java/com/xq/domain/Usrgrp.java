package com.xq.domain;

public class Usrgrp {
    private Long usrgrpid;

    private String name;

    private Integer guiAccess;

    private Integer usersStatus;

    private Integer debugMode;

    public Long getUsrgrpid() {
        return usrgrpid;
    }

    public void setUsrgrpid(Long usrgrpid) {
        this.usrgrpid = usrgrpid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGuiAccess() {
        return guiAccess;
    }

    public void setGuiAccess(Integer guiAccess) {
        this.guiAccess = guiAccess;
    }

    public Integer getUsersStatus() {
        return usersStatus;
    }

    public void setUsersStatus(Integer usersStatus) {
        this.usersStatus = usersStatus;
    }

    public Integer getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(Integer debugMode) {
        this.debugMode = debugMode;
    }
}