package com.beyeon.nvss.dmu.model.dto;

import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Transient;

import com.beyeon.hibernate.fivsdb.DmuAlarmInfo;

public class DmuAlarmInfoDto implements Serializable{
	private DmuAlarmInfo dmuAlarmInfo;
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
	public DmuAlarmInfo getDmuAlarmInfo() {
		return dmuAlarmInfo;
	}
	public void setDmuAlarmInfo(DmuAlarmInfo dmuAlarmInfo) {
		this.dmuAlarmInfo = dmuAlarmInfo;
	}
	
	
	
}
