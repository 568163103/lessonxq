package com.xq.domain;

public class CruiseTrack {
    /**
    * channel ID, @see Channel.ID
    */
    private String channelId;

    /**
    * sequence num, start from 1,end to 255
    */
    private Integer num;

    /**
    * name
    */
    private String name;

    /**
    * sec
    */
    private Integer dwellTime;

    /**
    * ptz Speed
    */
    private Integer speed;

    /**
    * default 0
    */
    private Integer flag;

    /**
    * #1#4#2
    */
    private String presetNum;

    /**
    * stop 0,start 1,pause 2
    */
    private Integer status;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDwellTime() {
        return dwellTime;
    }

    public void setDwellTime(Integer dwellTime) {
        this.dwellTime = dwellTime;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getPresetNum() {
        return presetNum;
    }

    public void setPresetNum(String presetNum) {
        this.presetNum = presetNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}