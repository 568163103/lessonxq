package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
public class MssChannelPK implements Serializable {
	private String channelId;
	private String serverId;

	@Column(name = "channel_id")
	@Id
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Column(name = "server_id")
	@Id
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MssChannelPK that = (MssChannelPK) o;

		if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
		if (serverId != null ? !serverId.equals(that.serverId) : that.serverId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (serverId != null ? serverId.hashCode() : 0);
		return result;
	}
}
