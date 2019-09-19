package com.beyeon.hibernate.fivsdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.beyeon.general.baseinfo.model.bpo.DictBpo;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "dmu_alarm_type")
public class DmuAlarmType {
	private static Map<String,String> typeMap = new HashMap<String,String>();
	private static List<Map<String,String>> typeList = new ArrayList<Map<String,String>>();
	
	private String id;
	private String name;
	private Integer level;
	private String description;
	
	public static String getTypeName(String id){
		return typeMap.get(id);
	}

	public static Map<String,String> getTypeMap(){
		return typeMap;
	}
	
	public static List<Map<String,String>> getTypeList(){
		if (typeList !=null  && typeList.size()>0){
			return typeList;
		}else {
			Set<Entry<String, String>> entrySet = typeMap.entrySet();
			for (Entry<String, String> entry :entrySet){
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("id", entry.getKey());
				map.put("name", entry.getValue());
				typeList.add(map);
			}
			return typeList;
		}
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
	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}
	@Transient
	public String getAlarmLevelZh() {
		if (null != id) {
			return DictBpo.getDictName(TDict.ALARM_LEVEL,level.toString());
		}
		return "";
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
}
