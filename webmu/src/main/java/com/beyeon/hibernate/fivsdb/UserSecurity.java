package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "user_security")
public class UserSecurity extends BaseEntity {
	private String userId;
	private Integer errorCount;
	private String lastErrorLoginTime;
	private String description;
	
	@Id
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Basic
	@Column(name = "error_count")
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	@Basic
	@Column(name = "last_error_login_time")
	public String getLastErrorLoginTime() {
		return lastErrorLoginTime;
	}
	public void setLastErrorLoginTime(String lastErrorLoginTime) {
		this.lastErrorLoginTime = lastErrorLoginTime;
	}
	
	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
