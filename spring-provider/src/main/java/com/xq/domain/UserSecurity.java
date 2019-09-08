package com.xq.domain;

public class UserSecurity {
    /**
    * 用户ID
    */
    private String userId;

    private Integer errorCount;

    /**
    * 最后一次输错密码时间(yyyy-MM-dd HH:mm:ss)
    */
    private String lastErrorLoginTime;

    /**
    * 描述
    */
    private String description;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public String getLastErrorLoginTime() {
        return lastErrorLoginTime;
    }

    public void setLastErrorLoginTime(String lastErrorLoginTime) {
        this.lastErrorLoginTime = lastErrorLoginTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}