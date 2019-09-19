package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "mss_channel")
@IdClass(MssChannelPK.class)
public class MssChannel {
	private String channelId;
	private String serverId;

	public MssChannel() {
	}

	public MssChannel(String channelId, String serverId) {
		this.channelId = channelId;
		this.serverId = serverId;
	}

	@Id
	@Column(name = "channel_id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Id
	@Column(name = "server_id")
	public String getServerId() {
		return serverId;
	}

	@Transient
	public String getServerName() {
		return Server.getObjectName(ServerType.MSU.toString(), serverId);
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MssChannel that = (MssChannel) o;

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
