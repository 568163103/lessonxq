package com.mingsoft.nvssauthor.tempentiry;

public class ChannelStatistics {

    private int fixedCameraCount = 0;
    private int ptzCameraCount = 0;
    private int fixedIpCameraCount = 0;
    private int ptzIpCameraCount = 0;

    private int fixedCameraOnline = 0;
    private int ptzCameraOnline = 0;
    private int fixedIpCameraOnline = 0;
    private int ptzIpOnline = 0;

    private int fixedCameraOffOnline = 0;
    private int ptzCameraOffOnline = 0;
    private int fixedIpCameraOffOnline = 0;
    private int ptzIpOffOnline = 0;

    public ChannelStatistics(int fixedCameraCount, int ptzCameraCount, int fixedIpCameraCount, int ptzIpCameraCount) {
        this.fixedCameraCount = fixedCameraCount;
        this.ptzCameraCount = ptzCameraCount;
        this.fixedIpCameraCount = fixedIpCameraCount;
        this.ptzIpCameraCount = ptzIpCameraCount;
    }

    public int getFixedCameraCount() {
        return fixedCameraCount;
    }

    public void setFixedCameraCount(int fixedCameraCount) {
        this.fixedCameraCount = fixedCameraCount;
    }

    public int getPtzCameraCount() {
        return ptzCameraCount;
    }

    public void setPtzCameraCount(int ptzCameraCount) {
        this.ptzCameraCount = ptzCameraCount;
    }

    public int getFixedIpCameraCount() {
        return fixedIpCameraCount;
    }

    public void setFixedIpCameraCount(int fixedIpCameraCount) {
        this.fixedIpCameraCount = fixedIpCameraCount;
    }

    public int getPtzIpCameraCount() {
        return ptzIpCameraCount;
    }

    public void setPtzIpCameraCount(int ptzIpCameraCount) {
        this.ptzIpCameraCount = ptzIpCameraCount;
    }

    public int getFixedCameraOnline() {
        return fixedCameraOnline;
    }

    public void setFixedCameraOnline(int fixedCameraOnline) {
        this.fixedCameraOnline = fixedCameraOnline;
        getFixedCameraOffOnline();
    }

    public int getPtzCameraOnline() {
        return ptzCameraOnline;
    }

    public void setPtzCameraOnline(int ptzCameraOnline) {
        this.ptzCameraOnline = ptzCameraOnline;
        getPtzCameraOffOnline();
    }

    public int getFixedIpCameraOnline() {
        return fixedIpCameraOnline;
    }

    public void setFixedIpCameraOnline(int fixedIpCameraOnline) {
        this.fixedIpCameraOnline = fixedIpCameraOnline;
        getFixedIpCameraOffOnline();
    }

    public int getPtzIpOnline() {
        return ptzIpOnline;
    }

    public void setPtzIpOnline(int ptzIpOnline) {
        this.ptzIpOnline = ptzIpOnline;
        getPtzIpOffOnline();
    }

    public int getFixedCameraOffOnline() {
        this.fixedCameraOffOnline = this.fixedCameraCount - fixedCameraOnline;
        return fixedCameraOffOnline;
    }

    public void setFixedCameraOffOnline(int fixedCameraOffOnline) {
        this.fixedCameraOffOnline = fixedCameraOffOnline;
    }

    public int getPtzCameraOffOnline() {
        this.ptzCameraOffOnline = this.ptzCameraCount - this.ptzCameraOnline;
        return ptzCameraOffOnline;
    }

    public void setPtzCameraOffOnline(int ptzCameraOffOnline) {
        this.ptzCameraOffOnline = ptzCameraOffOnline;
    }

    public int getFixedIpCameraOffOnline() {
        this.fixedIpCameraOffOnline = this.fixedIpCameraCount - this.fixedIpCameraCount;
        return fixedIpCameraOffOnline;
    }

    public void setFixedIpCameraOffOnline(int fixedIpCameraOffOnline) {
        this.fixedIpCameraOffOnline = fixedIpCameraOffOnline;
    }

    public int getPtzIpOffOnline() {
        this.ptzIpOffOnline = this.ptzIpCameraCount - this.ptzIpOnline;
        return ptzIpOffOnline;
    }

    public void setPtzIpOffOnline(int ptzIpOffOnline) {
        this.ptzIpOffOnline = ptzIpOffOnline;
    }
}
