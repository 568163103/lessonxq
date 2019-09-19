package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.general.baseinfo.model.bpo.DictBpo;

/**
 * User: Administrator
 * Date: 2017/3/30
 * Time: 11:41
 */
@Entity
@Table(name = "tb_alarmsubcri_type")
public class TbAlarmsubcriType implements Serializable {
	private static Map<String,String> objectMap = new HashMap<String,String>();

	public static String getObjectName(String id){
		return objectMap.get(id);
	}

	public static Map<String,String> getObjectMap(){
		return objectMap;
	}

	public static void setObjectMap(Map<String, String> currObjectMap){
		Map<String,String> oldObjectMap = objectMap;
		objectMap = currObjectMap;
		oldObjectMap.clear();
	}	
	
	private String alarmType;
	private String beginTime;
	private String endTime;
	private String description;

	@Id
	@Column(name = "alarm_type")
	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarm_type) {
		this.alarmType = alarm_type;
	}

	@Basic
	@Column(name = "begin_time")
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	@Basic
	@Column(name = "end_time")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	

	@Transient
	public String getAlarmTypeZh() {		
		if (null != alarmType) {			
			return getObjectName(alarmType);
		}
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alarmType == null) ? 0 : alarmType.hashCode());
		result = prime * result + ((beginTime == null) ? 0 : beginTime.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TbAlarmsubcriType other = (TbAlarmsubcriType) obj;
		if (alarmType == null) {
			if (other.alarmType != null)
				return false;
		} else if (!alarmType.equals(other.alarmType))
			return false;
		if (beginTime == null) {
			if (other.beginTime != null)
				return false;
		} else if (!beginTime.equals(other.beginTime))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		return true;
	}
	
	
}
