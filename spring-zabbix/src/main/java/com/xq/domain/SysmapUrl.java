package com.xq.domain;

public class SysmapUrl {
    private Long sysmapurlid;

    private Long sysmapid;

    private String name;

    private String url;

    private Integer elementtype;

    public Long getSysmapurlid() {
        return sysmapurlid;
    }

    public void setSysmapurlid(Long sysmapurlid) {
        this.sysmapurlid = sysmapurlid;
    }

    public Long getSysmapid() {
        return sysmapid;
    }

    public void setSysmapid(Long sysmapid) {
        this.sysmapid = sysmapid;
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

    public Integer getElementtype() {
        return elementtype;
    }

    public void setElementtype(Integer elementtype) {
        this.elementtype = elementtype;
    }
}