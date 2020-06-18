package com.mingsoft.nvssauthor.domain;

import java.util.Date;

public class TDict {
    /**
    * 主键ID
    */
    private Integer did;

    /**
    * 父ID
    */
    private Integer preId;

    /**
    * 名称
    */
    private String name;

    private String value;

    private String descr;

    /**
    * 状态：1有效，0无效
    */
    private Boolean status;

    private Byte dmlflag;

    private Date dmltime;

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getPreId() {
        return preId;
    }

    public void setPreId(Integer preId) {
        this.preId = preId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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