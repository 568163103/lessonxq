package com.mingsoft.nvssauthor.domain;

public class RouteHop {
    private Integer routeId;

    /**
    * sequence num, start from 0
    */
    private Integer hopNum;

    /**
    * server ID
    */
    private String serverId;

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getHopNum() {
        return hopNum;
    }

    public void setHopNum(Integer hopNum) {
        this.hopNum = hopNum;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}