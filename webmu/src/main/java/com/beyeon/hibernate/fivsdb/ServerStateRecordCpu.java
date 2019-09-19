package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "server_state_record_cpu")
@GenericGenerator(name="genID", strategy="increment")
public class ServerStateRecordCpu extends BaseEntity{
	private Integer id;
	private Integer recordid;
	private String cpuPercent;
	private String cpuTemp;	

	public ServerStateRecordCpu (){
		
	}
	
	public ServerStateRecordCpu (ServerStateRecordCpu cpu){
		this.cpuPercent = cpu.getCpuPercent();
		this.cpuTemp = cpu.getCpuTemp();
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
	@Column(name = "recordid")
	public Integer getRecordid() {
		return recordid;
	}

	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}
	
	@Basic
	@Column(name = "cpuPercent")
	public String getCpuPercent() {
		return cpuPercent;
	}

	public void setCpuPercent(String cpuPercent) {
		this.cpuPercent = cpuPercent;
	}
	@Basic
	@Column(name = "cpuTemp")
	public String getCpuTemp() {
		return cpuTemp;
	}

	public void setCpuTemp(String cpuTemp) {
		this.cpuTemp = cpuTemp;
	}
	
	

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ServerStateRecordCpu server = (ServerStateRecordCpu) o;

		if (id != null ? !id.equals(server.id) : server.id != null) return false;
		if (recordid != null ? !recordid.equals(server.recordid) : server.recordid != null) return false;
		if (cpuPercent != null ? !cpuPercent.equals(server.cpuPercent) : server.cpuPercent != null) return false;
		if (cpuTemp != null ? !cpuTemp.equals(server.cpuTemp) : server.cpuTemp != null) return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		int result =  id != null ? id.hashCode() : 0 ;
		result = 31 * result + (recordid != null ? recordid.hashCode() : 0);
		result = 31 * result + (cpuPercent != null ? cpuPercent.hashCode() : 0);
		result = 31 * result + (cpuTemp != null ? cpuTemp.hashCode() : 0);
		return result;
	}
}
