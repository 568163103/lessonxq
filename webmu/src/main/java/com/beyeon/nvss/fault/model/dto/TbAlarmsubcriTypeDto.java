package com.beyeon.nvss.fault.model.dto;

import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Transient;

import com.beyeon.hibernate.fivsdb.TbAlarmsubcriType;

public class TbAlarmsubcriTypeDto implements Serializable{
	private TbAlarmsubcriType tbAlarmsubcriType;
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

	public TbAlarmsubcriType getTbAlarmsubcriType() {
		return tbAlarmsubcriType;
	}

	public void setTbAlarmsubcriType(TbAlarmsubcriType tbAlarmsubcriType) {
		this.tbAlarmsubcriType = tbAlarmsubcriType;
	}

	
	
}
