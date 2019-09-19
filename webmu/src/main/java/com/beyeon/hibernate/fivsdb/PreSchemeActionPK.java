package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
public class PreSchemeActionPK implements Serializable {
	private Integer schemeId;
	private Integer actionId;

	@Column(name = "scheme_id")
	@Id
	public Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	@Column(name = "action_id")
	@Id
	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PreSchemeActionPK that = (PreSchemeActionPK) o;

		if (schemeId != null ? !schemeId.equals(that.schemeId) : that.schemeId != null) return false;
		if (actionId != null ? !actionId.equals(that.actionId) : that.actionId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = schemeId != null ? schemeId.hashCode() : 0;
		result = 31 * result + (actionId != null ? actionId.hashCode() : 0);
		return result;
	}
}
