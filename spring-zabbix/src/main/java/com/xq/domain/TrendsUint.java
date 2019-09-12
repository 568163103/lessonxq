package com.xq.domain;

public class TrendsUint {
    private Long itemid;

    private Integer clock;

    private Integer num;

    private Long valueMin;

    private Long valueAvg;

    private Long valueMax;

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Integer getClock() {
        return clock;
    }

    public void setClock(Integer clock) {
        this.clock = clock;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getValueMin() {
        return valueMin;
    }

    public void setValueMin(Long valueMin) {
        this.valueMin = valueMin;
    }

    public Long getValueAvg() {
        return valueAvg;
    }

    public void setValueAvg(Long valueAvg) {
        this.valueAvg = valueAvg;
    }

    public Long getValueMax() {
        return valueMax;
    }

    public void setValueMax(Long valueMax) {
        this.valueMax = valueMax;
    }
}