package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by Administrator on 2016/8/3.
 */
public class TPrivDataPK implements Serializable {
	private String userId;
	private String privCode;

	@Column(name = "user_id")
	@Id
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "priv_code")
	@Id
	public String getPrivCode() {
		return privCode;
	}

	public void setPrivCode(String privCode) {
		this.privCode = privCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TPrivDataPK that = (TPrivDataPK) o;

		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (privCode != null ? !privCode.equals(that.privCode) : that.privCode != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userId != null ? userId.hashCode() : 0;
		result = 31 * result + (privCode != null ? privCode.hashCode() : 0);
		return result;
	}
}
