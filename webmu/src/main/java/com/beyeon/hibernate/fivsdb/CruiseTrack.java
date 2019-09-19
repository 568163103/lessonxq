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
@Table(name = "cruise_track")
@IdClass(CruiseTrackPK.class)
public class CruiseTrack {
	private String channelId;
	private Integer num;
	private String name;
	private Integer dwellTime;
	private Integer speed;
	private Integer flag;
	private String presetNum;
	private Integer status;

	@Id
	@Column(name = "channel_id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
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
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "dwell_time")
	public Integer getDwellTime() {
		return dwellTime;
	}

	public void setDwellTime(Integer dwellTime) {
		this.dwellTime = dwellTime;
	}

	@Basic
	@Column(name = "speed")
	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	@Basic
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Basic
	@Column(name = "preset_num")
	public String getPresetNum() {
		return presetNum;
	}

	public void setPresetNum(String presetNum) {
		this.presetNum = presetNum;
	}

	@Basic
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CruiseTrack that = (CruiseTrack) o;

		if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
		if (num != null ? !num.equals(that.num) : that.num != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (dwellTime != null ? !dwellTime.equals(that.dwellTime) : that.dwellTime != null) return false;
		if (speed != null ? !speed.equals(that.speed) : that.speed != null) return false;
		if (flag != null ? !flag.equals(that.flag) : that.flag != null) return false;
		if (presetNum != null ? !presetNum.equals(that.presetNum) : that.presetNum != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (num != null ? num.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (dwellTime != null ? dwellTime.hashCode() : 0);
		result = 31 * result + (speed != null ? speed.hashCode() : 0);
		result = 31 * result + (flag != null ? flag.hashCode() : 0);
		result = 31 * result + (presetNum != null ? presetNum.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		return result;
	}
}
