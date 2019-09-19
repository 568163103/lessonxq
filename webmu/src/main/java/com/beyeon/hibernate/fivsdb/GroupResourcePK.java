package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
public class GroupResourcePK implements Serializable {
	private String groupId;
	private String resourceId;

	@Column(name = "group_id")
	@Id
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "resource_id")
	@Id
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GroupResourcePK that = (GroupResourcePK) o;

		if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
		if (resourceId != null ? !resourceId.equals(that.resourceId) : that.resourceId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = groupId != null ? groupId.hashCode() : 0;
		result = 31 * result + (resourceId != null ? resourceId.hashCode() : 0);
		return result;
	}
}
