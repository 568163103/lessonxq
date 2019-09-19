package com.beyeon.nvss.device.model.dto;

import java.io.InputStream;

import javax.persistence.Transient;

import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.ServerExtra;

public class ServerDto {
	private Server server;
	private ServerExtra serverExtra;
	
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
	
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	public ServerExtra getServerExtra() {
		return serverExtra;
	}
	public void setServerExtra(ServerExtra serverExtra) {
		this.serverExtra = serverExtra;
	}
	
	
	
}
