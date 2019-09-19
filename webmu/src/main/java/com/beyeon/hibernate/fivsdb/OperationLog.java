package com.beyeon.hibernate.fivsdb;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "operation_log")
public class OperationLog   {
	private Integer id;
	private String userId;
	private String userName;
	private String terminalIp;
	private String time;
	private String operation;
	private String objectId;
	private String description;
	private String objectTime;
	

	public static String getTypeName(String id){
		return DictBpo.getDictName(TDict.OPERATION_TYPE, id);
	}

	public static Collection<AutoCompleteDto> getTypes(){
		return DictBpo.find(TDict.OPERATION_TYPE);
	}
	@Id
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Basic
	@Column(name = "terminal_ip")
	public String getTerminalIp() {
		return terminalIp;
	}

	public void setTerminalIp(String terminalIp) {
		this.terminalIp = terminalIp;
	}

	@Basic
	@Column(name = "time")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Basic
	@Column(name = "operation")
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Basic
	@Column(name = "object_id")
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	@Basic
	@Column(name = "object_time")
	public String getObjectTime() {
		return objectTime;
	}

	public void setObjectTime(String objectTime) {
		this.objectTime = objectTime;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OperationLog that = (OperationLog) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
		if (terminalIp != null ? !terminalIp.equals(that.terminalIp) : that.terminalIp != null) return false;
		if (time != null ? !time.equals(that.time) : that.time != null) return false;
		if (operation != null ? !operation.equals(that.operation) : that.operation != null) return false;
		if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		result = 31 * result + (userName != null ? userName.hashCode() : 0);
		result = 31 * result + (terminalIp != null ? terminalIp.hashCode() : 0);
		result = 31 * result + (time != null ? time.hashCode() : 0);
		result = 31 * result + (operation != null ? operation.hashCode() : 0);
		result = 31 * result + (objectId != null ? objectId.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
