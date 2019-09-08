package com.xq.domain;

public class RecordPlan {
    /**
    * plan name,unique
    */
    private String name;

    /**
    * image resolution
    */
    private Integer resolution;

    /**
    * frame rate
    */
    private Integer frametype;

    /**
    * time span,7*24 bytes, character 0 indicate no record
    */
    private String timespan;

    /**
    * cycle day
    */
    private Integer cycleDate;

    /**
    * description
    */
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public Integer getFrametype() {
        return frametype;
    }

    public void setFrametype(Integer frametype) {
        this.frametype = frametype;
    }

    public String getTimespan() {
        return timespan;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }

    public Integer getCycleDate() {
        return cycleDate;
    }

    public void setCycleDate(Integer cycleDate) {
        this.cycleDate = cycleDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}