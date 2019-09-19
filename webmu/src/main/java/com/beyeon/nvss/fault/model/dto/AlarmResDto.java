package com.beyeon.nvss.fault.model.dto;

import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Transient;

import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.EncoderStateRecord;
import com.beyeon.hibernate.fivsdb.TbAlarmRes;
import com.beyeon.nvss.dmu.model.dto.ServerStateRecordDto;
import com.beyeon.nvss.dmu.model.dto.SwitchStateRecordDto;

public class AlarmResDto implements Serializable{
	private TbAlarmRes alarmRes;
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

	public TbAlarmRes getAlarmRes() {
		return alarmRes;
	}

	public void setAlarmRes(TbAlarmRes tbAlarmRes) {
		this.alarmRes = tbAlarmRes;
	}
	
	
}
