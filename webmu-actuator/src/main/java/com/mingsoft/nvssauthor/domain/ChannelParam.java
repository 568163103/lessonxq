package com.mingsoft.nvssauthor.domain;

public class ChannelParam {
    /**
    * channel ID,@see Channel.ID
    */
    private String channelId;

    /**
    * 0 default , 1 SmokeDetect , 2 Infrared
    */
    private Integer channelType;

    /**
    * 0 default , 1 FUJINON , 2 KOWA
    */
    private Integer sceneType;

    /**
    * 0 default
    */
    private Integer decoderAddress;

    /**
    * 0 default, 1 yaan
    */
    private Integer ptzModel;

    /**
    * 0 pelco-d, 1 pelco-p
    */
    private Integer ptzProtocol;

    /**
    * 0 default,1 485,2 422,3 232
    */
    private Integer transComType;

    /**
    * 2400 default
    */
    private Integer baudrate;

    /**
    * 0 default
    */
    private Integer checkbit;

    /**
    * 8 default
    */
    private Integer databit;

    /**
    * 0 default
    */
    private Integer flowcontrol;

    /**
    * 1 default
    */
    private Integer stopbit;

    /**
    * SmokeDetect param
    */
    private String vaparam;

    /**
    * InterDevice ID, @see InterDevice.DeviceID
    */
    private Integer interDeviceId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getSceneType() {
        return sceneType;
    }

    public void setSceneType(Integer sceneType) {
        this.sceneType = sceneType;
    }

    public Integer getDecoderAddress() {
        return decoderAddress;
    }

    public void setDecoderAddress(Integer decoderAddress) {
        this.decoderAddress = decoderAddress;
    }

    public Integer getPtzModel() {
        return ptzModel;
    }

    public void setPtzModel(Integer ptzModel) {
        this.ptzModel = ptzModel;
    }

    public Integer getPtzProtocol() {
        return ptzProtocol;
    }

    public void setPtzProtocol(Integer ptzProtocol) {
        this.ptzProtocol = ptzProtocol;
    }

    public Integer getTransComType() {
        return transComType;
    }

    public void setTransComType(Integer transComType) {
        this.transComType = transComType;
    }

    public Integer getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(Integer baudrate) {
        this.baudrate = baudrate;
    }

    public Integer getCheckbit() {
        return checkbit;
    }

    public void setCheckbit(Integer checkbit) {
        this.checkbit = checkbit;
    }

    public Integer getDatabit() {
        return databit;
    }

    public void setDatabit(Integer databit) {
        this.databit = databit;
    }

    public Integer getFlowcontrol() {
        return flowcontrol;
    }

    public void setFlowcontrol(Integer flowcontrol) {
        this.flowcontrol = flowcontrol;
    }

    public Integer getStopbit() {
        return stopbit;
    }

    public void setStopbit(Integer stopbit) {
        this.stopbit = stopbit;
    }

    public String getVaparam() {
        return vaparam;
    }

    public void setVaparam(String vaparam) {
        this.vaparam = vaparam;
    }

    public Integer getInterDeviceId() {
        return interDeviceId;
    }

    public void setInterDeviceId(Integer interDeviceId) {
        this.interDeviceId = interDeviceId;
    }
}