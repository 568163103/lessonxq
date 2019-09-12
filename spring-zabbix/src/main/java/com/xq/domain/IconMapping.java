package com.xq.domain;

public class IconMapping {
    private Long iconmappingid;

    private Long iconmapid;

    private Long iconid;

    private Integer inventoryLink;

    private String expression;

    private Integer sortorder;

    public Long getIconmappingid() {
        return iconmappingid;
    }

    public void setIconmappingid(Long iconmappingid) {
        this.iconmappingid = iconmappingid;
    }

    public Long getIconmapid() {
        return iconmapid;
    }

    public void setIconmapid(Long iconmapid) {
        this.iconmapid = iconmapid;
    }

    public Long getIconid() {
        return iconid;
    }

    public void setIconid(Long iconid) {
        this.iconid = iconid;
    }

    public Integer getInventoryLink() {
        return inventoryLink;
    }

    public void setInventoryLink(Integer inventoryLink) {
        this.inventoryLink = inventoryLink;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getSortorder() {
        return sortorder;
    }

    public void setSortorder(Integer sortorder) {
        this.sortorder = sortorder;
    }
}