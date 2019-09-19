package com.beyeon.nvss.performance.model.dto;

import java.util.List;

import com.beyeon.nvss.common.model.dto.CmuResultDto;

/**
 * User: Administrator
 * Date: 2016/2/29
 * Time: 11:23
 */
public class ReqResUserDto extends CmuResultDto {
	String serverId;
	String resId;
	List<ReqResUser> itemList;

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public List<ReqResUser> getItemList() {
		return itemList;
	}

	public void setItemList(List<ReqResUser> itemList) {
		this.itemList = itemList;
	}

	public class ReqResUser {
		String userId = "";

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userId;
		}
	}
}
