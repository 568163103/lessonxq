package com.mingsoft.nvssauthor.domain;

public class EncoderOutput {
    /**
    * ID,must be union code
    */
    private String id;

    /**
    * name
    */
    private String name;

    /**
    * enabled flag,0 indicate disabled
    */
    private Boolean enabled;

    /**
    * encoder ID
    */
    private String encoderId;

    /**
    * sequence num, start from 0
    */
    private Integer num;

    /**
    * electric status
    */
    private Integer status;

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

    public String getEncoderId() {
        return encoderId;
    }

    public void setEncoderId(String encoderId) {
        this.encoderId = encoderId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}