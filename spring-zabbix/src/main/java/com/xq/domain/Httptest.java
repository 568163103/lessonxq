package com.xq.domain;

public class Httptest {
    private Long httptestid;

    private String name;

    private Long applicationid;

    private Integer nextcheck;

    private Integer delay;

    private Integer status;

    private String variables;

    private String agent;

    private Integer authentication;

    private String httpUser;

    private String httpPassword;

    private Long hostid;

    private Long templateid;

    private String httpProxy;

    private Integer retries;

    private String sslCertFile;

    private String sslKeyFile;

    private String sslKeyPassword;

    private Integer verifyPeer;

    private Integer verifyHost;

    private String headers;

    public Long getHttptestid() {
        return httptestid;
    }

    public void setHttptestid(Long httptestid) {
        this.httptestid = httptestid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(Long applicationid) {
        this.applicationid = applicationid;
    }

    public Integer getNextcheck() {
        return nextcheck;
    }

    public void setNextcheck(Integer nextcheck) {
        this.nextcheck = nextcheck;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Integer getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Integer authentication) {
        this.authentication = authentication;
    }

    public String getHttpUser() {
        return httpUser;
    }

    public void setHttpUser(String httpUser) {
        this.httpUser = httpUser;
    }

    public String getHttpPassword() {
        return httpPassword;
    }

    public void setHttpPassword(String httpPassword) {
        this.httpPassword = httpPassword;
    }

    public Long getHostid() {
        return hostid;
    }

    public void setHostid(Long hostid) {
        this.hostid = hostid;
    }

    public Long getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Long templateid) {
        this.templateid = templateid;
    }

    public String getHttpProxy() {
        return httpProxy;
    }

    public void setHttpProxy(String httpProxy) {
        this.httpProxy = httpProxy;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public String getSslCertFile() {
        return sslCertFile;
    }

    public void setSslCertFile(String sslCertFile) {
        this.sslCertFile = sslCertFile;
    }

    public String getSslKeyFile() {
        return sslKeyFile;
    }

    public void setSslKeyFile(String sslKeyFile) {
        this.sslKeyFile = sslKeyFile;
    }

    public String getSslKeyPassword() {
        return sslKeyPassword;
    }

    public void setSslKeyPassword(String sslKeyPassword) {
        this.sslKeyPassword = sslKeyPassword;
    }

    public Integer getVerifyPeer() {
        return verifyPeer;
    }

    public void setVerifyPeer(Integer verifyPeer) {
        this.verifyPeer = verifyPeer;
    }

    public Integer getVerifyHost() {
        return verifyHost;
    }

    public void setVerifyHost(Integer verifyHost) {
        this.verifyHost = verifyHost;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
}