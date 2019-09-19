package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2017/3/30
 * Time: 11:41
 */
@Entity
@Table(name = "tb_user_alarm_res")
public class TbUserAlarmRes  implements Serializable{
	
	private Integer alarmResId;
	private String userId;


	@Id
	@Column(name = "alarm_res_id")
	public Integer getAlarmResId() {
		return alarmResId;
	}
	public void setAlarmResId(Integer alarmResId) {
		this.alarmResId = alarmResId;
	}
	
	@Id
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public TbUserAlarmRes(Integer alarmResId, String userId) {
		super();
		this.alarmResId = alarmResId;
		this.userId = userId;
	}
}
