package com.xq.domain;

import java.util.Date;

public class TRoleMenu {
    /**
    * 角色ID
    */
    private Integer rid;

    /**
    * 模块ID
    */
    private Integer mid;

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

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
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