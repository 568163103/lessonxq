package com.mingsoft.nvssauthor.domain;

import java.util.Date;

public class TUserRole {
    /**
    * 角色ID
    */
    private Integer rid;

    /**
    * 管理员ID
    */
    private String amid;

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

    public String getAmid() {
        return amid;
    }

    public void setAmid(String amid) {
        this.amid = amid;
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