package com.mingsoft.nvssauthor.domain;

public class ImsGisInfo {
    /**
    * channel ID,@see Channel.ID
    */
    private String channelId;

    /**
    * yyyy-mm-dd hh:mm:ss
    */
    private String recvtime;

    /**
    * 0 online status
    */
    private Integer status;

    /**
    * longitude
    */
    private Double longitude;

    /**
    * latitude
    */
    private Double latitude;

    /**
    * direction
    */
    private Double direction;

    /**
    * dpitch
    */
    private Double dpitch;

    /**
    * angle
    */
    private Double angle;

    /**
    * curr range
    */
    private Double curRange;

    /**
    * speed
    */
    private Double speed;

    /**
    * height
    */
    private Double height;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getRecvtime() {
        return recvtime;
    }

    public void setRecvtime(String recvtime) {
        this.recvtime = recvtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getDirection() {
        return direction;
    }

    public void setDirection(Double direction) {
        this.direction = direction;
    }

    public Double getDpitch() {
        return dpitch;
    }

    public void setDpitch(Double dpitch) {
        this.dpitch = dpitch;
    }

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public Double getCurRange() {
        return curRange;
    }

    public void setCurRange(Double curRange) {
        this.curRange = curRange;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}