package com.xq.domain;

public class DuChannelConfig {
    /**
    * du_channel ID,must be union code
    */
    private String duChannelId;

    /**
    * video_wall ID
    */
    private Integer videoWallId;

    /**
    * sequence num, start from 0
    */
    private Integer windowsNum;

    /**
    * config info
    */
    private String configInfo;

    /**
    * description
    */
    private String description;

    public String getDuChannelId() {
        return duChannelId;
    }

    public void setDuChannelId(String duChannelId) {
        this.duChannelId = duChannelId;
    }

    public Integer getVideoWallId() {
        return videoWallId;
    }

    public void setVideoWallId(Integer videoWallId) {
        this.videoWallId = videoWallId;
    }

    public Integer getWindowsNum() {
        return windowsNum;
    }

    public void setWindowsNum(Integer windowsNum) {
        this.windowsNum = windowsNum;
    }

    public String getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}