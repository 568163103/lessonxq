package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name = "tb_shield_res")
@IdClass(TbShieldResPK.class)
public class TbShieldRes {
	
	private String resId;
	private Integer shieldId;

	public TbShieldRes(){
	}

	public TbShieldRes(String resId, Integer shieldId) {
		this.resId = resId;
		this.shieldId = shieldId;
	}

	@Id
	@Column(name = "res_id")
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
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
