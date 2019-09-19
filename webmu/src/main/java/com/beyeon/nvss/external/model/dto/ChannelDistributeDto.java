package com.beyeon.nvss.external.model.dto;

import com.beyeon.hibernate.fivsdb.ChannelDistribute;
import com.beyeon.hibernate.fivsdb.TbResShieldPlan;
import com.beyeon.hibernate.fivsdb.TbShieldRes;
import com.beyeon.hibernate.fivsdb.TbShieldUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChannelDistributeDto implements Serializable{
	
	private ChannelDistribute channelDistribute;


	public ChannelDistribute getChannelDistribute() {
		return channelDistribute;
	}

	public void setChannelDistribute(ChannelDistribute channelDistribute) {
		this.channelDistribute = channelDistribute;
	}
}
