package com.mingsoft.nvssauthor.domain;

public class ServerStateRecord {
    private Long id;

    /**
    * server ID,must be union code
    */
    private String serverid;

    /**
    * systemInfo
    */
    private String systeminfo;

    /**
    * diskNum
    */
    private String disknum;

    /**
    * totalMem
    */
    private String totalmem;

    /**
    * usedMem
    */
    private String usedmem;

    /**
    * leftMem
    */
    private String leftmem;

    /**
    * cpuNum
    */
    private String cpunum;

    /**
    * networkCard
    */
    private String networkcard;

    /**
    * recordTime
    */
    private String recordtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    public String getSysteminfo() {
        return systeminfo;
    }

    public void setSysteminfo(String systeminfo) {
        this.systeminfo = systeminfo;
    }

    public String getDisknum() {
        return disknum;
    }

    public void setDisknum(String disknum) {
        this.disknum = disknum;
    }

    public String getTotalmem() {
        return totalmem;
    }

    public void setTotalmem(String totalmem) {
        this.totalmem = totalmem;
    }

    public String getUsedmem() {
        return usedmem;
    }

    public void setUsedmem(String usedmem) {
        this.usedmem = usedmem;
    }

    public String getLeftmem() {
        return leftmem;
    }

    public void setLeftmem(String leftmem) {
        this.leftmem = leftmem;
    }

    public String getCpunum() {
        return cpunum;
    }

    public void setCpunum(String cpunum) {
        this.cpunum = cpunum;
    }

    public String getNetworkcard() {
        return networkcard;
    }

    public void setNetworkcard(String networkcard) {
        this.networkcard = networkcard;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }
}