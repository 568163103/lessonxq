package com.mingsoft.nvssauthor.domain;

public class ServerStateRecordCpu {
    private Long id;

    /**
    * 记录id
    */
    private Long recordid;

    /**
    * CPU使用百分比
    */
    private String cpupercent;

    /**
    * cpu温度
    */
    private String cputemp;

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

    public String getCpupercent() {
        return cpupercent;
    }

    public void setCpupercent(String cpupercent) {
        this.cpupercent = cpupercent;
    }

    public String getCputemp() {
        return cputemp;
    }

    public void setCputemp(String cputemp) {
        this.cputemp = cputemp;
    }
}