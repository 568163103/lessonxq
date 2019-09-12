package com.xq.domain;

public class ProxyHistory {
    private Long id;

    private Long itemid;

    private Integer clock;

    private Integer timestamp;

    private String source;

    private Integer severity;

    private String value;

    private Integer logeventid;

    private Integer ns;

    private Integer state;

    private Long lastlogsize;

    private Integer mtime;

    private Integer flags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Integer getClock() {
        return clock;
    }

    public void setClock(Integer clock) {
        this.clock = clock;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getLogeventid() {
        return logeventid;
    }

    public void setLogeventid(Integer logeventid) {
        this.logeventid = logeventid;
    }

    public Integer getNs() {
        return ns;
    }

    public void setNs(Integer ns) {
        this.ns = ns;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getLastlogsize() {
        return lastlogsize;
    }

    public void setLastlogsize(Long lastlogsize) {
        this.lastlogsize = lastlogsize;
    }

    public Integer getMtime() {
        return mtime;
    }

    public void setMtime(Integer mtime) {
        this.mtime = mtime;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }
}