package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.beyeon.general.baseinfo.model.bpo.DictBpo;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "user_group_relation")
@IdClass(UserGroupRelationPK.class)
public class UserGroupRelation {
	public static int USER_RIGHT_PLAY       = 1;
	public static int USER_RIGHT_PTZ        = 2;
	public static int USER_RIGHT_PLAYBACK   = 4;
	public static int USER_RIGHT_DEALALARM  = 8;
	public static int USER_RIGHT_DOWNLOAD   = 16;
	private Integer userGroupId;
	private String groupId;
	private Integer groupRight;
	
	public UserGroupRelation(){
	}

	public UserGroupRelation(Integer userGroupId,String groupId, Integer groupRight) {
		this.groupId = groupId;
		this.userGroupId = userGroupId;
		this.groupRight = groupRight;
	}
	@Id
	@Column(name = "user_group_id")
	public Integer getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((userGroupId == null) ? 0 : userGroupId.hashCode());
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
		UserGroupRelation other = (UserGroupRelation) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (userGroupId == null) {
			if (other.userGroupId != null)
				return false;
		} else if (!userGroupId.equals(other.userGroupId))
			return false;
		return true;
	}



	
}
