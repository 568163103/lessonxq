package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name = "tb_shield_user")
@IdClass(TbShieldUserPK.class)
public class TbShieldUser {
	
	private String userId;
	private Integer shieldId;

	public TbShieldUser(){
	}

	public TbShieldUser(String userId, Integer shieldId) {
		this.userId = userId;
		this.shieldId = shieldId;
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
	@Column(name = "shield_id")
	public Integer getShieldId() {
		return shieldId;
	}

	public void setShieldId(Integer shieldId) {
		this.shieldId = shieldId;
	}

}
