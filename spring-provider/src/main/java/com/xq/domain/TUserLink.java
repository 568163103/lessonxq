package com.xq.domain;

public class TUserLink {
    /**
    * 全ID（父全ID_自ID）
    */
    private String fullAmid;

    /**
    * 管理员ID
    */
    private String amid;

    /**
    * 上级管理员ID，默认0-TOP
    */
    private String preamid;

    public String getFullAmid() {
        return fullAmid;
    }

    public void setFullAmid(String fullAmid) {
        this.fullAmid = fullAmid;
    }

    public String getAmid() {
        return amid;
    }

    public void setAmid(String amid) {
        this.amid = amid;
    }

    public String getPreamid() {
        return preamid;
    }

    public void setPreamid(String preamid) {
        this.preamid = preamid;
    }
}