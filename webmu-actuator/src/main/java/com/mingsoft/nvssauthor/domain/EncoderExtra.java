package com.mingsoft.nvssauthor.domain;

public class EncoderExtra {
    /**
    * encoder ID,must be union code
    */
    private String id;

    /**
    * 厂家
    */
    private String corp;

    /**
    * 类型 0 编码器 1 IP摄像机
    */
    private Integer type;

    /**
    * 设备型号
    */
    private String version;

    /**
    * mac地址
    */
    private String mac;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}