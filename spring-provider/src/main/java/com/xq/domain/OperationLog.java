package com.xq.domain;

public class OperationLog {
    /**
    * log ID
    */
    private Integer id;

    /**
    * user ID
    */
    private String userId;

    /**
    * user name
    */
    private String userName;

    /**
    * terminal IP
    */
    private String terminalIp;

    /**
    * date time
    */
    private String time;

    /**
    * operation name
    */
    private String operation;

    /**
    * object ID
    */
    private String objectId;

    /**
    * object time
    */
    private String objectTime;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectTime() {
        return objectTime;
    }

    public void setObjectTime(String objectTime) {
        this.objectTime = objectTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}