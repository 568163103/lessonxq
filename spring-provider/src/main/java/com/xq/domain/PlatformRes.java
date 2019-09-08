package com.xq.domain;

public class PlatformRes {
    /**
    * node ID,must be union code
    */
    private String id;

    /**
    * name
    */
    private String name;

    /**
    * parent ID, empty if it is root
    */
    private String parentId;

    /**
    * SA ID
    */
    private String serverId;

    /**
    * children count
    */
    private Integer subnum;

    /**
    * 0 indicate no PTZ
    */
    private Integer hasPtz;

    /**
    * video codec,@see MediaCodec
    */
    private Integer codec;

    /**
    * longitude
    */
    private Double longitude;

    /**
    * latitude
    */
    private Double latitude;

    /**
    * online status, 0 indicate offline
    */
    private Boolean status;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public Integer getSubnum() {
        return subnum;
    }

    public void setSubnum(Integer subnum) {
        this.subnum = subnum;
    }

    public Integer getHasPtz() {
        return hasPtz;
    }

    public void setHasPtz(Integer hasPtz) {
        this.hasPtz = hasPtz;
    }

    public Integer getCodec() {
        return codec;
    }

    public void setCodec(Integer codec) {
        this.codec = codec;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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