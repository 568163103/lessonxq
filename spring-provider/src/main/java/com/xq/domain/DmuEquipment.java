package com.xq.domain;

public class DmuEquipment {
    /**
    * server ID, must be union code
    */
    private String id;

    /**
    * name, must be unique
    */
    private String name;

    /**
    * 厂家
    */
    private String corp;

    /**
    * @see ServerType
    */
    private String type;

    /**
    * 设备型号
    */
    private String version;

    /**
    * physical address
    */
    private String pos;

    /**
    * ip
    */
    private String ip;

    /**
    * mac地址
    */
    private String mac;

    /**
    * port to CMU
    */
    private Short port;

    private String master;

    /**
    * description
    */
    private String remark;

    private Boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Short getPort() {
        return port;
    }

    public void setPort(Short port) {
        this.port = port;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}