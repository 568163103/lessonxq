package com.xq.domain;

public class Slideshows {
    private Long slideshowid;

    private String name;

    private Integer delay;

    private Long userid;

    private Integer private1;



    public Long getSlideshowid() {
        return slideshowid;
    }

    public void setSlideshowid(Long slideshowid) {
        this.slideshowid = slideshowid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getPrivate1() {
        return private1;
    }

    public void setPrivate1(Integer private1) {
        this.private1 = private1;
    }
}