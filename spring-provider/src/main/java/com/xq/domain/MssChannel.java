package com.xq.domain;

public class MssChannel {
    /**
    * channel ID,@see Channel.ID
    */
    private String channelId;

    /**
    * MSS ID
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