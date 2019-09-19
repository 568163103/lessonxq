package com.beyeon.nvss.device.model.dto;

import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.hibernate.fivsdb.Terminal;

import javax.persistence.Transient;
import java.io.InputStream;
import java.io.Serializable;

public class TerminalDto implements Serializable{
	private Terminal terminal;
	
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


	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
}
