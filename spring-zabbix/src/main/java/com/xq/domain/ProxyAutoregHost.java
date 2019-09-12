package com.xq.domain;

public class ProxyAutoregHost {
    private Long id;

    private Integer clock;

    private String host;

    private String listenIp;

    private Integer listenPort;

    private String listenDns;

    private String hostMetadata;

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getListenIp() {
        return listenIp;
    }

    public void setListenIp(String listenIp) {
        this.listenIp = listenIp;
    }

    public Integer getListenPort() {
        return listenPort;
    }

    public void setListenPort(Integer listenPort) {
        this.listenPort = listenPort;
    }

    public String getListenDns() {
        return listenDns;
    }

    public void setListenDns(String listenDns) {
        this.listenDns = listenDns;
    }

    public String getHostMetadata() {
        return hostMetadata;
    }

    public void setHostMetadata(String hostMetadata) {
        this.hostMetadata = hostMetadata;
    }
}