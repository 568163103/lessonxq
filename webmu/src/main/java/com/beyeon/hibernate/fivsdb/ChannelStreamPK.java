package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
public class ChannelStreamPK implements Serializable {
	private String channelId;
	private Integer num;

	@Column(name = "channel_id")
	@Id
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
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

		ChannelStreamPK that = (ChannelStreamPK) o;

		if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
		if (num != null ? !num.equals(that.num) : that.num != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (num != null ? num.hashCode() : 0);
		return result;
	}
}
