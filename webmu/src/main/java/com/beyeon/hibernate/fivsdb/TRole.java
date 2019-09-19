package com.beyeon.hibernate.fivsdb;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * ScfgRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_role")
public class TRole extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -2980035584525695894L;

	private Integer rid;
	private String appCode;
	private String rname;
	private Short roleType = 0;
	private Short dmlflag;
	private Date dmltime;
	private Short status;
	private Byte isPublic = 0;

	public TRole() {
	}

	public TRole(String rname,Short roleType, Short dmlflag, Date dmltime) {
		this.rname = rname;
		this.roleType = roleType;
		this.dmlflag = dmlflag;
		this.dmltime = dmltime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "rid")
	public Integer getRid() {
		return this.rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	@Column(name = "app_code")
	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	@Column(name = "rname")
	public String getRname() {
		if(null != this.rname)
			return this.rname;
		return "";
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	@Column(name = "role_type")
	public Short getRoleType() {
		return roleType;
	}

	public void setRoleType(Short roleType) {
		this.roleType = roleType;
	}
	
	@Transient
	public String getRoleTypeName() {
		switch (this.roleType) {
			case 2:
				return "私有管理员";
			default:
				return "公共管理员";
		}
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

	@Column(name = "status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "is_public")
	public Byte getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Byte isPublic) {
		this.isPublic = isPublic;
	}

	@Transient
	public String getIsPublicName() {
		if(isPublic == 0){
			return "否";
		}
		return "是";
	}

}