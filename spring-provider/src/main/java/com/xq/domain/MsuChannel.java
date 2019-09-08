package com.xq.domain;

public class MsuChannel {
    /**
    * channel ID,@see Channel.ID
    */
    private String channelId;

    /**
    * MSU ID
    */
    private String serverId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}