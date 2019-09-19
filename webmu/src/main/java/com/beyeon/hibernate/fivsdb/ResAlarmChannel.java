package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "res_alarm_channel")
@IdClass(ResAlarmChannelPK.class)
public class ResAlarmChannel {
	private String alarmChannelId;
	private String channelId;

	@Id
	@Column(name = "alarm_channel_id")
	public String getAlarmChannelId() {
		return alarmChannelId;
	}

	public void setAlarmChannelId(String alarmChannelId) {
		this.alarmChannelId = alarmChannelId;
	}

	@Id
	@Column(name = "channel_id")
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

		ResAlarmChannel that = (ResAlarmChannel) o;

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
