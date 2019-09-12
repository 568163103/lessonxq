package com.xq.domain;

public class MediaType {
    private Long mediatypeid;

    private Integer type;

    private String description;

    private String smtpServer;

    private String smtpHelo;

    private String smtpEmail;

    private String execPath;

    private String gsmModem;

    private String username;

    private String passwd;

    private Integer status;

    private Integer smtpPort;

    private Integer smtpSecurity;

    private Integer smtpVerifyPeer;

    private Integer smtpVerifyHost;

    private Integer smtpAuthentication;

    private String execParams;

    public Long getMediatypeid() {
        return mediatypeid;
    }

    public void setMediatypeid(Long mediatypeid) {
        this.mediatypeid = mediatypeid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getSmtpHelo() {
        return smtpHelo;
    }

    public void setSmtpHelo(String smtpHelo) {
        this.smtpHelo = smtpHelo;
    }

    public String getSmtpEmail() {
        return smtpEmail;
    }

    public void setSmtpEmail(String smtpEmail) {
        this.smtpEmail = smtpEmail;
    }

    public String getExecPath() {
        return execPath;
    }

    public void setExecPath(String execPath) {
        this.execPath = execPath;
    }

    public String getGsmModem() {
        return gsmModem;
    }

    public void setGsmModem(String gsmModem) {
        this.gsmModem = gsmModem;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public Integer getSmtpSecurity() {
        return smtpSecurity;
    }

    public void setSmtpSecurity(Integer smtpSecurity) {
        this.smtpSecurity = smtpSecurity;
    }

    public Integer getSmtpVerifyPeer() {
        return smtpVerifyPeer;
    }

    public void setSmtpVerifyPeer(Integer smtpVerifyPeer) {
        this.smtpVerifyPeer = smtpVerifyPeer;
    }

    public Integer getSmtpVerifyHost() {
        return smtpVerifyHost;
    }

    public void setSmtpVerifyHost(Integer smtpVerifyHost) {
        this.smtpVerifyHost = smtpVerifyHost;
    }

    public Integer getSmtpAuthentication() {
        return smtpAuthentication;
    }

    public void setSmtpAuthentication(Integer smtpAuthentication) {
        this.smtpAuthentication = smtpAuthentication;
    }

    public String getExecParams() {
        return execParams;
    }

    public void setExecParams(String execParams) {
        this.execParams = execParams;
    }
}