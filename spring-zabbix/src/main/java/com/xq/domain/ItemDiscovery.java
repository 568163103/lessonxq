package com.xq.domain;

public class ItemDiscovery {
    private Long itemdiscoveryid;

    private Long itemid;

    private Long parentItemid;

    private String key;

    private Integer lastcheck;

    private Integer tsDelete;

    public Long getItemdiscoveryid() {
        return itemdiscoveryid;
    }

    public void setItemdiscoveryid(Long itemdiscoveryid) {
        this.itemdiscoveryid = itemdiscoveryid;
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Long getParentItemid() {
        return parentItemid;
    }

    public void setParentItemid(Long parentItemid) {
        this.parentItemid = parentItemid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getLastcheck() {
        return lastcheck;
    }

    public void setLastcheck(Integer lastcheck) {
        this.lastcheck = lastcheck;
    }

    public Integer getTsDelete() {
        return tsDelete;
    }

    public void setTsDelete(Integer tsDelete) {
        this.tsDelete = tsDelete;
    }
}