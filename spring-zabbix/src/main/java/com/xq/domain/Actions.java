package com.xq.domain;

public class Actions {
    private Long actionid;

    private String name;

    private Integer eventsource;

    private Integer evaltype;

    private Integer status;

    private Integer escPeriod;

    private String defShortdata;

    private String defLongdata;

    private Integer recoveryMsg;

    private String rShortdata;

    private String rLongdata;

    private String formula;

    public Long getActionid() {
        return actionid;
    }

    public void setActionid(Long actionid) {
        this.actionid = actionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEventsource() {
        return eventsource;
    }

    public void setEventsource(Integer eventsource) {
        this.eventsource = eventsource;
    }

    public Integer getEvaltype() {
        return evaltype;
    }

    public void setEvaltype(Integer evaltype) {
        this.evaltype = evaltype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEscPeriod() {
        return escPeriod;
    }

    public void setEscPeriod(Integer escPeriod) {
        this.escPeriod = escPeriod;
    }

    public String getDefShortdata() {
        return defShortdata;
    }

    public void setDefShortdata(String defShortdata) {
        this.defShortdata = defShortdata;
    }

    public String getDefLongdata() {
        return defLongdata;
    }

    public void setDefLongdata(String defLongdata) {
        this.defLongdata = defLongdata;
    }

    public Integer getRecoveryMsg() {
        return recoveryMsg;
    }

    public void setRecoveryMsg(Integer recoveryMsg) {
        this.recoveryMsg = recoveryMsg;
    }

    public String getrShortdata() {
        return rShortdata;
    }

    public void setrShortdata(String rShortdata) {
        this.rShortdata = rShortdata;
    }

    public String getrLongdata() {
        return rLongdata;
    }

    public void setrLongdata(String rLongdata) {
        this.rLongdata = rLongdata;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}