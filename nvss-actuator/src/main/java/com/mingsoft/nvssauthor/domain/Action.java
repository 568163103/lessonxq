package com.mingsoft.nvssauthor.domain;

public class Action {
    /**
    * action ID
    */
    private Integer id;

    /**
    * action name
    */
    private String name;

    /**
    * action type,@see  ActionType
    */
    private String type;

    /**
    * target ID
    */
    private String target;

    /**
    * in second
    */
    private Integer duration;

    /**
    * in second
    */
    private Integer aheadOfTime;

    /**
    * param
    */
    private String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getAheadOfTime() {
        return aheadOfTime;
    }

    public void setAheadOfTime(Integer aheadOfTime) {
        this.aheadOfTime = aheadOfTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}