package com.xq.domain;

public class SysmapsLinks {
    private Long linkid;

    private Long sysmapid;

    private Long selementid1;

    private Long selementid2;

    private Integer drawtype;

    private String color;

    private String label;

    public Long getLinkid() {
        return linkid;
    }

    public void setLinkid(Long linkid) {
        this.linkid = linkid;
    }

    public Long getSysmapid() {
        return sysmapid;
    }

    public void setSysmapid(Long sysmapid) {
        this.sysmapid = sysmapid;
    }

    public Long getSelementid1() {
        return selementid1;
    }

    public void setSelementid1(Long selementid1) {
        this.selementid1 = selementid1;
    }

    public Long getSelementid2() {
        return selementid2;
    }

    public void setSelementid2(Long selementid2) {
        this.selementid2 = selementid2;
    }

    public Integer getDrawtype() {
        return drawtype;
    }

    public void setDrawtype(Integer drawtype) {
        this.drawtype = drawtype;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}