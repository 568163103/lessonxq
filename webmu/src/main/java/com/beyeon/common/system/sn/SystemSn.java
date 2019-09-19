package com.beyeon.common.system.sn;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * User: Administrator
 * Date: 2016/1/15
 * Time: 14:38
 */
public class SystemSn {
	private static Long MIN_USE_TIME = 1*31L*24*60*60*1000;
	String id = "";
	String uuid = "";
	Integer userNum = 10;
	Integer deviceNum = 4;
	Long useTime = 0L;
	Date startTime = new Date();
	Date endTime = new Date(System.currentTimeMillis()+MIN_USE_TIME);

	public SystemSn() {
	}

	public SystemSn(String uuid) {
		this.uuid = uuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public Integer getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(Integer deviceNum) {
		this.deviceNum = deviceNum;
	}

	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean isAvailable(){
		if (useTime <= 0){
			return true;
		}
		if (endTime.getTime() > System.currentTimeMillis() &&
				(System.currentTimeMillis()+60 * 60 * 1000) > (startTime.getTime() + useTime)){
			return true;
		}
		return false;
	}

}
