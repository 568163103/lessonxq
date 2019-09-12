package com.xq.domain;

public class ItemCondition {
    private Long itemConditionid;

    private Long itemid;

    private Integer operator;

    private String macro;

    private String value;

    public Long getItemConditionid() {
        return itemConditionid;
    }

    public void setItemConditionid(Long itemConditionid) {
        this.itemConditionid = itemConditionid;
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getMacro() {
        return macro;
    }

    public void setMacro(String macro) {
        this.macro = macro;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}