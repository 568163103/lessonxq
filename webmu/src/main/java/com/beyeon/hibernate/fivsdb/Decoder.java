package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
public class Decoder {
	private String id;
	private String name;
	private Byte enabled;
	private String model;
	private String ip;
	private Integer port;
	private String username;
	private String password;
	private Integer channelCount;
	private String address;
	private String description;
	private Byte status;

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	@Column(name = "enabled")
	public Byte getEnabled() {
		return enabled;
	}

	public void setEnabled(Byte enabled) {
		this.enabled = enabled;
	}

	@Basic
	@Column(name = "model")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Basic
	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Basic
	@Column(name = "port")
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Basic
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Basic
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "channel_count")
	public Integer getChannelCount() {
		return channelCount;
	}

	public void setChannelCount(Integer channelCount) {
		this.channelCount = channelCount;
	}

	@Basic
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "status")
	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Decoder decoder = (Decoder) o;

		if (id != null ? !id.equals(decoder.id) : decoder.id != null) return false;
		if (name != null ? !name.equals(decoder.name) : decoder.name != null) return false;
		if (enabled != null ? !enabled.equals(decoder.enabled) : decoder.enabled != null) return false;
		if (model != null ? !model.equals(decoder.model) : decoder.model != null) return false;
		if (ip != null ? !ip.equals(decoder.ip) : decoder.ip != null) return false;
		if (port != null ? !port.equals(decoder.port) : decoder.port != null) return false;
		if (username != null ? !username.equals(decoder.username) : decoder.username != null) return false;
		if (password != null ? !password.equals(decoder.password) : decoder.password != null) return false;
		if (channelCount != null ? !channelCount.equals(decoder.channelCount) : decoder.channelCount != null)
			return false;
		if (address != null ? !address.equals(decoder.address) : decoder.address != null) return false;
		if (description != null ? !description.equals(decoder.description) : decoder.description != null) return false;
		if (status != null ? !status.equals(decoder.status) : decoder.status != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
		result = 31 * result + (model != null ? model.hashCode() : 0);
		result = 31 * result + (ip != null ? ip.hashCode() : 0);
		result = 31 * result + (port != null ? port.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (channelCount != null ? channelCount.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		return result;
	}
}
