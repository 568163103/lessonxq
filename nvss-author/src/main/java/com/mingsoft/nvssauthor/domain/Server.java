package com.mingsoft.nvssauthor.domain;

public class Server {
    /**
    * server ID, must be union code
    */
    private String id;

    /**
    * name, must be unique
    */
    private String name;

    /**
    * @see ServerType
    */
    private Integer type;

    /**
    * ip
    */
    private String ip;

    /**
    * port to CMU
    */
    private Short port;

    /**
    * register name for CMU
    */
    private String username;

    /**
    * register password for CMU
    */
    private String password;

    /**
    * max connection count
    */
    private Integer maxConnection;

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

    /**
    * 是否禁止外设
    */
    private Integer prohibitedStatus;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(Integer maxConnection) {
        this.maxConnection = maxConnection;
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

    public Integer getProhibitedStatus() {
        return prohibitedStatus;
    }

    public void setProhibitedStatus(Integer prohibitedStatus) {
        this.prohibitedStatus = prohibitedStatus;
    }
}