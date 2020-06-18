package com.mingsoft.nvssauthor.domain;

public class Host {
    /**
    * 服务器ID
    */
    private String id;

    /**
    * 服务器名称
    */
    private String serverName;

    /**
    * 服务器ip1
    */
    private String ip1;

    /**
    * 服务器ip2
    */
    private String ip2;

    /**
    * 服务器ip3
    */
    private String ip3;

    /**
    * 服务器ip4
    */
    private String ip4;

    /**
    * 部署位置
    */
    private String serverPosition;

    /**
    * 系统版本
    */
    private String systemVersion;

    /**
    * cpu核心数
    */
    private Long cpuCoreCount;

    /**
    * 内存大小
    */
    private Long memoryCount;

    /**
    * 硬盘大小
    */
    private Long diskCount;

    /**
    * 0 离线 1在线
    */
    private Integer status;

    /**
    * 插入时间
    */
    private String insertionTime;

    /**
    * 更新时间
    */
    private String updateTime;

    /**
    * 备用字段1
    */
    private String spare1;

    /**
    * 备用字段2
    */
    private Long spare2;

    /**
    * 备用字段3
    */
    private String spare3;

    /**
    * 备用字段4
    */
    private Long spare4;

    /**
    * 备用字段5
    */
    private String spare5;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getIp1() {
        return ip1;
    }

    public void setIp1(String ip1) {
        this.ip1 = ip1;
    }

    public String getIp2() {
        return ip2;
    }

    public void setIp2(String ip2) {
        this.ip2 = ip2;
    }

    public String getIp3() {
        return ip3;
    }

    public void setIp3(String ip3) {
        this.ip3 = ip3;
    }

    public String getIp4() {
        return ip4;
    }

    public void setIp4(String ip4) {
        this.ip4 = ip4;
    }

    public String getServerPosition() {
        return serverPosition;
    }

    public void setServerPosition(String serverPosition) {
        this.serverPosition = serverPosition;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public Long getCpuCoreCount() {
        return cpuCoreCount;
    }

    public void setCpuCoreCount(Long cpuCoreCount) {
        this.cpuCoreCount = cpuCoreCount;
    }

    public Long getMemoryCount() {
        return memoryCount;
    }

    public void setMemoryCount(Long memoryCount) {
        this.memoryCount = memoryCount;
    }

    public Long getDiskCount() {
        return diskCount;
    }

    public void setDiskCount(Long diskCount) {
        this.diskCount = diskCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInsertionTime() {
        return insertionTime;
    }

    public void setInsertionTime(String insertionTime) {
        this.insertionTime = insertionTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1;
    }

    public Long getSpare2() {
        return spare2;
    }

    public void setSpare2(Long spare2) {
        this.spare2 = spare2;
    }

    public String getSpare3() {
        return spare3;
    }

    public void setSpare3(String spare3) {
        this.spare3 = spare3;
    }

    public Long getSpare4() {
        return spare4;
    }

    public void setSpare4(Long spare4) {
        this.spare4 = spare4;
    }

    public String getSpare5() {
        return spare5;
    }

    public void setSpare5(String spare5) {
        this.spare5 = spare5;
    }
}