package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 * @author
 */
@Entity
@Table(name = "alarm_pre_scheme")
public class AlarmPreScheme implements Serializable	{
	private String sourceId;
	private Integer alarmType;
	private Integer schemeId;

	public AlarmPreScheme() {
	}

	public AlarmPreScheme(Integer alarmType, String sourceId, Integer schemeId) {
		this.sourceId = sourceId;
		this.alarmType = alarmType;
		this.schemeId = schemeId;
	}

	@Id
	@Column(name = "source_id")
	public String getSourceId() {
		return sourceId;
	}

	@Transient
	public String getSourceName() {
		if(alarmType==13){
			return Channel.getObjectName(sourceId);
		}
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

	@Transient
	public String getAlarmTypeName() {
		return AlarmType.getTypeName(alarmType.toString());
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}

	@Id
	@Column(name = "schema_id")
	public Integer getSchemeId() {
		return schemeId;
	}

	@Transient
	public String getSchemeName() {
		return PreScheme.getObjectName(schemeId.toString());
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AlarmPreScheme that = (AlarmPreScheme) o;

		if (sourceId != null ? !sourceId.equals(that.sourceId) : that.sourceId != null) return false;
		if (alarmType != null ? !alarmType.equals(that.alarmType) : that.alarmType != null) return false;
		if (schemeId != null ? !schemeId.equals(that.schemeId) : that.schemeId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = sourceId != null ? sourceId.hashCode() : 0;
		result = 31 * result + (alarmType != null ? alarmType.hashCode() : 0);
		result = 31 * result + (schemeId != null ? schemeId.hashCode() : 0);
		return result;
	}
}
