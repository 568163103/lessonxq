package com.beyeon.hibernate.fivsdb;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.beyeon.common.web.model.entity.BaseEntity;

@Entity
@Table(name = "t_dept")
public class TDept extends BaseEntity implements java.io.Serializable {
	private	Integer	deptId;
	private	Integer	preid;
	private	String	fullDid;
	private	String	deptName;
	private	Short	lel;
	private	Short	dmlflag;
	private	Date	dmltime = new Date();
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "dept_id")
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	
	@Column(name = "preid")
	public Integer getPreid() {
		return preid;
	}
	public void setPreid(Integer preid) {
		this.preid = preid;
	}
	
	@Column(name = "full_did")
	public String getFullDid() {
		return fullDid;
	}
	public void setFullDid(String fullDid) {
		this.fullDid = fullDid;
	}
	
	@Column(name = "dept_name")
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	@Column(name = "lel")
	public Short getLel() {
		return lel;
	}
	public void setLel(Short lel) {
		this.lel = lel;
	}
	
	@Column(name = "dmlflag")
	public Short getDmlflag() {
		return dmlflag;
	}
	public void setDmlflag(Short dmlflag) {
		this.dmlflag = dmlflag;
	}
	
	@Column(name = "dmltime")
	public Date getDmltime() {
		return dmltime;
	}
	public void setDmltime(Date dmltime) {
		this.dmltime = dmltime;
	}

}
