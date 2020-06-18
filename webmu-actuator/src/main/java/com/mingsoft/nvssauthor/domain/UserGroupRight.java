package com.mingsoft.nvssauthor.domain;

public class UserGroupRight {
    /**
    * user ID, @see User.ID
    */
    private String userId;

    /**
    * group ID,@see Group.ID
    */
    private String groupId;

    /**
    * right
    */
    private Integer groupRight;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupRight() {
        return groupRight;
    }

    public void setGroupRight(Integer groupRight) {
        this.groupRight = groupRight;
    }
}