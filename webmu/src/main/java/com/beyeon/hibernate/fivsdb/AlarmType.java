package com.beyeon.hibernate.fivsdb;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "alarm_type")
public class AlarmType {

	public static String getTypeName(String id){
		return DictBpo.getDictName(TDict.ALARM_TYPE, id);
	}

	public static Collection<AutoCompleteDto> getTypes(){
		return DictBpo.find(TDict.ALARM_TYPE);
	}
	
	public static Collection<AutoCompleteDto> getLevels(){
		return DictBpo.find(TDict.ALARM_LEVEL);
	}

	public static String getLevelName(String id){return DictBpo.getDictName(TDict.ALARM_LEVEL, id);	}
	
	private Integer type;
	private String name;

	@Id
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AlarmType alarmType = (AlarmType) o;

		if (type != null ? !type.equals(alarmType.type) : alarmType.type != null) return false;
		if (name != null ? !name.equals(alarmType.name) : alarmType.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = type != null ? type.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
