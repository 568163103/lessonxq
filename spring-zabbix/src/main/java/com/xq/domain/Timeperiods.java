package com.xq.domain;

public class Timeperiods {
    private Long timeperiodid;

    private Integer timeperiodType;

    private Integer every;

    private Integer month;

    private Integer dayofweek;

    private Integer day;

    private Integer startTime;

    private Integer period;

    private Integer startDate;

    public Long getTimeperiodid() {
        return timeperiodid;
    }

    public void setTimeperiodid(Long timeperiodid) {
        this.timeperiodid = timeperiodid;
    }

    public Integer getTimeperiodType() {
        return timeperiodType;
    }

    public void setTimeperiodType(Integer timeperiodType) {
        this.timeperiodType = timeperiodType;
    }

    public Integer getEvery() {
        return every;
    }

    public void setEvery(Integer every) {
        this.every = every;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(Integer dayofweek) {
        this.dayofweek = dayofweek;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }
}