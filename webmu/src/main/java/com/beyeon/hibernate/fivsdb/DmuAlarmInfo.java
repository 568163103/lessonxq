package com.beyeon.hibernate.fivsdb;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.InputStream;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "dmu_alarm_info")
public class DmuAlarmInfo {
	private Integer id;
	private String sourceId;
	private String alarmType;
	private String name;
	private String beginTime;
	private String endTime;
	private Integer level;
	private Integer state;
	private String description;
	private String targetInfo;
	private String status;
	private String memo;
	private Integer dealState;
	private String dealTime;
	private String dealUserId;

	/** 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致*/
	private InputStream inputStream;
	/** 这个名称就是用来传给上面struts.xml中的${fileName}的*/
	private String fileName;

	@Transient
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Transient
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "source_id")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@Basic
	@Column(name = "alarm_type")
	public String getAlarmType() {
		return alarmType;
	}

	@Transient
	public String getDmuAlarmTypeName() {
		return DmuAlarmType.getTypeName(alarmType);
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "begin_time")
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	@Basic
	@Column(name = "end_time")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Basic
	@Column(name = "state")
	public Integer getState() {
		return state;
	}
	@Transient
	public String getStateZh() {
		return state==0 ?"正常":"告警";
	}
	public void setState(Integer state) {
		this.state = state;
	}

	@Basic
	@Column(name = "alarm_level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "target_info")
	public String getTargetInfo() {
		return targetInfo;
	}

	public void setTargetInfo(String targetInfo) {
		this.targetInfo = targetInfo;
	}

	@Basic
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Basic
	@Column(name = "deal_state")
	public Integer getDealState() {
		return dealState;
	}

	public void setDealState(Integer dealState) {
		this.dealState = dealState;
	}
	@Basic
	@Column(name = "deal_time")
	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	@Basic
	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Basic
	@Column(name = "deal_user_id")
	public String getDealUserId() {
		return dealUserId;
	}

	public void setDealUserId(String dealUserId) {
		this.dealUserId = dealUserId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alarmType == null) ? 0 : alarmType.hashCode());
		result = prime * result + ((beginTime == null) ? 0 : beginTime.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sourceId == null) ? 0 : sourceId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((targetInfo == null) ? 0 : targetInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DmuAlarmInfo other = (DmuAlarmInfo) obj;
		if (alarmType == null) {
			if (other.alarmType != null)
				return false;
		} else if (!alarmType.equals(other.alarmType))
			return false;
		if (beginTime == null) {
			if (other.beginTime != null)
				return false;
		} else if (!beginTime.equals(other.beginTime))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sourceId == null) {
			if (other.sourceId != null)
				return false;
		} else if (!sourceId.equals(other.sourceId))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (targetInfo == null) {
			if (other.targetInfo != null)
				return false;
		} else if (!targetInfo.equals(other.targetInfo))
			return false;
		return true;
	}


}
