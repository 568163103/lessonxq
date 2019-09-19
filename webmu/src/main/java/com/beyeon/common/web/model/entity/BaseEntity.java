package com.beyeon.common.web.model.entity;

import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Transient;

public class BaseEntity implements Serializable {
	/**
	 * 增删改查标记
	 * */
	public static short insert = 1;
	public static short edit = 2;
	public static short delete = 3;
	protected Boolean enabled = true;
	protected Boolean status = false;
	
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

	@Transient
	public String getEnabledZh() {
		return enabled?"是":"否";
	}

	@Transient
	public String getStatusZh() {
		return null != status && status?"在线":"离线";
	}
}
