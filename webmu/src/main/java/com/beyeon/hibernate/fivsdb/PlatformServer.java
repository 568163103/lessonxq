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
@Table(name = "platform_server")
@IdClass(PlatformServerPK.class)
public class PlatformServer {
	private String platformId;
	private String serverId;

	@Id
	@Column(name = "platform_id")
	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	@Id
	@Column(name = "server_id")
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

		PlatformServer that = (PlatformServer) o;

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
