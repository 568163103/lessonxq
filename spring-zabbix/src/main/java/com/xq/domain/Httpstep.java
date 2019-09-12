package com.xq.domain;

public class Httpstep {
    private Long httpstepid;

    private Long httptestid;

    private String name;

    private Integer no;

    private String url;

    private Integer timeout;

    private String posts;

    private String required;

    private String statusCodes;

    private String variables;

    private Integer followRedirects;

    private Integer retrieveMode;

    private String headers;

    public Long getHttpstepid() {
        return httpstepid;
    }

    public void setHttpstepid(Long httpstepid) {
        this.httpstepid = httpstepid;
    }

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

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getStatusCodes() {
        return statusCodes;
    }

    public void setStatusCodes(String statusCodes) {
        this.statusCodes = statusCodes;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public Integer getFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(Integer followRedirects) {
        this.followRedirects = followRedirects;
    }

    public Integer getRetrieveMode() {
        return retrieveMode;
    }

    public void setRetrieveMode(Integer retrieveMode) {
        this.retrieveMode = retrieveMode;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
}