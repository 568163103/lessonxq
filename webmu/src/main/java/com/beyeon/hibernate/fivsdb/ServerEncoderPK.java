package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
public class ServerEncoderPK implements Serializable {
	private String serverId;
	private String encoderId;

	@Column(name = "server_id")
	@Id
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Column(name = "encoder_id")
	@Id
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

		ServerEncoderPK that = (ServerEncoderPK) o;

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
