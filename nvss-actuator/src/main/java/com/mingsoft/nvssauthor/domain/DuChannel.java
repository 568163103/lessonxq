package com.mingsoft.nvssauthor.domain;

public class DuChannel {
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
    * du ID
    */
    private String duId;

    /**
    * sequence num, start from 0
    */
    private Integer num;

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

    public String getDuId() {
        return duId;
    }

    public void setDuId(String duId) {
        this.duId = duId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}