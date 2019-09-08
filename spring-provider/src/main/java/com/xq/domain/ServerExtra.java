package com.xq.domain;

public class ServerExtra {
    /**
    * server ID, must be union code
    */
    private String id;

    /**
    * 厂家
    */
    private String corp;

    /**
    * 设备型号
    */
    private String version;

    /**
    * mac地址
    */
    private String mac;

    /**
    * 心跳时间
    */
    private Short keepaliveperiod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Short getKeepaliveperiod() {
        return keepaliveperiod;
    }

    public void setKeepaliveperiod(Short keepaliveperiod) {
        this.keepaliveperiod = keepaliveperiod;
    }
}