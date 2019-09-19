package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * TUserRoleId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class TUserRoleId extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -7327086959912975861L;

	private Integer rid;
	private String amid;

	public TUserRoleId() {
	}

	public TUserRoleId(Integer rid, String amid) {
		this.rid = rid;
		this.amid = amid;
	}

	@Column(name = "rid")
	public Integer getRid() {
		return this.rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	@Column(name = "amid")
	public String getAmid() {
		return this.amid;
	}

	public void setAmid(String amid) {
		this.amid = amid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TUserRoleId))
			return false;
		TUserRoleId castOther = (TUserRoleId) other;

		return ((this.getRid() == castOther.getRid()) || (this.getRid() != null
				&& castOther.getRid() != null && this.getRid().equals(
				castOther.getRid())))
				&& ((this.getAmid() == castOther.getAmid()) || (this.getAmid() != null
						&& castOther.getAmid() != null && this.getAmid()
						.equals(castOther.getAmid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRid() == null ? 0 : this.getRid().hashCode());
		result = 37 * result
				+ (getAmid() == null ? 0 : this.getAmid().hashCode());
		return result;
	}

}