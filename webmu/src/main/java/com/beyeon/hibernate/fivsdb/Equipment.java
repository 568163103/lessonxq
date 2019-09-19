package com.beyeon.hibernate.fivsdb;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "equipment")
public class Equipment extends BaseEntity {
	private static Map<String,String> typeMap = new HashMap<String,String>();
	private static Map<String,String> corpMap = new HashMap<String,String>();
	private static Map<String,Map<String,String>> objectMap = new HashMap<String,Map<String,String>>();
	private String id;
	private String name;
	private String corp;	
	private Integer type;
	private String version;
	private String pos;
	private String ip;
	private String mac;
	private Integer port;
	private String remark;
	private Integer status;
	private String corpName;
	
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
	public Equipment (){
		
	}
	public static String getTypeName(String id){
		return typeMap.get(id);
	}

	public static Map<String,String> getTypeMap(){
		return typeMap;
	}
	
	public static String getCorpName(String id){
		return corpMap.get(id);
	}

	public static Map<String,String> getCorpMap(){
		return corpMap;
	}

	public static Map<String,String> getObjectMap(String type){
		Map<String,String> childServerMap = objectMap.get(type);
		if (null == childServerMap){
			childServerMap = new HashMap<String, String>();
			objectMap.put(type, childServerMap);
		}
		return childServerMap;
	}

	public static String getObjectName(String id){
		for (String t : typeMap.keySet()) {
			String equipmentName = getObjectName(t,id);
			if(StringUtils.isNotBlank(equipmentName)){
				return equipmentName;
			}
		}
		return "";
	}

	public static String getObjectName(String type, String id){
		if(null == objectMap.get(type)){
			return "";
		}
		return objectMap.get(type).get(id);
	}

	public static void setObjectMap(Map<String, Map<String, String>> currObjectMap){
		Map<String,Map<String,String>> oldObjectMap = objectMap;
		objectMap = currObjectMap;
		oldObjectMap.clear();
	}

	public static Map<String,String> getObjectMap(Map<String, Map<String, String>> currObejctMap, String type){
		Map<String,String> childServerMap = currObejctMap.get(type);
		if (null == childServerMap){
			childServerMap = new HashMap<String, String>();
			currObejctMap.put(type, childServerMap);
		}
		return childServerMap;
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
	@Column(name = "corp")
	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}
	@Basic
	@Column(name = "type")
	public Integer getType() {
		return type;
	}
	
	@Transient
	public String getTypeName() {
		return typeMap.get(type.toString());
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Transient
	public String getCorpName(){
		return typeMap.get(corp);
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
	@Column(name = "pos")
	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
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
	@Column(name = "mac")
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	@Basic
	@Column(name = "port")
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	@Basic
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Basic
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Equipment equipment = (Equipment) o;

		if (id != null ? !id.equals(equipment.id) : equipment.id != null) return false;
		if (name != null ? !name.equals(equipment.name) : equipment.name != null) return false;
		if (type != null ? !type.equals(equipment.type) : equipment.type != null) return false;
		if (corp != null ? !corp.equals(equipment.corp) : equipment.corp != null) return false;
		if (ip != null ? !ip.equals(equipment.ip) : equipment.ip != null) return false;
		if (port != null ? !port.equals(equipment.port) : equipment.port != null) return false;
		if (version != null ? !version.equals(equipment.version) : equipment.version != null) return false;
		if (pos != null ? !pos.equals(equipment.pos) : equipment.pos != null) return false;
		if (mac != null ? !mac.equals(equipment.mac) : equipment.mac != null)	return false;
		if (remark != null ? !remark.equals(equipment.remark) : equipment.remark != null) return false;
		if (status != null ? !status.equals(equipment.status) : equipment.status != null) return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (corp != null ? corp.hashCode() : 0);
		result = 31 * result + (ip != null ? ip.hashCode() : 0);
		result = 31 * result + (port != null ? port.hashCode() : 0);
		result = 31 * result + (version != null ? version.hashCode() : 0);
		result = 31 * result + (pos != null ? pos.hashCode() : 0);
		result = 31 * result + (mac != null ? mac.hashCode() : 0);
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		return result;
	}
}
