package com.xq.domain;

public class Channel {
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
    * encoder ID
    */
    private String encoderId;

    /**
    * sequence num, start from 0
    */
    private Integer num;

    /**
    * 0 indicate no PTZ
    */
    private Integer hasPtz;

    /**
    * 0 indicate no audio
    */
    private Integer hasAudio;

    /**
    * stream count, can be 2 or more
    */
    private Integer streamCount;

    /**
    * description
    */
    private String description;

    /**
    * location
    */
    private String location;

    /**
    * purpose
    */
    private String purpose;

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

    public Integer getHasPtz() {
        return hasPtz;
    }

    public void setHasPtz(Integer hasPtz) {
        this.hasPtz = hasPtz;
    }

    public Integer getHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(Integer hasAudio) {
        this.hasAudio = hasAudio;
    }

    public Integer getStreamCount() {
        return streamCount;
    }

    public void setStreamCount(Integer streamCount) {
        this.streamCount = streamCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}