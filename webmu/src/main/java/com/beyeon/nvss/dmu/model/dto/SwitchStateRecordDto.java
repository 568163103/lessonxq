package com.beyeon.nvss.dmu.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.beyeon.hibernate.fivsdb.SwitchStateRecord;
import com.beyeon.hibernate.fivsdb.SwitchStateRecordPort;

public class SwitchStateRecordDto {
	private SwitchStateRecord switchStateRecord;
	private List<SwitchStateRecordPort> switchStateRecordPort;
	
	public  SwitchStateRecordDto(){
		
	}
	
	public  SwitchStateRecordDto( SwitchStateRecordDto dto){
		this.switchStateRecord = new SwitchStateRecord(dto.getSwitchStateRecord());
		List<SwitchStateRecordPort> ports = new ArrayList<SwitchStateRecordPort>();
		for (SwitchStateRecordPort port : dto.getSwitchStateRecordPort()){
			ports.add(new SwitchStateRecordPort(port));
		}
		this.switchStateRecordPort = ports;
	}
	public SwitchStateRecord getSwitchStateRecord() {
		return switchStateRecord;
	}
	public void setSwitchStateRecord(SwitchStateRecord switchStateRecord) {
		this.switchStateRecord = switchStateRecord;
	}
	public List<SwitchStateRecordPort> getSwitchStateRecordPort() {
		return switchStateRecordPort;
	}
	public void setSwitchStateRecordPort(List<SwitchStateRecordPort> switchStateRecordPort) {
		this.switchStateRecordPort = switchStateRecordPort;
	}
	
	
	
	
	
}
