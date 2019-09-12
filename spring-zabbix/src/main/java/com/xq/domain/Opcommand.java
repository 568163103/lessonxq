package com.xq.domain;

public class Opcommand {
    private Long operationid;

    private Integer type;

    private Long scriptid;

    private Integer executeOn;

    private String port;

    private Integer authtype;

    private String username;

    private String password;

    private String publickey;

    private String privatekey;

    private String command;

    public Long getOperationid() {
        return operationid;
    }

    public void setOperationid(Long operationid) {
        this.operationid = operationid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getScriptid() {
        return scriptid;
    }

    public void setScriptid(Long scriptid) {
        this.scriptid = scriptid;
    }

    public Integer getExecuteOn() {
        return executeOn;
    }

    public void setExecuteOn(Integer executeOn) {
        this.executeOn = executeOn;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getAuthtype() {
        return authtype;
    }

    public void setAuthtype(Integer authtype) {
        this.authtype = authtype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}