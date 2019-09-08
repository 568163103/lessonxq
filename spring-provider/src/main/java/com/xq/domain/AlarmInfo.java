package com.xq.domain;

public class AlarmInfo {
    /**
    * alarm ID
    */
    private Integer id;

    /**
    * source ID
    */
    private String sourceId;

    /**
    * alarm type
    */
    private Integer alarmType;

    /**
    * name
    */
    private String name;

    /**
    * begin time
    */
    private String beginTime;

    /**
    * end time
    */
    private String endTime;

    /**
    * state,1 indicate close
    */
    private Integer state;

    /**
    * CRU ID indicate which CRU saved the stream
    */
    private String storageServerId;

    /**
    * 1 indicate correct,-1 indicate error, 0 indicate unknown
    */
    private Integer dealState;

    /**
    * user ID
    */
    private String dealUserId;

    /**
    * deal time
    */
    private String dealTime;

    /**
    * res id
    */
    private String storageResId;

    /**
    * res size
    */
    private String storageSize;

    /**
    * begin time
    */
    private String storageBeginTime;

    /**
    * end time
    */
    private String storageEndTime;

    /**
    * description
    */
    private String description;

    /**
    * memo
    */
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStorageServerId() {
        return storageServerId;
    }

    public void setStorageServerId(String storageServerId) {
        this.storageServerId = storageServerId;
    }

    public Integer getDealState() {
        return dealState;
    }

    public void setDealState(Integer dealState) {
        this.dealState = dealState;
    }

    public String getDealUserId() {
        return dealUserId;
    }

    public void setDealUserId(String dealUserId) {
        this.dealUserId = dealUserId;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getStorageResId() {
        return storageResId;
    }

    public void setStorageResId(String storageResId) {
        this.storageResId = storageResId;
    }

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    public String getStorageBeginTime() {
        return storageBeginTime;
    }

    public void setStorageBeginTime(String storageBeginTime) {
        this.storageBeginTime = storageBeginTime;
    }

    public String getStorageEndTime() {
        return storageEndTime;
    }

    public void setStorageEndTime(String storageEndTime) {
        this.storageEndTime = storageEndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}