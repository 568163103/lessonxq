package com.xq.domain;

public class AutoregHost {
    private Long autoregHostid;

    private Long proxyHostid;

    private String host;

    private String listenIp;

    private Integer listenPort;

    private String listenDns;

    private String hostMetadata;

    public Long getAutoregHostid() {
        return autoregHostid;
    }

    public void setAutoregHostid(Long autoregHostid) {
        this.autoregHostid = autoregHostid;
    }

    public Long getProxyHostid() {
        return proxyHostid;
    }

    public void setProxyHostid(Long proxyHostid) {
        this.proxyHostid = proxyHostid;
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