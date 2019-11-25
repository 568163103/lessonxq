package com.mingsoft.nvssauthor.domain;

public class ServerStateRecordDisk {
    private Long id;

    private Long recordid;

    /**
    * 硬盘分区盘符
    */
    private String driver;

    /**
    * 硬盘分区总容量
    */
    private String total;

    /**
    * 已用容量
    */
    private String used;

    /**
    * 剩余容量
    */
    private String leftdisk;

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

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getLeftdisk() {
        return leftdisk;
    }

    public void setLeftdisk(String leftdisk) {
        this.leftdisk = leftdisk;
    }
}