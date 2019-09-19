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
@Table(name = "dmu_alarm_res")
public class DmuAlarmRes{
	private Integer id;
	private String resId;
	private String alarmType;
	private String name;
	private String description;
	
	public DmuAlarmRes(){
		
	}

	public DmuAlarmRes(String resId, String alarmType, String name) {
		super();
		this.resId = resId;
		this.alarmType = alarmType;
		this.name = name;
	}
	
	public DmuAlarmRes(String resId, String alarmType, String name,String description) {
		super();
		this.resId = resId;
		this.alarmType = alarmType;
		this.name = name;
		this.description = description;
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
	public String getDmuAlarmTypeName() {
		return DmuAlarmType.getTypeName(alarmType);
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
	
}
