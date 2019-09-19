package com.beyeon.nvss.dmu.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.beyeon.hibernate.fivsdb.ServerStateRecord;
import com.beyeon.hibernate.fivsdb.ServerStateRecordCpu;
import com.beyeon.hibernate.fivsdb.ServerStateRecordDisk;
import com.beyeon.hibernate.fivsdb.ServerStateRecordNetcard;

public class ServerStateRecordDto {
	private ServerStateRecord serverStateRecord;
	private List<ServerStateRecordCpu> serverStateRecordCpu;
	private List<ServerStateRecordDisk> serverStateRecordDisk;
	private List<ServerStateRecordNetcard> serverStateRecordNetcard;

	public ServerStateRecordDto (){
		
	}
	
	public ServerStateRecordDto(ServerStateRecordDto dto){
		this.serverStateRecord  = new ServerStateRecord(dto.getServerStateRecord());
		ArrayList<ServerStateRecordCpu> cpustate = new ArrayList<ServerStateRecordCpu>();
		for (ServerStateRecordCpu cpu : dto.getServerStateRecordCpu()){
			cpustate.add(new ServerStateRecordCpu(cpu));
		}
		ArrayList<ServerStateRecordDisk> diskstate = new ArrayList<ServerStateRecordDisk>();
		for (ServerStateRecordDisk disk : dto.getServerStateRecordDisk()){
			diskstate.add(new ServerStateRecordDisk(disk));
		}
		ArrayList<ServerStateRecordNetcard> netstate = new ArrayList<ServerStateRecordNetcard>();
		for (ServerStateRecordNetcard net : dto.getServerStateRecordNetcard()){
			netstate.add(new ServerStateRecordNetcard(net));
		}
		
		this.serverStateRecordCpu  = cpustate;
		this.serverStateRecordDisk = diskstate;
		this.serverStateRecordNetcard = netstate;
	}
	
	public ServerStateRecord getServerStateRecord() {
		return serverStateRecord;
	}
	public void setServerStateRecord(ServerStateRecord serverStateRecord) {
		this.serverStateRecord = serverStateRecord;
	}
	public List<ServerStateRecordCpu> getServerStateRecordCpu() {
		return serverStateRecordCpu;
	}
	public void setServerStateRecordCpu(List<ServerStateRecordCpu> serverStateRecordCpu) {
		this.serverStateRecordCpu = serverStateRecordCpu;
	}
	public List<ServerStateRecordDisk> getServerStateRecordDisk() {
		return serverStateRecordDisk;
	}
	public void setServerStateRecordDisk(List<ServerStateRecordDisk> serverStateRecordDisk) {
		this.serverStateRecordDisk = serverStateRecordDisk;
	}
	public List<ServerStateRecordNetcard> getServerStateRecordNetcard() {
		return serverStateRecordNetcard;
	}
	public void setServerStateRecordNetcard(List<ServerStateRecordNetcard> serverStateRecordNetcard) {
		this.serverStateRecordNetcard = serverStateRecordNetcard;
	}
	
	
	
	
}
