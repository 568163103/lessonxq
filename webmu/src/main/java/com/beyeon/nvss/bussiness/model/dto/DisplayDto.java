package com.beyeon.nvss.bussiness.model.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import com.beyeon.common.util.DateUtil;

/**
 * User: Administrator
 * Date: 2015/11/17
 * Time: 9:07
 */
public class DisplayDto {
	private String resId;
	private String sn = String.valueOf(System.currentTimeMillis());
	private int recordPath;
	private int recordType;
	private String startTime;
	private String endTime;
	private long page = 0;

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public int getRecordPath() {
		return recordPath;
	}

	public void setRecordPath(int recordPath) {
		this.recordPath = recordPath;
	}

	public int getRecordType() {
		return recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return DateUtil.parse(startTime,DateUtil.yyyyMMddHHmmssSpt);
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setStime(String startTime) {
		this.startTime = DateUtil.format(new Date(Long.valueOf(startTime)*1000),DateUtil.yyyyMMddHHmmssSpt);
	}

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return DateUtil.parse(endTime,DateUtil.yyyyMMddHHmmssSpt);
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setEtime(String endTime) {
		this.endTime = DateUtil.format(new Date(Long.valueOf(endTime)*1000),DateUtil.yyyyMMddHHmmssSpt);
	}

	public Date getBeginTime() {
		return DateUtil.parse(DateUtil.parse(endTime, DateUtil.yyyyMMddSpt), DateUtil.yyyyMMddSpt);
	}
}
