package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "ims_gis_info")
public class ImsGisInfo {
	private String channelId = "";
	private String recvtime = "";
	private Boolean status = false;
	private Double longitude = 0.0;
	private Double latitude = 0.0;
	private Double direction = 0.0;
	private Double dpitch = 0.0;
	private Double angle = 0.0;
	private Double curRange = 0.0;
	private Double speed = 0.0;
	private Double height = 0.0;

	@Id
	@Column(name = "channel_id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Transient
	public String getChannelName(){
		return Channel.getObjectName(channelId);
	}

	@Basic
	@Column(name = "recvtime")
	public String getRecvtime() {
		return recvtime;
	}

	public void setRecvtime(String recvtime) {
		this.recvtime = recvtime;
	}

	@Basic
	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Basic
	@Column(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Basic
	@Column(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Basic
	@Column(name = "direction")
	public Double getDirection() {
		return direction;
	}

	public void setDirection(Double direction) {
		this.direction = direction;
	}

	@Basic
	@Column(name = "dpitch")
	public Double getDpitch() {
		return dpitch;
	}

	public void setDpitch(Double dpitch) {
		this.dpitch = dpitch;
	}

	@Basic
	@Column(name = "angle")
	public Double getAngle() {
		return angle;
	}

	public void setAngle(Double angle) {
		this.angle = angle;
	}

	@Basic
	@Column(name = "cur_range")
	public Double getCurRange() {
		return curRange;
	}

	public void setCurRange(Double curRange) {
		this.curRange = curRange;
	}

	@Basic
	@Column(name = "speed")
	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	@Basic
	@Column(name = "height")
	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ImsGisInfo that = (ImsGisInfo) o;

		if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
		if (recvtime != null ? !recvtime.equals(that.recvtime) : that.recvtime != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;
		if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
		if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
		if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
		if (dpitch != null ? !dpitch.equals(that.dpitch) : that.dpitch != null) return false;
		if (angle != null ? !angle.equals(that.angle) : that.angle != null) return false;
		if (curRange != null ? !curRange.equals(that.curRange) : that.curRange != null) return false;
		if (speed != null ? !speed.equals(that.speed) : that.speed != null) return false;
		if (height != null ? !height.equals(that.height) : that.height != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (recvtime != null ? recvtime.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
		result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
		result = 31 * result + (direction != null ? direction.hashCode() : 0);
		result = 31 * result + (dpitch != null ? dpitch.hashCode() : 0);
		result = 31 * result + (angle != null ? angle.hashCode() : 0);
		result = 31 * result + (curRange != null ? curRange.hashCode() : 0);
		result = 31 * result + (speed != null ? speed.hashCode() : 0);
		result = 31 * result + (height != null ? height.hashCode() : 0);
		return result;
	}
}
