package com.beyeon.hibernate.fivsdb;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.beyeon.common.web.model.entity.BaseEntity;

@Entity
@Table(name = "t_dict")
public class TDict extends BaseEntity {
	public static String USER_PTZ_LEVEL = "1";
	public static String USER_AV_LEVEL = "2";
	public static String PRESET_FLAG = "3";
	public static String GROUPS_TYPE = "4";
	public static String USER_GROUPS_RIGHT_TYPE = "5";
	public static String ACTION_TYPE = "6";
	public static String ALARM_TYPE = "7";
	public static String SYSTEM_PARAM = "8";
	public static String TB_DEPARTMENT = "9";
	public static String TB_USER_TYPE = "10";
	public static String ALARM_LEVEL = "11";
	public static String CAMERA_TYPE = "12";
	public static String OPERATION_TYPE = "13";
	public static String OPERATION_LOG = "14";

	private Integer did;
	private Integer preId;
	private String name;
	private String value;
	private String descr;
	private Short dmlflag = 1;
	private Date dmltime = new Date();

	public TDict() {
	}

	public TDict(Integer did, Integer preId, String name,
			Short dmlflag, Date dmltime) {
		this.did = did;
		this.preId = preId;
		this.name = name;
		this.dmlflag = dmlflag;
		this.dmltime = dmltime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "did")
	public Integer getDid() {
		return this.did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	@Column(name = "pre_id")
	public Integer getPreId() {
		return this.preId;
	}

	public void setPreId(Integer preId) {
		this.preId = preId;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "value")
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "descr")
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "dmlflag")
	public Short getDmlflag() {
		return this.dmlflag;
	}

	public void setDmlflag(Short dmlflag) {
		this.dmlflag = dmlflag;
	}

	@Column(name = "dmltime")
	public Date getDmltime() {
		return this.dmltime;
	}

	public void setDmltime(Date dmltime) {
		this.dmltime = dmltime;
	}

}