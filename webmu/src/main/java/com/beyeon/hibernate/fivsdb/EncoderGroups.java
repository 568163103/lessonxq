package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "encoder_groups")
public class EncoderGroups {
	private String encoderId;
	private String groupId;

	public EncoderGroups() {
	}

	public EncoderGroups(String encoderId, String groupId) {
		this.encoderId = encoderId;
		this.groupId = groupId;
	}

	@Id
	@Column(name = "encoder_id")
	public String getEncoderId() {
		return encoderId;
	}

	public void setEncoderId(String encoderId) {
		this.encoderId = encoderId;
	}

	@Column(name = "group_id")
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

		EncoderGroups that = (EncoderGroups) o;

		if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
		if (encoderId != null ? !encoderId.equals(that.encoderId) : that.encoderId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = groupId != null ? groupId.hashCode() : 0;
		result = 31 * result + (encoderId != null ? encoderId.hashCode() : 0);
		return result;
	}
}
