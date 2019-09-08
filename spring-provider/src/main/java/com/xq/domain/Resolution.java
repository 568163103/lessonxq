package com.xq.domain;

public class Resolution {
    /**
    * resolution ID, start from 1
    */
    private Integer id;

    /**
    * image width
    */
    private Integer width;

    /**
    * image height
    */
    private Integer height;

    /**
    * name
    */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}