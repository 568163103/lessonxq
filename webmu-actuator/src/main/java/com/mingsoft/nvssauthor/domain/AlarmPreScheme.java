package com.mingsoft.nvssauthor.domain;

public class AlarmPreScheme {
    /**
    * alarm source ID
    */
    private String sourceId;

    /**
    * alarm type
    */
    private Integer alarmType;

    /**
    * scheme ID
    */
    private Integer schemaId;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }
}