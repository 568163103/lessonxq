package com.xq.domain;

public class AlarmDeviceChannel {
    /**
    * sequence num, start from 0
    */
    private Integer num;

    /**
    * device ID, @see AlarmDevice
    */
    private String deviceId;

    /**
    * ID,must be union code
    */
    private String channelId;

    /**
    * name, unique
    */
    private String name;

    /**
    * description
    */
    private String description;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}