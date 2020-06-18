package com.mingsoft.nvssauthor.domain;

import java.beans.Transient;

public class Encoder {
    /**
    * encoder ID,must be union code
    */
    private String id;

    /**
    * name, unique
    */
    private String name;

    /**
    * enabled flag,0 indicate disabled
    */
    private Boolean enabled;

    /**
    * enabled flag,0 indicate disabled
    */
    private Boolean hasAudio;

    /**
    * device model
    */
    private String model;

    /**
    * device ip
    */
    private String ip;

    /**
    * device port
    */
    private Short port;

    /**
    * login user name
    */
    private String username;

    /**
    * login user password
    */
    private String password;

    /**
    * video channel count
    */
    private Integer channelCount;

    /**
    * input count
    */
    private Integer inputCount;

    /**
    * output count
    */
    private Integer outputCount;

    /**
    * physical address
    */
    private String address;

    /**
    * description
    */
    private String description;

    /**
    * online status, 0 indicate offline
    */
    private Boolean status;

    public String getPosition() {
        return position;
    }

    @Transient
    public void setPosition(String position) {
        this.position = position;
    }

    private String position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(Boolean hasAudio) {
        this.hasAudio = hasAudio;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Short getPort() {
        return port;
    }

    public void setPort(Short port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(Integer channelCount) {
        this.channelCount = channelCount;
    }

    public Integer getInputCount() {
        return inputCount;
    }

    public void setInputCount(Integer inputCount) {
        this.inputCount = inputCount;
    }

    public Integer getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(Integer outputCount) {
        this.outputCount = outputCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}