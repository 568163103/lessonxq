package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2018/04/12
 * Time: 17:41
 */
@Entity
@Table(name = "encoder_extra")
public class EncoderExtra {
	private String id;
	private String corp;
	private String type;
	private String version;
	private String mac;

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Basic
	@Column(name = "corp")
	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}
	
	@Basic
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Basic
	@Column(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Basic
	@Column(name = "mac")
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EncoderExtra that = (EncoderExtra) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (corp != null ? !corp.equals(that.corp) : that.corp != null) return false;
		if (version != null ? !version.equals(that.version) : that.version != null) return false;
		if (mac != null ? !mac.equals(that.mac) : that.mac != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (corp != null ? corp.hashCode() : 0);
		result = 31 * result + (version != null ? version.hashCode() : 0);
		result = 31 * result + (mac != null ? mac.hashCode() : 0);
		return result;
	}
}
