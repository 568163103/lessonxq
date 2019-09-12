package com.xq.domain;

public class Trends {
    private Long itemid;

    private Integer clock;

    private Integer num;

    private Double valueMin;

    private Double valueAvg;

    private Double valueMax;

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

    public Double getValueMin() {
        return valueMin;
    }

    public void setValueMin(Double valueMin) {
        this.valueMin = valueMin;
    }

    public Double getValueAvg() {
        return valueAvg;
    }

    public void setValueAvg(Double valueAvg) {
        this.valueAvg = valueAvg;
    }

    public Double getValueMax() {
        return valueMax;
    }

    public void setValueMax(Double valueMax) {
        this.valueMax = valueMax;
    }
}