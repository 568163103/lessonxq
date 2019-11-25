package com.mingsoft.nvssauthor.domain;

public class TbUser {
    /**
    * user ID, must be union code
    */
    private String id;

    private String name;

    private String sid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}