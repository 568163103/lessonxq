package com.xq.domain;

public class TbUserAlarmRes {
    /**
    * user ID
    */
    private String userId;

    /**
    * tb_alarm_res id
    */
    private Integer alarmResId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAlarmResId() {
        return alarmResId;
    }

    public void setAlarmResId(Integer alarmResId) {
        this.alarmResId = alarmResId;
    }
}