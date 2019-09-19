package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.beyeon.general.baseinfo.model.bpo.DictBpo;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "user_info")
@PrimaryKeyJoinColumn(name = "id")
public class UserInfo extends User {
    private String alias;
    private Integer ptzLevel;
    private Integer avLevel;
    private Integer userType = 1;
    private String activeBeginTime;
    private String activeEndTime;
    private String phone;
    private String mail;
    private String lastLoginTime;
    private String description = "";
    private Boolean status = false;

    private String tbDept;
    private String tbUType;
    private Integer userLevel;
    private Integer maxCameraNum;
    private Integer ptzLockTime;
    private String activeTime;
    private Integer prohibited_status;

    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "ptz_level")
    public Integer getPtzLevel() {
        return ptzLevel;
    }

    public void setPtzLevel(Integer ptzLevel) {
        this.ptzLevel = ptzLevel;
    }

    @Basic
    @Column(name = "av_level")
    public Integer getAvLevel() {
        return avLevel;
    }

    public void setAvLevel(Integer avLevel) {
        this.avLevel = avLevel;
    }

    @Basic
    @Column(name = "user_type")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "active_begin_time")
    public String getActiveBeginTime() {
        return activeBeginTime;
    }

    public void setActiveBeginTime(String activeBeginTime) {
        this.activeBeginTime = activeBeginTime;
    }

    @Basic
    @Column(name = "active_end_time")
    public String getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(String activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "last_login_time")
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "tb_dept")
    public String getTbDept() {
        return tbDept;
    }

    public void setTbDept(String tbDept) {
        this.tbDept = tbDept;
    }

    @Basic
    @Column(name = "user_level")
    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    @Basic
    @Column(name = "max_camera_num")
    public Integer getMaxCameraNum() {
        return maxCameraNum;
    }

    public void setMaxCameraNum(Integer maxCameraNum) {
        this.maxCameraNum = maxCameraNum;
    }

    @Basic
    @Column(name = "ptz_lock_time")
    public Integer getPtzLockTime() {
        return ptzLockTime;
    }

    public void setPtzLockTime(Integer ptzLockTime) {
        this.ptzLockTime = ptzLockTime;
    }

    @Basic
    @Column(name = "active_time")
    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }
    @Basic
    @Column(name = "prohibited_status")
    public Integer getProhibited_status() {
        return prohibited_status;
    }

    public void setProhibited_status(Integer prohibited_status) {
        this.prohibited_status = prohibited_status;
    }

    @Transient
    public String getTbUType() {
        return tbUType;
    }

    public void setTbUType(String tbUType) {
        this.tbUType = tbUType;
    }

    @Transient
    public String getStatusZh() {
        return status ? "在线" : "离线";
    }

//	@Transient
//	public String getPtzLevelZh() {
//		if (null != ptzLevel) {
//			return DictBpo.getDictName(TDict.USER_PTZ_LEVEL,ptzLevel.toString());
//		}
//		return "";
//	}

    @Transient
    public String getTbDeptZh() {
        if (null != tbDept) {
            return DictBpo.getDictName(TDict.TB_DEPARTMENT, tbDept.toString());
        }
        return "";
    }

    @Transient
    public String getAvLevelZh() {
        if (null != avLevel) {
            return DictBpo.getDictName(TDict.USER_AV_LEVEL, avLevel.toString());
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        if (this.getId() != null ? !this.getId().equals(userInfo.getId()) : userInfo.getId() != null) return false;
        if (alias != null ? !alias.equals(userInfo.alias) : userInfo.alias != null) return false;
        if (ptzLevel != null ? !ptzLevel.equals(userInfo.ptzLevel) : userInfo.ptzLevel != null) return false;
        if (avLevel != null ? !avLevel.equals(userInfo.avLevel) : userInfo.avLevel != null) return false;
        if (userType != null ? !userType.equals(userInfo.userType) : userInfo.userType != null) return false;
        if (activeBeginTime != null ? !activeBeginTime.equals(userInfo.activeBeginTime) : userInfo.activeBeginTime != null)
            return false;
        if (activeEndTime != null ? !activeEndTime.equals(userInfo.activeEndTime) : userInfo.activeEndTime != null)
            return false;
        if (phone != null ? !phone.equals(userInfo.phone) : userInfo.phone != null) return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(userInfo.lastLoginTime) : userInfo.lastLoginTime != null)
            return false;
        if (description != null ? !description.equals(userInfo.description) : userInfo.description != null)
            return false;
        if (status != null ? !status.equals(userInfo.status) : userInfo.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.getId() != null ? this.getId().hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (ptzLevel != null ? ptzLevel.hashCode() : 0);
        result = 31 * result + (avLevel != null ? avLevel.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (activeBeginTime != null ? activeBeginTime.hashCode() : 0);
        result = 31 * result + (activeEndTime != null ? activeEndTime.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
