package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;


public class TbShieldUserPK implements Serializable {
	private String userId;
	private Integer shieldId;

	@Column(name = "user_id")
	@Id
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "shield_id")
	@Id
	public Integer getShieldId() {
		return shieldId;
	}

	public void setShieldId(Integer shieldId) {
		this.shieldId = shieldId;
	}

}
