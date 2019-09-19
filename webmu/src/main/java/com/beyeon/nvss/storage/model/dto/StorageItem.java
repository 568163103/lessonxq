package com.beyeon.nvss.storage.model.dto;

import java.util.Date;

import com.beyeon.common.util.DateUtil;

public class StorageItem {
	private String beginTime;
	private String endTime;
	private String lengthDesc;
	private int length = 0; 
	private int totalLength = 0;
	private double percent = 0.0f;
	
	public String getBeginTime() {
		return beginTime;
	}
	
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	public String getLengthDesc() {
		Date begin = DateUtil.parse(beginTime, DateUtil.yyyyMMddHHmmssSpt);
		Date end = DateUtil.parse(endTime, DateUtil.yyyyMMddHHmmssSpt);
		long val = (end.getTime() - begin.getTime()) / 1000;
		int day  = (int) (val / 60 / 60 /24 );
		int hour = (int) (val / 60 /60 % 24);
		int min  =  (int) (val / 60 % 60) ;
		int sec =  (int) (val % 60) ;
		String desc = "";
		if (day >0){
			desc =  day+" 天  " + hour+" 时  "+min+" 分  "+sec+" 秒";
		}else  if (hour > 0 ){
			desc =  hour+" 时  "+min+" 分  "+sec+" 秒";
		}else if(min > 0 ){
			desc = min+" 分  "+sec+" 秒";
		}else {
			desc = sec+" 秒";
		}
		this.setLengthDesc(desc);
		return lengthDesc;
	}

	public void setLengthDesc(String lengthDesc) {
		this.lengthDesc = lengthDesc;
	}

	public int getLength() {
		Date begin = DateUtil.parse(beginTime, DateUtil.yyyyMMddHHmmssSpt);
		Date end = DateUtil.parse(endTime, DateUtil.yyyyMMddHHmmssSpt);
		long val = (end.getTime() - begin.getTime()) / 1000;
		this.setLength((int)val);
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(int totalLength) {
		this.totalLength = totalLength;
	}

	public double getPercent() {
		this.setPercent((this.length * 1.0  / this.totalLength * 100));
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}
	
	
}
