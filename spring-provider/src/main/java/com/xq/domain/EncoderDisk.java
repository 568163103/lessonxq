package com.xq.domain;

public class EncoderDisk {
    /**
    * encoder ID,must be union code
    */
    private String id;

    /**
    * disk info
    */
    private String diskInfo;

    /**
    * lasttime
    */
    private String lasttime;

    /**
    * description
    */
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiskInfo() {
        return diskInfo;
    }

    public void setDiskInfo(String diskInfo) {
        this.diskInfo = diskInfo;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}