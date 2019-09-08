package com.xq.domain;

public class ResAlarmChannel {
    /**
    * ID,must be union code
    */
    private String alarmChannelId;

    /**
    * ID,must be union code
    */
    private String channelId;

    public String getAlarmChannelId() {
        return alarmChannelId;
    }

    public void setAlarmChannelId(String alarmChannelId) {
        this.alarmChannelId = alarmChannelId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}