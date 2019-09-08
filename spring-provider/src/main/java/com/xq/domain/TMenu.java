package com.xq.domain;

import java.util.Date;

public class TMenu {
    /**
    * 菜单ID
    */
    private Integer mid;

    /**
    * 上级节点ID，默认0-TOP
    */
    private Integer preid;

    /**
    * 全ID（父全ID_自ID）
    */
    private String fullMid;

    /**
    * 菜单名称
    */
    private String name;

    /**
    * 菜单链接
    */
    private String url;

    /**
    * 菜单显示图标
    */
    private String iconPath;

    /**
    * 是否功能页面（0-否；1-是）
    */
    private Byte isFunc;

    /**
    * 菜单层级
    */
    private Byte lel;

    /**
    * 启用状态：0-不启用（默认）；1-启用
    */
    private Byte status;

    /**
    * 序列号
    */
    private Integer serialNo;

    /**
    * 操作标识：1：新增；2：更新；3：删除。
    */
    private Byte dmlflag;

    private Date dmltime;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getPreid() {
        return preid;
    }

    public void setPreid(Integer preid) {
        this.preid = preid;
    }

    public String getFullMid() {
        return fullMid;
    }

    public void setFullMid(String fullMid) {
        this.fullMid = fullMid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Byte getIsFunc() {
        return isFunc;
    }

    public void setIsFunc(Byte isFunc) {
        this.isFunc = isFunc;
    }

    public Byte getLel() {
        return lel;
    }

    public void setLel(Byte lel) {
        this.lel = lel;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
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