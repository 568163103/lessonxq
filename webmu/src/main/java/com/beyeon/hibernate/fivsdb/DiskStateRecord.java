package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "disk_state_record")
@GenericGenerator(name="genID", strategy="increment")
public class DiskStateRecord extends BaseEntity{
	private Integer id;
	private String diskid;
	private String totalvolume;
	private String undistributed;
	private String portNum;
	private String cpu;
	private String fan;
	private String bad;
	private String state;
	private String recordTime;

	public DiskStateRecord (){
		
	}
	
	public DiskStateRecord(DiskStateRecord record){
		this.diskid = record.getDiskid();
		this.totalvolume = record.getTotalvolume();
		this.undistributed = record.getUndistributed();
		this.portNum = record.getPortNum();
		this.cpu = record.getCpu();
		this.fan = record.getFan();
		this.bad = record.getBad();
		this.state = record.getState();
	}
	@Id
	@GeneratedValue(generator="genID")
	@Column(name = "id")
	public Integer  getId() {
		return id;
	}

	public void setId(Integer  id) {
		this.id = id;
	}

	@Basic
	@Column(name = "diskid")
	public String getDiskid() {
		return diskid;
	}

	public void setDiskid(String diskid) {
		this.diskid = diskid;
	}

	
	@Basic
	@Column(name = "totalvolume")
	public String getTotalvolume() {
		return totalvolume;
	}

	public void setTotalvolume(String totalvolume) {
		this.totalvolume = totalvolume;
	}
	
	@Basic
	@Column(name = "undistributed")
	public String getUndistributed() {
		return undistributed;
	}

	public void setUndistributed(String undistributed) {
		this.undistributed = undistributed;
	}
	
	@Basic
	@Column(name = "portNum")
	public String getPortNum() {
		return portNum;
	}

	public void setPortNum(String portNum) {
		this.portNum = portNum;
	}
	
	@Basic
	@Column(name = "cpu")
	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	
	@Basic
	@Column(name = "fan")
	public String getFan() {
		return fan;
	}

	public void setFan(String fan) {
		this.fan = fan;
	}
	
	@Basic
	@Column(name = "bad")
	public String getBad() {
		return bad;
	}

	public void setBad(String bad) {
		this.bad = bad;
	}
	
	@Basic
	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Basic
	@Column(name = "recordTime")
	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DiskStateRecord server = (DiskStateRecord) o;

		if (id != null ? !id.equals(server.id) : server.id != null) return false;
		if (diskid != null ? !diskid.equals(server.diskid) : server.diskid != null) return false;
		if (totalvolume != null ? !totalvolume.equals(server.totalvolume) : server.totalvolume != null) return false;
		if (undistributed != null ? !undistributed.equals(server.undistributed) : server.undistributed != null) return false;
		if (portNum != null ? !portNum.equals(server.portNum) : server.portNum != null) return false;
		if (cpu != null ? !cpu.equals(server.cpu) : server.cpu != null) return false;
		if (fan != null ? !fan.equals(server.fan) : server.fan != null) return false;
		if (bad != null ? !bad.equals(server.bad) : server.bad != null) return false;
		if (state != null ? !state.equals(server.state) : server.state != null)	return false;
		if (recordTime != null ? !recordTime.equals(server.recordTime) : server.recordTime != null) return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		int result =  id != null ? id.hashCode() : 0 ;
		result = 31 * result + (diskid != null ? diskid.hashCode() : 0);
		result = 31 * result + (totalvolume != null ? totalvolume.hashCode() : 0);
		result = 31 * result + (undistributed != null ? undistributed.hashCode() : 0);
		result = 31 * result + (portNum != null ? portNum.hashCode() : 0);
		result = 31 * result + (cpu != null ? cpu.hashCode() : 0);
		result = 31 * result + (fan != null ? fan.hashCode() : 0);
		result = 31 * result + (bad != null ? bad.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (recordTime != null ? recordTime.hashCode() : 0);
		return result;
	}
}
