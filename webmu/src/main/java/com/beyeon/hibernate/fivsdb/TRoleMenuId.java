package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * TRoleMenuId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class TRoleMenuId extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -8083826484125837344L;

	private Integer rid;
	private Integer mid;

	public TRoleMenuId() {
	}

	public TRoleMenuId(Integer rid, Integer mid) {
		this.rid = rid;
		this.mid = mid;
	}

	@Column(name = "rid")
	public Integer getRid() {
		return this.rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	@Column(name = "mid")
	public Integer getMid() {
		return this.mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TRoleMenuId))
			return false;
		TRoleMenuId castOther = (TRoleMenuId) other;

		return ((this.getRid() == castOther.getRid()) || (this.getRid() != null
				&& castOther.getRid() != null && this.getRid().equals(
				castOther.getRid())))
				&& ((this.getMid() == castOther.getMid()) || (this.getMid() != null
						&& castOther.getMid() != null && this.getMid().equals(
						castOther.getMid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRid() == null ? 0 : this.getRid().hashCode());
		result = 37 * result
				+ (getMid() == null ? 0 : this.getMid().hashCode());
		return result;
	}

}