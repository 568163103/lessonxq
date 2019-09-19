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
@Table(name = "dmu_equipment")
public class DmuEquipment extends BaseEntity {
	private String id;
	private String name;
	private String corp;	
	private String type;
	private String version;
	private String pos;
	private String ip;
	private String mac;
	private Integer port;
	private String remark;
	private String master;
	private Integer status;
	
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
	public DmuEquipment (){
		
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
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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
	@Column(name = "master")
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	@Basic
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Transient
	public String getStatusZh() {
		return status == 1 ?"在线":"离线";
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DmuEquipment equipment = (DmuEquipment) o;

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
