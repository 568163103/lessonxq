package com.xq.domain;

public class Dchecks {
    private Long dcheckid;

    private Long druleid;

    private Integer type;

    private String key;

    private String snmpCommunity;

    private String ports;

    private String snmpv3Securityname;

    private Integer snmpv3Securitylevel;

    private String snmpv3Authpassphrase;

    private String snmpv3Privpassphrase;

    private Integer uniq;

    private Integer snmpv3Authprotocol;

    private Integer snmpv3Privprotocol;

    private String snmpv3Contextname;

    public Long getDcheckid() {
        return dcheckid;
    }

    public void setDcheckid(Long dcheckid) {
        this.dcheckid = dcheckid;
    }

    public Long getDruleid() {
        return druleid;
    }

    public void setDruleid(Long druleid) {
        this.druleid = druleid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSnmpCommunity() {
        return snmpCommunity;
    }

    public void setSnmpCommunity(String snmpCommunity) {
        this.snmpCommunity = snmpCommunity;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getSnmpv3Securityname() {
        return snmpv3Securityname;
    }

    public void setSnmpv3Securityname(String snmpv3Securityname) {
        this.snmpv3Securityname = snmpv3Securityname;
    }

    public Integer getSnmpv3Securitylevel() {
        return snmpv3Securitylevel;
    }

    public void setSnmpv3Securitylevel(Integer snmpv3Securitylevel) {
        this.snmpv3Securitylevel = snmpv3Securitylevel;
    }

    public String getSnmpv3Authpassphrase() {
        return snmpv3Authpassphrase;
    }

    public void setSnmpv3Authpassphrase(String snmpv3Authpassphrase) {
        this.snmpv3Authpassphrase = snmpv3Authpassphrase;
    }

    public String getSnmpv3Privpassphrase() {
        return snmpv3Privpassphrase;
    }

    public void setSnmpv3Privpassphrase(String snmpv3Privpassphrase) {
        this.snmpv3Privpassphrase = snmpv3Privpassphrase;
    }

    public Integer getUniq() {
        return uniq;
    }

    public void setUniq(Integer uniq) {
        this.uniq = uniq;
    }

    public Integer getSnmpv3Authprotocol() {
        return snmpv3Authprotocol;
    }

    public void setSnmpv3Authprotocol(Integer snmpv3Authprotocol) {
        this.snmpv3Authprotocol = snmpv3Authprotocol;
    }

    public Integer getSnmpv3Privprotocol() {
        return snmpv3Privprotocol;
    }

    public void setSnmpv3Privprotocol(Integer snmpv3Privprotocol) {
        this.snmpv3Privprotocol = snmpv3Privprotocol;
    }

    public String getSnmpv3Contextname() {
        return snmpv3Contextname;
    }

    public void setSnmpv3Contextname(String snmpv3Contextname) {
        this.snmpv3Contextname = snmpv3Contextname;
    }
}