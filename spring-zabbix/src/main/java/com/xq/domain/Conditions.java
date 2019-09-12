package com.xq.domain;

public class Conditions {
    private Long conditionid;

    private Long actionid;

    private Integer conditiontype;

    private Integer operator;

    private String value;

    public Long getConditionid() {
        return conditionid;
    }

    public void setConditionid(Long conditionid) {
        this.conditionid = conditionid;
    }

    public Long getActionid() {
        return actionid;
    }

    public void setActionid(Long actionid) {
        this.actionid = actionid;
    }

    public Integer getConditiontype() {
        return conditiontype;
    }

    public void setConditiontype(Integer conditiontype) {
        this.conditiontype = conditiontype;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}