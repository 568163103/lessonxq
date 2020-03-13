package com.mingsoft.nvssauthor.domain;

public class DiskStateRecord {
    /**
    * 记录id
    */
    private Long id;

    /**
    * switchid
    */
    private String diskid;

    /**
    * 总体容量
    */
    private String totalvolume;

    /**
    * 未分配容量
    */
    private String undistributed;

    /**
    * 存储端口数量
    */
    private String portnum;

    /**
    * cpu温度
    */
    private String cpu;

    /**
    * 风扇工作状态
    */
    private String fan;

    /**
    * 磁盘坏块数量
    */
    private String bad;

    /**
    * 磁盘阵列状态
    */
    private String state;

    private String recordtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiskid() {
        return diskid;
    }

    public void setDiskid(String diskid) {
        this.diskid = diskid;
    }

    public String getTotalvolume() {
        return totalvolume;
    }

    public void setTotalvolume(String totalvolume) {
        this.totalvolume = totalvolume;
    }

    public String getUndistributed() {
        return undistributed;
    }

    public void setUndistributed(String undistributed) {
        this.undistributed = undistributed;
    }

    public String getPortnum() {
        return portnum;
    }

    public void setPortnum(String portnum) {
        this.portnum = portnum;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getFan() {
        return fan;
    }

    public void setFan(String fan) {
        this.fan = fan;
    }

    public String getBad() {
        return bad;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }
}