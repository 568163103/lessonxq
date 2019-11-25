package com.mingsoft.nvssauthor.domain;

import java.util.Date;

public class ServerStauts {
    private String serverId;

    private String cpu;

    private String cpuTotal;

    private String memory;

    private String memoryFree;

    private String memoryTotal;

    private String networkIn;

    private String networkOut;

    private String onlineClient;

    private String onlineServer;

    private Date createTime;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getCpuTotal() {
        return cpuTotal;
    }

    public void setCpuTotal(String cpuTotal) {
        this.cpuTotal = cpuTotal;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getMemoryFree() {
        return memoryFree;
    }

    public void setMemoryFree(String memoryFree) {
        this.memoryFree = memoryFree;
    }

    public String getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(String memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public String getNetworkIn() {
        return networkIn;
    }

    public void setNetworkIn(String networkIn) {
        this.networkIn = networkIn;
    }

    public String getNetworkOut() {
        return networkOut;
    }

    public void setNetworkOut(String networkOut) {
        this.networkOut = networkOut;
    }

    public String getOnlineClient() {
        return onlineClient;
    }

    public void setOnlineClient(String onlineClient) {
        this.onlineClient = onlineClient;
    }

    public String getOnlineServer() {
        return onlineServer;
    }

    public void setOnlineServer(String onlineServer) {
        this.onlineServer = onlineServer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}