package com.xq.domain;

public class ChannelRecordPlan {
    /**
    * channel ID,@see Channel.ID
    */
    private String channelId;

    /**
    * plan name
    */
    private String planName;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}