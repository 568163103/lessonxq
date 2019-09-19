package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "group_resource")
@IdClass(GroupResourcePK.class)
public class GroupResource {
	private String groupId;
	private String resourceId;

	public GroupResource() {
	}

	public GroupResource(String groupId, String resourceId) {
		this.groupId = groupId;
		this.resourceId = resourceId;
	}

	@Id
	@Column(name = "group_id")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Id
	@Column(name = "resource_id")
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

		GroupResource that = (GroupResource) o;

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
