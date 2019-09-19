package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "res_alarm_type")
public class ResAlarmType {
	private String id;
	private String sourceId;
	private Integer alarmType;
	private String description;

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Basic
	@Column(name = "source_id")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@Basic
	@Column(name = "alarm_type")
	public Integer getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
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

		ResAlarmType that = (ResAlarmType) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (sourceId != null ? !sourceId.equals(that.sourceId) : that.sourceId != null) return false;
		if (alarmType != null ? !alarmType.equals(that.alarmType) : that.alarmType != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
		result = 31 * result + (alarmType != null ? alarmType.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
