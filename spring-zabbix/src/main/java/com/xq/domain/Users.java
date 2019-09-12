package com.xq.domain;

public class Users {
    private Long userid;

    private String alias;

    private String name;

    private String surname;

    private String passwd;

    private String url;

    private Integer autologin;

    private Integer autologout;

    private String lang;

    private Integer refresh;

    private Integer type;

    private String theme;

    private Integer attemptFailed;

    private String attemptIp;

    private Integer attemptClock;

    private Integer rowsPerPage;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getAutologin() {
        return autologin;
    }

    public void setAutologin(Integer autologin) {
        this.autologin = autologin;
    }

    public Integer getAutologout() {
        return autologout;
    }

    public void setAutologout(Integer autologout) {
        this.autologout = autologout;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getAttemptFailed() {
        return attemptFailed;
    }

    public void setAttemptFailed(Integer attemptFailed) {
        this.attemptFailed = attemptFailed;
    }

    public String getAttemptIp() {
        return attemptIp;
    }

    public void setAttemptIp(String attemptIp) {
        this.attemptIp = attemptIp;
    }

    public Integer getAttemptClock() {
        return attemptClock;
    }

    public void setAttemptClock(Integer attemptClock) {
        this.attemptClock = attemptClock;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }
}