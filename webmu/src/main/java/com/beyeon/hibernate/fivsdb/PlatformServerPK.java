package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
public class PlatformServerPK implements Serializable {
	private String platformId;
	private String serverId;

	@Column(name = "platform_id")
	@Id
	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
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

		PlatformServerPK that = (PlatformServerPK) o;

		if (platformId != null ? !platformId.equals(that.platformId) : that.platformId != null) return false;
		if (serverId != null ? !serverId.equals(that.serverId) : that.serverId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = platformId != null ? platformId.hashCode() : 0;
		result = 31 * result + (serverId != null ? serverId.hashCode() : 0);
		return result;
	}
}
