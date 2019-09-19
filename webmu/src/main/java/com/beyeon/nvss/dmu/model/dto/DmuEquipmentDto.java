package com.beyeon.nvss.dmu.model.dto;

import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Transient;

import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.EncoderStateRecord;
import com.beyeon.hibernate.fivsdb.ServerStateRecord;
import com.beyeon.hibernate.fivsdb.SwitchStateRecord;

public class DmuEquipmentDto implements Serializable{
	private DmuEquipment dmuEquipment;
	private SwitchStateRecordDto switchStateRecord;
	private ServerStateRecordDto serverStateRecord;
	private EncoderStateRecord encoderStateRecord;
	private DiskStateRecord diskStateRecord;
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
	public DmuEquipment getDmuEquipment() {
		return dmuEquipment;
	}
	public void setDmuEquipment(DmuEquipment dmuEquipment) {
		this.dmuEquipment = dmuEquipment;
	}
	public SwitchStateRecordDto getSwitchStateRecord() {
		return switchStateRecord;
	}
	public void setSwitchStateRecord(SwitchStateRecordDto switchStateRecord) {
		this.switchStateRecord = switchStateRecord;
	}
	public ServerStateRecordDto getServerStateRecord() {
		return serverStateRecord;
	}
	public void setServerStateRecord(ServerStateRecordDto serverStateRecord) {
		this.serverStateRecord = serverStateRecord;
	}
	public EncoderStateRecord getEncoderStateRecord() {
		return encoderStateRecord;
	}
	public void setEncoderStateRecord(EncoderStateRecord encoderStateRecord) {
		this.encoderStateRecord = encoderStateRecord;
	}
	public DiskStateRecord getDiskStateRecord() {
		return diskStateRecord;
	}
	public void setDiskStateRecord(DiskStateRecord diskStateRecord) {
		this.diskStateRecord = diskStateRecord;
	}
	
	
}
