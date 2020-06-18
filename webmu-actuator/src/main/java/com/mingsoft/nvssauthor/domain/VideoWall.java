package com.mingsoft.nvssauthor.domain;

public class VideoWall {
    /**
    * videowall ID
    */
    private Integer id;

    /**
    * config info
    */
    private String configInfo;

    /**
    * description
    */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}