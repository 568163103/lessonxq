package com.xq.domain;

import java.util.Date;

public class Video {
    private Integer id;

    /**
    * 视频标题
    */
    private String title;

    /**
    * 概述
    */
    private String summary;

    /**
    * 封面图
    */
    private String coverImg;

    /**
    * 观看数
    */
    private Integer viewNum;

    /**
    * 价格,分
    */
    private Integer price;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 0表示未上线，1表示上线
    */
    private Integer online;

    /**
    * 默认8.7，最高10分
    */
    private Double point;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }
}