package com.mingsoft.nvssauthor.domain;

public class UserInfo {
    /**
    * @see
    */
    private String id;

    /**
    * user aliase
    */
    private String alias;

    /**
    * ptz control level,[1-8], 8 is the highest
    */
    private Integer ptzLevel;

    /**
    * video level, [1-8], 8 is the highest
    */
    private Integer avLevel;

    /**
    * 1 default user, 2 gis user
    */
    private Integer userType;

    /**
    * format: hh:mm:ss
    */
    private String activeBeginTime;

    /**
    * format: hh:mm:ss
    */
    private String activeEndTime;

    /**
    * phone number
    */
    private String phone;

    /**
    * mail number
    */
    private String mail;

    /**
    * last login time
    */
    private String lastLoginTime;

    /**
    * user description
    */
    private String description;

    /**
    * online status, 0 indicate offline
    */
    private Boolean status;

    /**
    * 用户部门
    */
    private String tbDept;

    /**
    * 用户级别
    */
    private Integer userLevel;

    /**
    * 用户最大视频路数
    */
    private Integer maxCameraNum;

    /**
    * 云台锁定时间
    */
    private Integer ptzLockTime;

    /**
    * 用户活动时间多个;隔开
    */
    private String activeTime;

    /**
    * 用户是否禁止外设
    */
    private Integer prohibitedStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getPtzLevel() {
        return ptzLevel;
    }

    public void setPtzLevel(Integer ptzLevel) {
        this.ptzLevel = ptzLevel;
    }

    public Integer getAvLevel() {
        return avLevel;
    }

    public void setAvLevel(Integer avLevel) {
        this.avLevel = avLevel;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getActiveBeginTime() {
        return activeBeginTime;
    }

    public void setActiveBeginTime(String activeBeginTime) {
        this.activeBeginTime = activeBeginTime;
    }

    public String getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(String activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public String getTbDept() {
        return tbDept;
    }

    public void setTbDept(String tbDept) {
        this.tbDept = tbDept;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getMaxCameraNum() {
        return maxCameraNum;
    }

    public void setMaxCameraNum(Integer maxCameraNum) {
        this.maxCameraNum = maxCameraNum;
    }

    public Integer getPtzLockTime() {
        return ptzLockTime;
    }

    public void setPtzLockTime(Integer ptzLockTime) {
        this.ptzLockTime = ptzLockTime;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public Integer getProhibitedStatus() {
        return prohibitedStatus;
    }

    public void setProhibitedStatus(Integer prohibitedStatus) {
        this.prohibitedStatus = prohibitedStatus;
    }
}