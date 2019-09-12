package com.xq.domain;

public class Operations {
    private Long operationid;

    private Long actionid;

    private Integer operationtype;

    private Integer escPeriod;

    private Integer escStepFrom;

    private Integer escStepTo;

    private Integer evaltype;

    public Long getOperationid() {
        return operationid;
    }

    public void setOperationid(Long operationid) {
        this.operationid = operationid;
    }

    public Long getActionid() {
        return actionid;
    }

    public void setActionid(Long actionid) {
        this.actionid = actionid;
    }

    public Integer getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(Integer operationtype) {
        this.operationtype = operationtype;
    }

    public Integer getEscPeriod() {
        return escPeriod;
    }

    public void setEscPeriod(Integer escPeriod) {
        this.escPeriod = escPeriod;
    }

    public Integer getEscStepFrom() {
        return escStepFrom;
    }

    public void setEscStepFrom(Integer escStepFrom) {
        this.escStepFrom = escStepFrom;
    }

    public Integer getEscStepTo() {
        return escStepTo;
    }

    public void setEscStepTo(Integer escStepTo) {
        this.escStepTo = escStepTo;
    }

    public Integer getEvaltype() {
        return evaltype;
    }

    public void setEvaltype(Integer evaltype) {
        this.evaltype = evaltype;
    }
}