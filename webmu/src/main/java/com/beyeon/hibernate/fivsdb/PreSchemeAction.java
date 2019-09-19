package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "pre_scheme_action")
@IdClass(PreSchemeActionPK.class)
public class PreSchemeAction {
	private Integer schemeId;
	private Integer actionId;

	public PreSchemeAction(Integer schemeId, Integer actionId) {
		this.schemeId = schemeId;
		this.actionId = actionId;
	}

	@Id
	@Column(name = "scheme_id")
	public Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	@Id
	@Column(name = "action_id")
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

		PreSchemeAction that = (PreSchemeAction) o;

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
