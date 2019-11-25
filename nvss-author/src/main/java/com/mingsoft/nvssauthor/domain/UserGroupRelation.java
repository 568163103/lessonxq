package com.mingsoft.nvssauthor.domain;

public class UserGroupRelation {
    private Integer userGroupId;

    private String groupId;

    private Integer groupRight;

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
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