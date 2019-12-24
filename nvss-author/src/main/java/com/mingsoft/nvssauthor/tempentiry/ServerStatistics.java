package com.mingsoft.nvssauthor.tempentiry;

public class ServerStatistics {

    private int mssCount = 0;
    private int dmsCount = 0;
    private int cmsCount = 0;

    private int mssOnline = 0;
    private int dmsOnline = 0;
    private int cmsOnline = 0;

    private int mssOffOnline = 0;
    private int dmsOffOnline = 0;
    private int cmsOffOnline = 0;

    public int getMssOffOnline() {
        this.mssOffOnline = mssCount - mssOnline;
        return mssOffOnline;
    }



    public ServerStatistics(int mssCount, int mssOnline) {
        this.mssCount = mssCount;
        this.mssOnline = mssOnline;
    }

    public ServerStatistics(int mssCount, int dmsCount, int mssOnline, int dmsOnline) {
        this.mssCount = mssCount;
        this.dmsCount = dmsCount;
        this.mssOnline = mssOnline;
        this.dmsOnline = dmsOnline;
    }

    public ServerStatistics(int mssCount, int dmsCount, int cmsCount, int mssOnline, int dmsOnline, int cmsOnline) {
        this.mssCount = mssCount;
        this.dmsCount = dmsCount;
        this.cmsCount = cmsCount;
        this.mssOnline = mssOnline;
        this.dmsOnline = dmsOnline;
        this.cmsOnline = cmsOnline;
    }

    public void setMssOffOnline(int mssOffOnline) {

        this.mssOffOnline = mssOffOnline;
        getMssOffOnline();
    }

    public int getDmsOffOnline() {
        this.dmsOffOnline = dmsCount - dmsOnline;
        return dmsOffOnline;
    }

    public void setDmsOffOnline(int dmsOffOnline) {
        this.dmsOffOnline = dmsOffOnline;
        getDmsOffOnline();
    }

    public int getCmsOffOnline() {
        this.cmsOffOnline = cmsCount - cmsOnline;
        return cmsOffOnline;
    }

    public void setCmsOffOnline(int cmsOffOnline) {
        this.cmsOffOnline = cmsOffOnline;
        getCmsOffOnline();
    }


    public int getMssCount() {
        return mssCount;
    }

    public void setMssCount(int mssCount) {
        this.mssCount = mssCount;
    }

    public int getDmsCount() {
        return dmsCount;
    }

    public void setDmsCount(int dmsCount) {
        this.dmsCount = dmsCount;
    }

    public int getCmsCount() {
        return cmsCount;
    }

    public void setCmsCount(int cmsCount) {
        this.cmsCount = cmsCount;
    }

    public int getMssOnline() {
        return mssOnline;
    }

    public void setMssOnline(int mssOnline) {
        this.mssOnline = mssOnline;
    }

    public int getDmsOnline() {
        return dmsOnline;
    }

    public void setDmsOnline(int dmsOnline) {
        this.dmsOnline = dmsOnline;
    }

    public int getCmsOnline() {
        return cmsOnline;
    }

    public void setCmsOnline(int cmsOnline) {
        this.cmsOnline = cmsOnline;
    }
}
