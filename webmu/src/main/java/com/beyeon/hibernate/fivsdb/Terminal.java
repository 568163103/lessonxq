package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "terminal")
public class Terminal extends BaseEntity {
	private String id;
	private String name;
	private String ip;
	private String address;
	private String unlockKey;
	private String description;
	
	private String position;	
	
	@Transient
	public String getPosition() {
		if (StringUtils.isBlank(position) && StringUtils.isNotBlank(id)){
			String pos = id.substring(0, 6);
			setPosition(pos);			
		}
		return position;
	}
	@Transient
	public String getPositionZH(){
		String pos = getPosition();
		return PositionCode.getTypeName(pos);
	}
	public void setPosition(String position) {
		this.position = position;
	}
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
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Basic
	@Column(name = "unlock_key")
	public String getUnlockKey() {
		return unlockKey;
	}

	public void setUnlockKey(String unlockKey) {
		this.unlockKey = unlockKey;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Terminal terminal = (Terminal) o;

		if (id != null ? !id.equals(terminal.id) : terminal.id != null) return false;
		if (name != null ? !name.equals(terminal.name) : terminal.name != null) return false;
		if (enabled != null ? !enabled.equals(terminal.enabled) : terminal.enabled != null) return false;
		if (ip != null ? !ip.equals(terminal.ip) : terminal.ip != null) return false;
		if (address != null ? !address.equals(terminal.address) : terminal.address != null) return false;
		if (unlockKey != null ? !unlockKey.equals(terminal.unlockKey) : terminal.unlockKey != null) return false;
		if (description != null ? !description.equals(terminal.description) : terminal.description != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
		result = 31 * result + (ip != null ? ip.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (unlockKey != null ? unlockKey.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
