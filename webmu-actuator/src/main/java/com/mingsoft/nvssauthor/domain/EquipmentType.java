package com.mingsoft.nvssauthor.domain;

public class EquipmentType {
    /**
    * type value, start from 1
    */
    private Integer id;

    /**
    * type name
    */
    private String name;

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
}