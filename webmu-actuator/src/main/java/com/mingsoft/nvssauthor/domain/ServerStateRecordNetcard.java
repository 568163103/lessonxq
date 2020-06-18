package com.mingsoft.nvssauthor.domain;

public class ServerStateRecordNetcard {
    private Long id;

    /**
    * 记录id
    */
    private Long recordid;

    /**
    * 端口
    */
    private String port;

    /**
    * 端口连接状态
    */
    private String portstate;

    /**
    * 端口带宽
    */
    private String bandwidth;

    /**
    * 端口流量
    */
    private String dataflow;

    /**
    * IP
    */
    private String ip;

    /**
    * MAC
    */
    private String mac;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordid() {
        return recordid;
    }

    public void setRecordid(Long recordid) {
        this.recordid = recordid;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPortstate() {
        return portstate;
    }

    public void setPortstate(String portstate) {
        this.portstate = portstate;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getDataflow() {
        return dataflow;
    }

    public void setDataflow(String dataflow) {
        this.dataflow = dataflow;
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
}