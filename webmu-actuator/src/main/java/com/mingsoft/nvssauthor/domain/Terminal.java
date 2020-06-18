package com.mingsoft.nvssauthor.domain;

public class Terminal {
    /**
    * ID,must be union code
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
    * ip
    */
    private String ip;

    /**
    * physical address
    */
    private String address;

    /**
    * unlock key
    */
    private String unlockKey;

    /**
    * description
    */
    private String description;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnlockKey() {
        return unlockKey;
    }

    public void setUnlockKey(String unlockKey) {
        this.unlockKey = unlockKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}