package com.mingsoft.nvssauthor.domain;

import java.util.Date;

public class TRole {
    /**
    * 角色ID
    */
    private Integer rid;

    private String appCode;

    /**
    * 角色名称
    */
    private String rname;

    /**
    * 启用状态：0-不启用（默认）；1-启用
    */
    private Byte status;

    /**
    * 角色类型：1-普通角色(默认);2-超级管理员;3-酒店自管理员.
    */
    private Byte roleType;

    /**
    * 是否通用角色，0非通用，1通用
    */
    private Byte isPublic;

    /**
    * 操作标识：1：新增；2：更新；3：删除。
    */
    private Byte dmlflag;

    private Date dmltime;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getRoleType() {
        return roleType;
    }

    public void setRoleType(Byte roleType) {
        this.roleType = roleType;
    }

    public Byte getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Byte isPublic) {
        this.isPublic = isPublic;
    }

    public Byte getDmlflag() {
        return dmlflag;
    }

    public void setDmlflag(Byte dmlflag) {
        this.dmlflag = dmlflag;
    }

    public Date getDmltime() {
        return dmltime;
    }

    public void setDmltime(Date dmltime) {
        this.dmltime = dmltime;
    }
}