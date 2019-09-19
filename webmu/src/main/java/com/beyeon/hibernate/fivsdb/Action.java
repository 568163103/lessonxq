package com.beyeon.hibernate.fivsdb;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "action")
public class Action {
	private Integer id;
	private String name;
	private String type;
	private String target;
	private Integer duration;
	private Integer aheadOfTime;
	private String data;
	private static Map<String,String> param1NameMap = new HashMap<String, String>();
	private static Map<String,String> param2NameMap = new HashMap<String, String>();
	private static Map<String,String> param3NameMap = new HashMap<String, String>();
	static {
		param1NameMap.put("preset","预置位");
		param1NameMap.put("record","预录时间");
//		param1NameMap.put("popwindow","持续时间");
//		param2NameMap.put("record","持续时间");
//		param3NameMap.put("record","分辨率");
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Basic
	@Column(name = "target")
	public String getTarget() {
		return target;
	}

	@Transient
	public String getTargetName() {
		String targetName = Channel.getObjectName(target);
		if(StringUtils.isBlank(targetName)){
			targetName = User.getObjectName(target);
		}
		return targetName;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Basic
	@Column(name = "duration")
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Basic
	@Column(name = "ahead_of_time")
	public Integer getAheadOfTime() {
		return aheadOfTime;
	}

	public void setAheadOfTime(Integer aheadOfTime) {
		this.aheadOfTime = aheadOfTime;
	}

	@Basic
	@Column(name = "data")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Transient
	public String getParam1Name(){
		return param1NameMap.get(type);
	}

	@Transient
	public String getParam2Name(){
		return param2NameMap.get(type);
	}

	@Transient
	public String getParam3Name(){
		return param3NameMap.get(type);
	}

	@Transient
	public Object getParam1(){
		if("preset".equals(type)){
			return data;
		}
		if("record".equals(type)){
			return aheadOfTime;
		}
		if ("popwindow".equals(type)){
			return duration;
		}
		return "";
	}

	@Transient
	public Object getParam2(){
		if("record".equals(type)){
			return duration;
		}
		return "";
	}

	@Transient
	public Object getParam3(){
		if("record".equals(type)){
			return Resolution.getTypeName(data);
		}
		return "";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Action action = (Action) o;

		if (id != null ? !id.equals(action.id) : action.id != null) return false;
		if (name != null ? !name.equals(action.name) : action.name != null) return false;
		if (type != null ? !type.equals(action.type) : action.type != null) return false;
		if (target != null ? !target.equals(action.target) : action.target != null) return false;
		if (duration != null ? !duration.equals(action.duration) : action.duration != null) return false;
		if (aheadOfTime != null ? !aheadOfTime.equals(action.aheadOfTime) : action.aheadOfTime != null) return false;
		if (data != null ? !data.equals(action.data) : action.data != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (target != null ? target.hashCode() : 0);
		result = 31 * result + (duration != null ? duration.hashCode() : 0);
		result = 31 * result + (aheadOfTime != null ? aheadOfTime.hashCode() : 0);
		result = 31 * result + (data != null ? data.hashCode() : 0);
		return result;
	}
}
