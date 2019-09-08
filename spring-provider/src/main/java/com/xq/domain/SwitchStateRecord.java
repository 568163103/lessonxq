package com.xq.domain;

public class SwitchStateRecord {
    /**
    * 记录id
    */
    private Long id;

    /**
    * switchid
    */
    private String switchid;

    /**
    * 内存使用百分比
    */
    private String memory;

    /**
    * cpu占用率
    */
    private String cpu;

    /**
    * 端口个数
    */
    private String portnum;

    private String recordtime;

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

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getPortnum() {
        return portnum;
    }

    public void setPortnum(String portnum) {
        this.portnum = portnum;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }
}