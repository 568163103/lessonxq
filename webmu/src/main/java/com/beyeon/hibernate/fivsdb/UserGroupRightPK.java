package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
public class UserGroupRightPK implements Serializable {
	private String userId;
	private String groupId;

	@Column(name = "user_id")
	@Id
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "group_id")
	@Id
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserGroupRightPK that = (UserGroupRightPK) o;

		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userId != null ? userId.hashCode() : 0;
		result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
		return result;
	}
}
