package com.xq.domain;

public class Services {
    private Long serviceid;

    private String name;

    private Integer status;

    private Integer algorithm;

    private Long triggerid;

    private Integer showsla;

    private Double goodsla;

    private Integer sortorder;

    public Long getServiceid() {
        return serviceid;
    }

    public void setServiceid(Long serviceid) {
        this.serviceid = serviceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Integer algorithm) {
        this.algorithm = algorithm;
    }

    public Long getTriggerid() {
        return triggerid;
    }

    public void setTriggerid(Long triggerid) {
        this.triggerid = triggerid;
    }

    public Integer getShowsla() {
        return showsla;
    }

    public void setShowsla(Integer showsla) {
        this.showsla = showsla;
    }

    public Double getGoodsla() {
        return goodsla;
    }

    public void setGoodsla(Double goodsla) {
        this.goodsla = goodsla;
    }

    public Integer getSortorder() {
        return sortorder;
    }

    public void setSortorder(Integer sortorder) {
        this.sortorder = sortorder;
    }
}