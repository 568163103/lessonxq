package com.xq.domain;

public class ServicesLinks {
    private Long linkid;

    private Long serviceupid;

    private Long servicedownid;

    private Integer soft;

    public Long getLinkid() {
        return linkid;
    }

    public void setLinkid(Long linkid) {
        this.linkid = linkid;
    }

    public Long getServiceupid() {
        return serviceupid;
    }

    public void setServiceupid(Long serviceupid) {
        this.serviceupid = serviceupid;
    }

    public Long getServicedownid() {
        return servicedownid;
    }

    public void setServicedownid(Long servicedownid) {
        this.servicedownid = servicedownid;
    }

    public Integer getSoft() {
        return soft;
    }

    public void setSoft(Integer soft) {
        this.soft = soft;
    }
}