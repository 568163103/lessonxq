package com.xq.domain;

public class Maintenances {
    private Long maintenanceid;

    private String name;

    private Integer maintenanceType;

    private String description;

    private Integer activeSince;

    private Integer activeTill;

    public Long getMaintenanceid() {
        return maintenanceid;
    }

    public void setMaintenanceid(Long maintenanceid) {
        this.maintenanceid = maintenanceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(Integer maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(Integer activeSince) {
        this.activeSince = activeSince;
    }

    public Integer getActiveTill() {
        return activeTill;
    }

    public void setActiveTill(Integer activeTill) {
        this.activeTill = activeTill;
    }
}