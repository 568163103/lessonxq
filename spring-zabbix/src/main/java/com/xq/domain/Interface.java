package com.xq.domain;

public class Interface {
    private Long interfaceid;

    private Long hostid;

    private Integer main;

    private Integer type;

    private Integer useip;

    private String ip;

    private String dns;

    private String port;

    private Integer bulk;

    public Long getInterfaceid() {
        return interfaceid;
    }

    public void setInterfaceid(Long interfaceid) {
        this.interfaceid = interfaceid;
    }

    public Long getHostid() {
        return hostid;
    }

    public void setHostid(Long hostid) {
        this.hostid = hostid;
    }

    public Integer getMain() {
        return main;
    }

    public void setMain(Integer main) {
        this.main = main;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUseip() {
        return useip;
    }

    public void setUseip(Integer useip) {
        this.useip = useip;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getBulk() {
        return bulk;
    }

    public void setBulk(Integer bulk) {
        this.bulk = bulk;
    }
}