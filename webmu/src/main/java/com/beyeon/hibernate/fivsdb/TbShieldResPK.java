package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;


public class TbShieldResPK implements Serializable {
	private String resId;
	private Integer shieldId;

	@Column(name = "res_id")
	@Id
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
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
