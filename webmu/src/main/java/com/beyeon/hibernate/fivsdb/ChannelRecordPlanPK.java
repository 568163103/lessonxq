package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
public class ChannelRecordPlanPK implements Serializable {
	private String channelId;
	private String planName;

	@Column(name = "channel_id")
	@Id
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Column(name = "plan_name")
	@Id
	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ChannelRecordPlanPK that = (ChannelRecordPlanPK) o;

		if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
		if (planName != null ? !planName.equals(that.planName) : that.planName != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (planName != null ? planName.hashCode() : 0);
		return result;
	}
}
