package com.beyeon.nvss.performance.model.dto;

import java.util.List;

import com.beyeon.hibernate.fivsdb.Channel;
import com.beyeon.nvss.common.model.dto.CmuResultDto;

/**
 * User: Administrator
 * Date: 2016/2/29
 * Time: 11:23
 */
public class ReqServerResDto extends CmuResultDto {
	String serverId;
	List<ReqServerRes> itemList;

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public List<ReqServerRes> getItemList() {
		return itemList;
	}

	public void setItemList(List<ReqServerRes> itemList) {
		this.itemList = itemList;
	}

	public class ReqServerRes {
		String id = "";
		short type = 0;
		String vodId;
		String beginTime;
		String endTime;
		boolean valid = false;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName(){
			return Channel.getObjectName(this.id);
		}

		public short getType() {
			return type;
		}

		public String getTypeName() {
			switch (type){
				case 0:
					return "实时视频预览";
				case 1:
					return "前端历史视频";
				default:
					return "中心历史视频";
			}
		}

		public void setType(short type) {
			this.type = type;
		}

		public String getVodId() {
			return vodId;
		}

		public void setVodId(String vodId) {
			this.vodId = vodId;
		}

		public String getBeginTime() {
			return beginTime;
		}

		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}
	}
}
