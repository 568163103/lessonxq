package com.xq.domain;

public class Rights {
    private Long rightid;

    private Long groupid;

    private Integer permission;

    private Long id;

    public Long getRightid() {
        return rightid;
    }

    public void setRightid(Long rightid) {
        this.rightid = rightid;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}