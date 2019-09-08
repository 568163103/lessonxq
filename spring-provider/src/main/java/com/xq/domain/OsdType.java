package com.xq.domain;

public class OsdType {
    /**
    * ID,must be union code
    */
    private String id;

    /**
    * 0 front osd,1 end osd
    */
    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}