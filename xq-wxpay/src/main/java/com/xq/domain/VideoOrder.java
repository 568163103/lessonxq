package com.xq.domain;

import java.util.Date;

public class VideoOrder {
    private Integer id;

    /**
    * 用户标示
    */
    private String openid;

    /**
    * 订单唯一标识
    */
    private String outTradeNo;

    /**
    * 0表示未支付，1表示已支付
    */
    private Integer state;

    /**
    * 订单生成时间
    */
    private Date createTime;

    /**
    * 支付回调时间
    */
    private Date notifyTime;

    /**
    * 支付金额，单位分
    */
    private Integer totalFee;

    /**
    * 微信昵称
    */
    private String nickname;

    /**
    * 微信头像
    */
    private String headImg;

    /**
    * 视频主键
    */
    private Integer videoId;

    /**
    * 视频名称
    */
    private String videoTitle;

    /**
    * 视频图片
    */
    private String videoImg;

    /**
    * 用户id
    */
    private Integer userId;

    /**
    * 用户ip地址
    */
    private String ip;

    /**
    * 0表示未删除，1表示已经删除
    */
    private Integer del;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }
}