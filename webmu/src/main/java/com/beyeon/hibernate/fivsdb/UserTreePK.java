package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
public class UserTreePK implements Serializable {
	private String userId;
	private String resId;
	private String parentId;

	@Column(name = "user_id")
	@Id
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "res_id")
	@Id
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	@Column(name = "parent_id")
	@Id
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserTreePK that = (UserTreePK) o;

		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (resId != null ? !resId.equals(that.resId) : that.resId != null) return false;
		if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userId != null ? userId.hashCode() : 0;
		result = 31 * result + (resId != null ? resId.hashCode() : 0);
		result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
		return result;
	}
}
