package com.beyeon.hibernate.fivsdb;

import javax.persistence.*;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "server_encoder")
@IdClass(ServerEncoderPK.class)
public class ServerEncoder {
	private String serverId;
	private String encoderId;

	@Id
	@Column(name = "server_id")
	public String getServerId() {
		return serverId;
	}

	@Transient
	public String getServerName() {
		return Server.getObjectName(ServerType.MDU.toString(), serverId);
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Id
	@Column(name = "encoder_id")
	public String getEncoderId() {
		return encoderId;
	}

	public void setEncoderId(String encoderId) {
		this.encoderId = encoderId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ServerEncoder that = (ServerEncoder) o;

		if (serverId != null ? !serverId.equals(that.serverId) : that.serverId != null) return false;
		if (encoderId != null ? !encoderId.equals(that.encoderId) : that.encoderId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = serverId != null ? serverId.hashCode() : 0;
		result = 31 * result + (encoderId != null ? encoderId.hashCode() : 0);
		return result;
	}
}
