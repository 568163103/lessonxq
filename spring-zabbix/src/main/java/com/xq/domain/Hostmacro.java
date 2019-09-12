package com.xq.domain;

public class Hostmacro {
    private Long hostmacroid;

    private Long hostid;

    private String macro;

    private String value;

    public Long getHostmacroid() {
        return hostmacroid;
    }

    public void setHostmacroid(Long hostmacroid) {
        this.hostmacroid = hostmacroid;
    }

    public Long getHostid() {
        return hostid;
    }

    public void setHostid(Long hostid) {
        this.hostid = hostid;
    }

    public String getMacro() {
        return macro;
    }

    public void setMacro(String macro) {
        this.macro = macro;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}