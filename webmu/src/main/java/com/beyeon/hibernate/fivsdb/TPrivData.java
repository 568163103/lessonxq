package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * Created by Administrator on 2016/8/3.
 */
@Entity
@Table(name = "t_priv_data")
@IdClass(TPrivDataPK.class)
public class TPrivData extends BaseEntity implements Serializable {
	public static Short PRIV_TYPE_CORP = 1;
	private String userId;
	private String privCode;
	private Short privType;
	private Short dmlflag;
	private Timestamp dmltime;

	@Id
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Id
	@Column(name = "priv_code")
	public String getPrivCode() {
		return privCode;
	}

	public void setPrivCode(String privCode) {
		this.privCode = privCode;
	}

	@Basic
	@Column(name = "priv_type")
	public Short getPrivType() {
		return privType;
	}

	public void setPrivType(Short privType) {
		this.privType = privType;
	}

	@Basic
	@Column(name = "dmlflag")
	public Short getDmlflag() {
		return dmlflag;
	}

	public void setDmlflag(Short dmlflag) {
		this.dmlflag = dmlflag;
	}

	@Basic
	@Column(name = "dmltime")
	public Timestamp getDmltime() {
		return dmltime;
	}

	public void setDmltime(Timestamp dmltime) {
		this.dmltime = dmltime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TPrivData tPrivData = (TPrivData) o;

		if (userId != null ? !userId.equals(tPrivData.userId) : tPrivData.userId != null) return false;
		if (privCode != null ? !privCode.equals(tPrivData.privCode) : tPrivData.privCode != null) return false;
		if (privType != null ? !privType.equals(tPrivData.privType) : tPrivData.privType != null) return false;
		if (dmlflag != null ? !dmlflag.equals(tPrivData.dmlflag) : tPrivData.dmlflag != null) return false;
		if (dmltime != null ? !dmltime.equals(tPrivData.dmltime) : tPrivData.dmltime != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userId != null ? userId.hashCode() : 0;
		result = 31 * result + (privCode != null ? privCode.hashCode() : 0);
		result = 31 * result + (privType != null ? privType.hashCode() : 0);
		result = 31 * result + (dmlflag != null ? dmlflag.hashCode() : 0);
		result = 31 * result + (dmltime != null ? dmltime.hashCode() : 0);
		return result;
	}
}
