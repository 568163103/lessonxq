package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "alarm_device_channel")
@IdClass(AlarmDeviceChannelPK.class)
public class AlarmDeviceChannel {
	private String channelId;
	private String name;
	private String deviceId;
	private Integer num;
	private String description;

	@Basic
	@Column(name = "channel_id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Id
	@Column(name = "device_id")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Id
	@Column(name = "num")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AlarmDeviceChannel that = (AlarmDeviceChannel) o;

		if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
		if (num != null ? !num.equals(that.num) : that.num != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
		result = 31 * result + (num != null ? num.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
