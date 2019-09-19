package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "db_version")
public class DbVersion {
	private String version;
	private String description;

	@Id
	@Column(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

		DbVersion dbVersion = (DbVersion) o;

		if (version != null ? !version.equals(dbVersion.version) : dbVersion.version != null) return false;
		if (description != null ? !description.equals(dbVersion.description) : dbVersion.description != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = version != null ? version.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
