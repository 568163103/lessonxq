package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "channel_record_plan")
public class ChannelRecordPlan {
	private String channelId;
	private String planName;

	@Id
	@Column(name = "channel_id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Column(name = "plan_name")
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

		ChannelRecordPlan that = (ChannelRecordPlan) o;

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
