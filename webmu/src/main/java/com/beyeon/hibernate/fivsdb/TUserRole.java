package com.beyeon.hibernate.fivsdb;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * ScfgRoleMap entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user_role")
public class TUserRole extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -3950569304544866999L;

	private TUserRoleId id;
	private Short dmlflag;
	private Date dmltime;

	public TUserRole() {
	}

	public TUserRole(TUserRoleId id, Short dmlflag, Date dmltime) {
		this.id = id;
		this.dmlflag = dmlflag;
		this.dmltime = dmltime;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "rid", column = @Column(name = "rid")),
			@AttributeOverride(name = "amid", column = @Column(name = "amid")) })
	public TUserRoleId getId() {
		return this.id;
	}

	public void setId(TUserRoleId id) {
		this.id = id;
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