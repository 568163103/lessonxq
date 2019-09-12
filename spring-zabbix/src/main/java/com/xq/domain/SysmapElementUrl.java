package com.xq.domain;

public class SysmapElementUrl {
    private Long sysmapelementurlid;

    private Long selementid;

    private String name;

    private String url;

    public Long getSysmapelementurlid() {
        return sysmapelementurlid;
    }

    public void setSysmapelementurlid(Long sysmapelementurlid) {
        this.sysmapelementurlid = sysmapelementurlid;
    }

    public Long getSelementid() {
        return selementid;
    }

    public void setSelementid(Long selementid) {
        this.selementid = selementid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}