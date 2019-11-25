package com.mingsoft.nvssauthor.domain;

public class SystemConfig {
    private Integer id;

    /**
    * 密码连续错误次数
    */
    private Integer maxErrorCount;

    /**
    * 密码错误处理机制 0关闭 1开启
    */
    private Integer errorLoginConfig;

    /**
    * 错误锁定时间（秒）
    */
    private Integer userLockTime;

    /**
    * 密码失效开关 0 关闭 1开启
    */
    private Integer pwdResetFlag;

    /**
    * 密码有效期限(分钟)
    */
    private Integer keepPasswordTime;

    /**
    * 修改密码提醒时间
    */
    private Integer pwdRemaindTime;

    /**
    * 限制例外用户
    */
    private String exceptionUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxErrorCount() {
        return maxErrorCount;
    }

    public void setMaxErrorCount(Integer maxErrorCount) {
        this.maxErrorCount = maxErrorCount;
    }

    public Integer getErrorLoginConfig() {
        return errorLoginConfig;
    }

    public void setErrorLoginConfig(Integer errorLoginConfig) {
        this.errorLoginConfig = errorLoginConfig;
    }

    public Integer getUserLockTime() {
        return userLockTime;
    }

    public void setUserLockTime(Integer userLockTime) {
        this.userLockTime = userLockTime;
    }

    public Integer getPwdResetFlag() {
        return pwdResetFlag;
    }

    public void setPwdResetFlag(Integer pwdResetFlag) {
        this.pwdResetFlag = pwdResetFlag;
    }

    public Integer getKeepPasswordTime() {
        return keepPasswordTime;
    }

    public void setKeepPasswordTime(Integer keepPasswordTime) {
        this.keepPasswordTime = keepPasswordTime;
    }

    public Integer getPwdRemaindTime() {
        return pwdRemaindTime;
    }

    public void setPwdRemaindTime(Integer pwdRemaindTime) {
        this.pwdRemaindTime = pwdRemaindTime;
    }

    public String getExceptionUser() {
        return exceptionUser;
    }

    public void setExceptionUser(String exceptionUser) {
        this.exceptionUser = exceptionUser;
    }
}