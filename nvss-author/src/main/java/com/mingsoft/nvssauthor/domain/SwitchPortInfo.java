package com.mingsoft.nvssauthor.domain;

public class SwitchPortInfo {
    /**
    * 自增主键
    */
    private Long id;

    /**
    * 交换机id
    */
    private String switchid;

    /**
    * 端口号
    */
    private Integer port;

    private String ip;

    private String mac;

    /**
    * 连接设备ID
    */
    private String deviceid;

    /**
    * dmuId
    */
    private String master;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSwitchid() {
        return switchid;
    }

    public void setSwitchid(String switchid) {
        this.switchid = switchid;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}