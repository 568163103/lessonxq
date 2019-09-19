package com.beyeon.hibernate.fivsdb;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User: Administrator
 * Date: 2017/3/30
 * Time: 11:41
 */
@Entity
@Table(name = "tb_alarm_res")
public class TbAlarmRes{
	private Integer id;
	private String resId;
	private String alarmType;
	private String name;
	private String description;
	private String sid;
	
	public TbAlarmRes(){
		
	}
	public TbAlarmRes(String resId, String alarmType, String name) {
		super();
		this.resId = resId;
		this.alarmType = alarmType;
		this.name = name;
	}
	public TbAlarmRes(String resId, String alarmType, String name,String sid) {
		super();
		this.resId = resId;
		this.alarmType = alarmType;
		this.name = name;
		this.sid = sid;
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
	@Column(name = "res_id")
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	@Basic
	@Column(name = "alarm_type")
	public String getAlarmType() {
		return alarmType;
	}
	
	@Transient
	public String getAlarmTypeName() {
		return AlarmType.getTypeName(alarmType.toString());
	}
	
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
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
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Basic
	@Column(name = "sid")
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
}
