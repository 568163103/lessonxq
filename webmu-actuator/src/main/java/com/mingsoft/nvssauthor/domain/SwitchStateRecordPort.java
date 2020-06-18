package com.mingsoft.nvssauthor.domain;

public class SwitchStateRecordPort {
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
    * 端口类型
    */
    private String type;

    /**
    * 端口速率
    */
    private String rate;

    /**
    * 端口状态
    */
    private String state;

    /**
    * 端口远程IP
    */
    private String ip;

    /**
    * 端口丢包数
    */
    private String loss;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }
}