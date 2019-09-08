package com.xq.domain;

public class VideowallWindowsGroup {
    /**
    * video_wall ID
    */
    private Integer videoWallId;

    /**
    * windows sequence num, start from 1
    */
    private Integer windowsNum;

    /**
    * sub_windows sequence num, start from 1,0 is not have
    */
    private Integer subWindowsNum;

    /**
    * turn_channel_group ID
    */
    private Integer turnChannelGroupId;

    /**
    * description
    */
    private String description;

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

    public Integer getSubWindowsNum() {
        return subWindowsNum;
    }

    public void setSubWindowsNum(Integer subWindowsNum) {
        this.subWindowsNum = subWindowsNum;
    }

    public Integer getTurnChannelGroupId() {
        return turnChannelGroupId;
    }

    public void setTurnChannelGroupId(Integer turnChannelGroupId) {
        this.turnChannelGroupId = turnChannelGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}