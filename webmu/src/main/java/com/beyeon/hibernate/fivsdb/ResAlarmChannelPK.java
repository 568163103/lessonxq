package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
public class ResAlarmChannelPK implements Serializable {
	private String alarmChannelId;
	private String channelId;

	@Column(name = "alarm_channel_id")
	@Id
	public String getAlarmChannelId() {
		return alarmChannelId;
	}

	public void setAlarmChannelId(String alarmChannelId) {
		this.alarmChannelId = alarmChannelId;
	}

	@Column(name = "channel_id")
	@Id
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ResAlarmChannelPK that = (ResAlarmChannelPK) o;

		if (alarmChannelId != null ? !alarmChannelId.equals(that.alarmChannelId) : that.alarmChannelId != null)
			return false;
		if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = alarmChannelId != null ? alarmChannelId.hashCode() : 0;
		result = 31 * result + (channelId != null ? channelId.hashCode() : 0);
		return result;
	}
}
