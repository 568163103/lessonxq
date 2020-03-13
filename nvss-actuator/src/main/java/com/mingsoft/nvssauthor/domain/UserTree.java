package com.mingsoft.nvssauthor.domain;

public class UserTree {
    /**
    * user ID
    */
    private String userId;

    /**
    * resource ID
    */
    private String resId;

    /**
    * parent ID,empty if it is root
    */
    private String parentId;

    /**
    * name
    */
    private String name;

    /**
    * Previous ID,empty if it is first
    */
    private String previousId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreviousId() {
        return previousId;
    }

    public void setPreviousId(String previousId) {
        this.previousId = previousId;
    }
}