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
@Table(name = "terminal_user")
@IdClass(TerminalUserPK.class)
public class TerminalUser {
	private String terminalId;
	private String userId;

	@Id
	@Column(name = "terminal_id")
	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	@Id
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TerminalUser that = (TerminalUser) o;

		if (terminalId != null ? !terminalId.equals(that.terminalId) : that.terminalId != null) return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = terminalId != null ? terminalId.hashCode() : 0;
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		return result;
	}
}
