package com.xq.domain;

public class TbResShieldPlan {
    private Integer id;

    /**
    * server id
    */
    private String sid;

    /**
    * cu id
    */
    private String userId;

    /**
    * cu level
    */
    private Integer userLevel;

    /**
    * 0 forbidden,1 noforbidden
    */
    private Integer status;

    /**
    * start time
    */
    private String beginTime;

    /**
    * end time
    */
    private String endTime;

    /**
    * schdule create time
    */
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}