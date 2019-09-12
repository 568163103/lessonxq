package com.xq.domain;

public class Scripts {
    private Long scriptid;

    private String name;

    private String command;

    private Integer hostAccess;

    private Long usrgrpid;

    private Long groupid;

    private String description;

    private String confirmation;

    private Integer type;

    private Integer executeOn;

    public Long getScriptid() {
        return scriptid;
    }

    public void setScriptid(Long scriptid) {
        this.scriptid = scriptid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getHostAccess() {
        return hostAccess;
    }

    public void setHostAccess(Integer hostAccess) {
        this.hostAccess = hostAccess;
    }

    public Long getUsrgrpid() {
        return usrgrpid;
    }

    public void setUsrgrpid(Long usrgrpid) {
        this.usrgrpid = usrgrpid;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getExecuteOn() {
        return executeOn;
    }

    public void setExecuteOn(Integer executeOn) {
        this.executeOn = executeOn;
    }
}