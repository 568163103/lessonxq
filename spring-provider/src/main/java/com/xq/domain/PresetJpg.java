package com.xq.domain;

public class PresetJpg {
    /**
    * channel ID, @see Channel.ID
    */
    private String channelId;

    /**
    * sequence num, start from 0
    */
    private Integer num;

    /**
    * image length
    */
    private Integer dataLen;

    /**
    * image data
    */
    private byte[] dataBuffer;

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

    public Integer getDataLen() {
        return dataLen;
    }

    public void setDataLen(Integer dataLen) {
        this.dataLen = dataLen;
    }

    public byte[] getDataBuffer() {
        return dataBuffer;
    }

    public void setDataBuffer(byte[] dataBuffer) {
        this.dataBuffer = dataBuffer;
    }
}