package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "user_group_right")
@IdClass(UserGroupRightPK.class)
public class UserGroupRight {
	public static int USER_RIGHT_PLAY       = 1;
	public static int USER_RIGHT_PTZ        = 2;
	public static int USER_RIGHT_PLAYBACK   = 4;
	public static int USER_RIGHT_DEALALARM  = 8;
	public static int USER_RIGHT_DOWNLOAD   = 16;
	private String userId;
	private String groupId;
	private Integer groupRight;

	public UserGroupRight(){
	}

	public UserGroupRight(String userId,String groupId, Integer groupRight) {
		this.groupId = groupId;
		this.userId = userId;
		this.groupRight = groupRight;
	}

	@Id
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Id
	@Column(name = "group_id")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Basic
	@Column(name = "group_right")
	public Integer getGroupRight() {
		return groupRight;
	}

	public void setGroupRight(Integer groupRight) {
		this.groupRight = groupRight;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserGroupRight that = (UserGroupRight) o;

		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
		if (groupRight != null ? !groupRight.equals(that.groupRight) : that.groupRight != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userId != null ? userId.hashCode() : 0;
		result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
		result = 31 * result + (groupRight != null ? groupRight.hashCode() : 0);
		return result;
	}
}
