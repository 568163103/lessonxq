package com.xq.domain;

public class Slides {
    private Long slideid;

    private Long slideshowid;

    private Long screenid;

    private Integer step;

    private Integer delay;

    public Long getSlideid() {
        return slideid;
    }

    public void setSlideid(Long slideid) {
        this.slideid = slideid;
    }

    public Long getSlideshowid() {
        return slideshowid;
    }

    public void setSlideshowid(Long slideshowid) {
        this.slideshowid = slideshowid;
    }

    public Long getScreenid() {
        return screenid;
    }

    public void setScreenid(Long screenid) {
        this.screenid = screenid;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}