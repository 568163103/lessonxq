package com.xq.domain;

public class Dservices {
    private Long dserviceid;

    private Long dhostid;

    private Integer type;

    private String key;

    private String value;

    private Integer port;

    private Integer status;

    private Integer lastup;

    private Integer lastdown;

    private Long dcheckid;

    private String ip;

    private String dns;

    public Long getDserviceid() {
        return dserviceid;
    }

    public void setDserviceid(Long dserviceid) {
        this.dserviceid = dserviceid;
    }

    public Long getDhostid() {
        return dhostid;
    }

    public void setDhostid(Long dhostid) {
        this.dhostid = dhostid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLastup() {
        return lastup;
    }

    public void setLastup(Integer lastup) {
        this.lastup = lastup;
    }

    public Integer getLastdown() {
        return lastdown;
    }

    public void setLastdown(Integer lastdown) {
        this.lastdown = lastdown;
    }

    public Long getDcheckid() {
        return dcheckid;
    }

    public void setDcheckid(Long dcheckid) {
        this.dcheckid = dcheckid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }
}