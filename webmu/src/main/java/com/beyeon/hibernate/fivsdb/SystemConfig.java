package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "system_config")
@GenericGenerator(name="genID", strategy="increment")
public class SystemConfig {
	private Integer id;
	private Integer maxErrorCount;
	private Integer errorLoginConfig;
	private Integer userLockTime;
	private Integer pwdResetFlag;
	private Integer keepPasswordTime;
	private Integer pwdRemaindTime;
	private String exceptionUser;
	private static SystemConfig config  = new SystemConfig();
	
	public static SystemConfig getConfig(){
		return config;
	}
	
	public static void setConfig(SystemConfig config){
		SystemConfig.config = config;
	} 
	@Id
	@GeneratedValue(generator="genID")
	@Column(name = "id")
	public Integer  getId() {
		return id;
	}

	public void setId(Integer  id) {
		this.id = id;
	}
	@Basic
	@Column(name = "max_error_count")
	public Integer getMaxErrorCount() {
		return maxErrorCount;
	}

	public void setMaxErrorCount(Integer maxErrorCount) {
		this.maxErrorCount = maxErrorCount;
	}
	@Basic
	@Column(name = "error_login_config")
	public Integer getErrorLoginConfig() {
		return errorLoginConfig;
	}

	public void setErrorLoginConfig(Integer errorLoginConfig) {
		this.errorLoginConfig = errorLoginConfig;
	}
	@Basic
	@Column(name = "user_lock_time")
	public Integer getUserLockTime() {
		return userLockTime;
	}

	public void setUserLockTime(Integer userLockTime) {
		this.userLockTime = userLockTime;
	}
	@Basic
	@Column(name = "pwd_reset_flag")
	public Integer getPwdResetFlag() {
		return pwdResetFlag;
	}

	public void setPwdResetFlag(Integer pwdResetFlag) {
		this.pwdResetFlag = pwdResetFlag;
	}
	@Basic
	@Column(name = "keep_password_time")
	public Integer getKeepPasswordTime() {
		return keepPasswordTime;
	}

	public void setKeepPasswordTime(Integer keepPasswordTime) {
		this.keepPasswordTime = keepPasswordTime;
	}
	@Basic
	@Column(name = "pwd_remaind_time")
	public Integer getPwdRemaindTime() {
		return pwdRemaindTime;
	}

	public void setPwdRemaindTime(Integer pwdRemaindTime) {
		this.pwdRemaindTime = pwdRemaindTime;
	}
	@Basic
	@Column(name = "exception_user")
	public String getExceptionUser() {
		return exceptionUser;
	}

	public void setExceptionUser(String exceptionUser) {
		this.exceptionUser = exceptionUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorLoginConfig == null) ? 0 : errorLoginConfig.hashCode());
		result = prime * result + ((exceptionUser == null) ? 0 : exceptionUser.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((keepPasswordTime == null) ? 0 : keepPasswordTime.hashCode());
		result = prime * result + ((maxErrorCount == null) ? 0 : maxErrorCount.hashCode());
		result = prime * result + ((pwdRemaindTime == null) ? 0 : pwdRemaindTime.hashCode());
		result = prime * result + ((pwdResetFlag == null) ? 0 : pwdResetFlag.hashCode());
		result = prime * result + ((userLockTime == null) ? 0 : userLockTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemConfig other = (SystemConfig) obj;
		if (errorLoginConfig == null) {
			if (other.errorLoginConfig != null)
				return false;
		} else if (!errorLoginConfig.equals(other.errorLoginConfig))
			return false;
		if (exceptionUser == null) {
			if (other.exceptionUser != null)
				return false;
		} else if (!exceptionUser.equals(other.exceptionUser))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (keepPasswordTime == null) {
			if (other.keepPasswordTime != null)
				return false;
		} else if (!keepPasswordTime.equals(other.keepPasswordTime))
			return false;
		if (maxErrorCount == null) {
			if (other.maxErrorCount != null)
				return false;
		} else if (!maxErrorCount.equals(other.maxErrorCount))
			return false;
		if (pwdRemaindTime == null) {
			if (other.pwdRemaindTime != null)
				return false;
		} else if (!pwdRemaindTime.equals(other.pwdRemaindTime))
			return false;
		if (pwdResetFlag == null) {
			if (other.pwdResetFlag != null)
				return false;
		} else if (!pwdResetFlag.equals(other.pwdResetFlag))
			return false;
		if (userLockTime == null) {
			if (other.userLockTime != null)
				return false;
		} else if (!userLockTime.equals(other.userLockTime))
			return false;
		return true;
	}

	
}
