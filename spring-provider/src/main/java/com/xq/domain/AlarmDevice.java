package com.xq.domain;

public class AlarmDevice {
    private String id;

    /**
    * name, unique
    */
    private String name;

    /**
    * device channel count
    */
    private Integer channelCount;

    /**
    * IP
    */
    private String ip;

    /**
    * port
    */
    private Integer port;

    /**
    * user name
    */
    private String username;

    /**
    * user password
    */
    private String password;

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

    public Integer getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(Integer channelCount) {
        this.channelCount = channelCount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
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