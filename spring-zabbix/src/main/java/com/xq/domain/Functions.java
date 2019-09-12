package com.xq.domain;

public class Functions {
    private Long functionid;

    private Long itemid;

    private Long triggerid;

    private String function;

    private String parameter;

    public Long getFunctionid() {
        return functionid;
    }

    public void setFunctionid(Long functionid) {
        this.functionid = functionid;
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Long getTriggerid() {
        return triggerid;
    }

    public void setTriggerid(Long triggerid) {
        this.triggerid = triggerid;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}