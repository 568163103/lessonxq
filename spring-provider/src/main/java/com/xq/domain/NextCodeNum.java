package com.xq.domain;

public class NextCodeNum {
    /**
    * entity type name
    */
    private String name;

    /**
    * type code,4 character
    */
    private String typeCode;

    /**
    * next num
    */
    private Integer num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}