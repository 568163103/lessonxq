package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.beyeon.common.util.DateUtil;


/**
 * The persistent class for the case_info database table.
 * 
 */
@Entity
@Table(name="case_info")
public class CaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cid")
	private String cid;

	@Column(name="case_type")
	private int casetype;

	private String uid ="";

	@Column(name="code_num")
	private String codenum = "";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime = new Date();

	private String creator = "";

	@Lob
	private String description = "";

	@Column(name="detect_state")
	private int detectstate = 1;

	@Column(name="happening_place")
	private String happeningplace = "";

	@Column(name="jurisdiction_area")
	private String jurisdictionarea ="";

	private String name ="";

	public CaseInfo() {
	}

	public int getCasetype() {
		return this.casetype;
	}

	public String getCaseTypeName() {
		switch (this.casetype){
			case 1 :return "一般";
			case 2 :return "特殊";
			default:return "其它";
		}
	}

	public void setCasetype(int casetype) {
		this.casetype = casetype;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCodenum() {
		return this.codenum;
	}

	public void setCodenum(String codenum) {
		this.codenum = codenum;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return DateUtil.format(this.createTime, DateUtil.yyyyMMddHHmmssSpt);
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDetectstate() {
		return this.detectstate;
	}

	public String getDetectStateZh() {
		switch (this.detectstate){
			case 1 :return "正在进行";
			case 2 :return "已结案";
			default:return "其它";
		}
	}

	public void setDetectstate(int detectstate) {
		this.detectstate = detectstate;
	}

	public String getHappeningplace() {
		return this.happeningplace;
	}

	public void setHappeningplace(String happeningplace) {
		this.happeningplace = happeningplace;
	}

	public String getJurisdictionarea() {
		return this.jurisdictionarea;
	}

	public void setJurisdictionarea(String jurisdictionarea) {
		this.jurisdictionarea = jurisdictionarea;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}