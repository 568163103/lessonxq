package com.beyeon.nvss.dmu.model.dto;

import com.beyeon.hibernate.fivsdb.DiskStateRecord;

public class DiskStateRecordDto {
	private DiskStateRecord diskStateRecord;
	
	public DiskStateRecordDto (){}
	
	public DiskStateRecordDto(DiskStateRecordDto dto){
		this.diskStateRecord =new DiskStateRecord(dto.getDiskStateRecord());
	}
	public DiskStateRecord getDiskStateRecord() {
		return diskStateRecord;
	}
	public void setDiskStateRecord(DiskStateRecord diskStateRecord) {
		this.diskStateRecord = diskStateRecord;
	}
	
	
	
	
	
}
