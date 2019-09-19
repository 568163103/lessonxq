package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "alarm_info")
public class AlarmInfo {
	private Integer id;
	private String sourceId;
	private Integer alarmType;
	private String name;
	private String beginTime;
	private String endTime;
	private Integer state;
	private String storageServerId;
	private Integer dealState;
	private String dealUserId;
	private String dealTime;
	private String description;
	private String memo;
	private String alarmLevel;

	public static Map<String, String> getDealStateMap() {
		return dealStateMap;
	}

	private static final Map<String,String> dealStateMap;
	static
	{
		dealStateMap = new HashMap<>();
		dealStateMap.put("0","待确认");
		dealStateMap.put("1","处理中");
		dealStateMap.put("2","待归档");
		dealStateMap.put("3","已归档");

	}
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
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
	@Column(name = "source_id")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@Basic
	@Column(name = "alarm_type")
	public Integer getAlarmType() {
		return alarmType;
	}

	@Transient
	public String getAlarmTypeName() {
		return AlarmType.getTypeName(alarmType.toString());
	}
	
	@Transient
	public String getAlarmLevel() {
		this.alarmLevel = TbAlarmType.getLevel(alarmType.toString());
		return alarmLevel;
	}
	
	@Transient
	public String getAlarmLevelName() {
		return TbAlarmType.getLevelName(alarmType.toString());
	}
	
	public void setAlarmType(Integer alarmType) {
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

	public void setState(Integer state) {
		this.state = state;
	}

	@Basic
	@Column(name = "storage_server_id")
	public String getStorageServerId() {
		return storageServerId;
	}

	public void setStorageServerId(String storageServerId) {
		this.storageServerId = storageServerId;
	}

	@Basic
	@Column(name = "deal_state")
	public Integer getDealState() {
		return dealState;
	}

	@Transient
	public String getDealStateZh() {
		return dealStateMap.get(dealState);
	}

	public void setDealState(Integer dealState) {
		this.dealState = dealState;
	}

	@Basic
	@Column(name = "deal_user_id")
	public String getDealUserId() {
		return dealUserId;
	}

	public void setDealUserId(String dealUserId) {
		this.dealUserId = dealUserId;
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
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Basic
	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AlarmInfo alarmInfo = (AlarmInfo) o;

		if (id != null ? !id.equals(alarmInfo.id) : alarmInfo.id != null) return false;
		if (sourceId != null ? !sourceId.equals(alarmInfo.sourceId) : alarmInfo.sourceId != null) return false;
		if (alarmType != null ? !alarmType.equals(alarmInfo.alarmType) : alarmInfo.alarmType != null) return false;
		if (name != null ? !name.equals(alarmInfo.name) : alarmInfo.name != null) return false;
		if (beginTime != null ? !beginTime.equals(alarmInfo.beginTime) : alarmInfo.beginTime != null) return false;
		if (endTime != null ? !endTime.equals(alarmInfo.endTime) : alarmInfo.endTime != null) return false;
		if (state != null ? !state.equals(alarmInfo.state) : alarmInfo.state != null) return false;
		if (storageServerId != null ? !storageServerId.equals(alarmInfo.storageServerId) : alarmInfo.storageServerId != null)
			return false;
		if (dealState != null ? !dealState.equals(alarmInfo.dealState) : alarmInfo.dealState != null) return false;
		if (dealUserId != null ? !dealUserId.equals(alarmInfo.dealUserId) : alarmInfo.dealUserId != null) return false;
		if (dealTime != null ? !dealTime.equals(alarmInfo.dealTime) : alarmInfo.dealTime != null) return false;
		if (description != null ? !description.equals(alarmInfo.description) : alarmInfo.description != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
		result = 31 * result + (alarmType != null ? alarmType.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
		result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (storageServerId != null ? storageServerId.hashCode() : 0);
		result = 31 * result + (dealState != null ? dealState.hashCode() : 0);
		result = 31 * result + (dealUserId != null ? dealUserId.hashCode() : 0);
		result = 31 * result + (dealTime != null ? dealTime.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
