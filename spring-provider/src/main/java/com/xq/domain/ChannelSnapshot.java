package com.xq.domain;

import java.util.Date;

public class ChannelSnapshot {
    private Integer id;

    /**
    * 外部唯一标识
    */
    private String otId;

    /**
    * 资源id
    */
    private String resId;

    /**
    * 事件类型
    */
    private String eventType;

    /**
    * 生成时间
    */
    private String createTime;

    /**
    * 图片存放相对路径
    */
    private String url;

    /**
    * 上传时间
    */
    private Date uploadTime;

    /**
    * 备注
    */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtId() {
        return otId;
    }

    public void setOtId(String otId) {
        this.otId = otId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}