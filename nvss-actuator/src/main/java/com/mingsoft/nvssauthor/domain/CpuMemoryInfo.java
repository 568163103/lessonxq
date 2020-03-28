package com.mingsoft.nvssauthor.domain;

public class CpuMemoryInfo {
    private Long id;

    private String memoryUseCount;

    private Long memoryCount;

    private Integer cpuTotal;

    private String cpuUseCount;

    private Integer spare1;

    private String spare2;

    private String spare3;

    private Integer spare4;

    private String spare5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemoryUseCount() {
        return memoryUseCount;
    }

    public void setMemoryUseCount(String memoryUseCount) {
        this.memoryUseCount = memoryUseCount;
    }

    public Long getMemoryCount() {
        return memoryCount;
    }

    public void setMemoryCount(Long memoryCount) {
        this.memoryCount = memoryCount;
    }

    public Integer getCpuTotal() {
        return cpuTotal;
    }

    public void setCpuTotal(Integer cpuTotal) {
        this.cpuTotal = cpuTotal;
    }

    public String getCpuUseCount() {
        return cpuUseCount;
    }

    public void setCpuUseCount(String cpuUseCount) {
        this.cpuUseCount = cpuUseCount;
    }

    public Integer getSpare1() {
        return spare1;
    }

    public void setSpare1(Integer spare1) {
        this.spare1 = spare1;
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2;
    }

    public String getSpare3() {
        return spare3;
    }

    public void setSpare3(String spare3) {
        this.spare3 = spare3;
    }

    public Integer getSpare4() {
        return spare4;
    }

    public void setSpare4(Integer spare4) {
        this.spare4 = spare4;
    }

    public String getSpare5() {
        return spare5;
    }

    public void setSpare5(String spare5) {
        this.spare5 = spare5;
    }
}