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

import com.beyeon.general.baseinfo.model.bpo.DictBpo;

/**
 * User: Administrator
 * Date: 2017/3/30
 * Time: 11:41
 */
@Entity
@Table(name = "tb_alarm_type")
public class TbAlarmType implements Serializable {
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
	
	private static Map<String,String> levelMap = new HashMap<String,String>();

	public static String getLevel(String id){
		return levelMap.get(id);
	}

	public static Map<String,String> getLevelMap(){
		return levelMap;
	}

	public static void setLevelMap(Map<String, String> currObjectMap){
		Map<String,String> oldObjectMap = levelMap;
		levelMap = currObjectMap;
		oldObjectMap.clear();
	}
	
	
	private static Map<String,String> levelNameMap = new HashMap<String,String>();

	public static String getLevelName(String id){
		return levelNameMap.get(id);
	}

	public static Map<String,String> getLevelNameMap(){
		return levelNameMap;
	}

	public static void setLevelNameMap(Map<String, String> currObjectMap){
		Map<String,String> oldObjectMap = levelNameMap;
		levelNameMap = currObjectMap;
		oldObjectMap.clear();
	}
	
	private String id;
	private String typeId;
	private Integer level;
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
	@Column(name = "type_id")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Basic
	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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
		if (null != id) {
			return DictBpo.getDictName(TDict.ALARM_TYPE,id.toString());
		}
		return "";
	}
	
	@Transient
	public String getAlarmLevelZh() {
		if (null != id) {
			return DictBpo.getDictName(TDict.ALARM_LEVEL,level.toString());
		}
		return "";
	}
}
