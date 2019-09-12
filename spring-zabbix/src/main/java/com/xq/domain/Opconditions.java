package com.xq.domain;

public class Opconditions {
    private Long opconditionid;

    private Long operationid;

    private Integer conditiontype;

    private Integer operator;

    private String value;

    public Long getOpconditionid() {
        return opconditionid;
    }

    public void setOpconditionid(Long opconditionid) {
        this.opconditionid = opconditionid;
    }

    public Long getOperationid() {
        return operationid;
    }

    public void setOperationid(Long operationid) {
        this.operationid = operationid;
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