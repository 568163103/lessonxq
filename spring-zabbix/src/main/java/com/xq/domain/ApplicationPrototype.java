package com.xq.domain;

public class ApplicationPrototype {
    private Long applicationPrototypeid;

    private Long itemid;

    private Long templateid;

    private String name;

    public Long getApplicationPrototypeid() {
        return applicationPrototypeid;
    }

    public void setApplicationPrototypeid(Long applicationPrototypeid) {
        this.applicationPrototypeid = applicationPrototypeid;
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Long getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Long templateid) {
        this.templateid = templateid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}