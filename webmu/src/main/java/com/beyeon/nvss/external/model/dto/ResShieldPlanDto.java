package com.beyeon.nvss.external.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.beyeon.hibernate.fivsdb.TbResShieldPlan;
import com.beyeon.hibernate.fivsdb.TbShieldRes;
import com.beyeon.hibernate.fivsdb.TbShieldUser;

public class ResShieldPlanDto implements Serializable{
	
	private TbResShieldPlan tbResShieldPlan;
	
	private List<TbShieldUser>  shielduUsers= new ArrayList<TbShieldUser>();
	
	private List<TbShieldRes>  shielduRess= new ArrayList<TbShieldRes>();

	public TbResShieldPlan getTbResShieldPlan() {
		return tbResShieldPlan;
	}

	public void setTbResShieldPlan(TbResShieldPlan tbResShieldPlan) {
		this.tbResShieldPlan = tbResShieldPlan;
	}

	public List<TbShieldUser> getShielduUsers() {
		return shielduUsers;
	}

	public void setShielduUsers(List<TbShieldUser> shielduUsers) {
		this.shielduUsers = shielduUsers;
	}

	public List<TbShieldRes> getShielduRess() {
		return shielduRess;
	}

	public void setShielduRess(List<TbShieldRes> shielduRess) {
		this.shielduRess = shielduRess;
	}
	

}
