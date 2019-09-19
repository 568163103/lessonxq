package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
public class AlarmDeviceChannelPK implements Serializable {
	private String deviceId;
	private Integer num;

	@Column(name = "device_id")
	@Id
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "num")
	@Id
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AlarmDeviceChannelPK that = (AlarmDeviceChannelPK) o;

		if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
		if (num != null ? !num.equals(that.num) : that.num != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = deviceId != null ? deviceId.hashCode() : 0;
		result = 31 * result + (num != null ? num.hashCode() : 0);
		return result;
	}
}
