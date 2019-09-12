package com.xq.domain;

public class ProxyDhistory {
    private Long id;

    private Integer clock;

    private Long druleid;

    private Integer type;

    private String ip;

    private Integer port;

    private String key;

    private String value;

    private Integer status;

    private Long dcheckid;

    private String dns;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClock() {
        return clock;
    }

    public void setClock(Integer clock) {
        this.clock = clock;
    }

    public Long getDruleid() {
        return druleid;
    }

    public void setDruleid(Long druleid) {
        this.druleid = druleid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDcheckid() {
        return dcheckid;
    }

    public void setDcheckid(Long dcheckid) {
        this.dcheckid = dcheckid;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }
}