package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "encoder_disk")
public class EncoderDisk {
	private String id;
	private String diskInfo;
	private String lastTime;
	private String description;
	
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Basic
	@Column(name = "disk_info")
	public String getDiskInfo() {
		return diskInfo;
	}

	public void setDiskInfo(String diskInfo) {
		this.diskInfo = diskInfo;
	}

	@Basic
	@Column(name = "lasttime")
	public String getLastTime() {
		return lastTime;
	}
	
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	
	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		EncoderDisk ed = (EncoderDisk)o;
		if (id != null ? !id.equals(ed.id) : ed.id != null) return false;
		if (diskInfo != null ? !diskInfo.equals(ed.diskInfo) : ed.diskInfo != null) return false;
		if (description != null ? !description.equals(ed.description) : ed.description != null) return false;
		if (lastTime != null ? !lastTime.equals(ed.lastTime) : ed.lastTime != null) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (diskInfo != null ? diskInfo.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
		
		return result;
	}
}
