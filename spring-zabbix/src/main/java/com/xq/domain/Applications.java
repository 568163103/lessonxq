package com.xq.domain;

public class Applications {
    private Long applicationid;

    private Long hostid;

    private String name;

    private Integer flags;

    public Long getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(Long applicationid) {
        this.applicationid = applicationid;
    }

    public Long getHostid() {
        return hostid;
    }

    public void setHostid(Long hostid) {
        this.hostid = hostid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }
}