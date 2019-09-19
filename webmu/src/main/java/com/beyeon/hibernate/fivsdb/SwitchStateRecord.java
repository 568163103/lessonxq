package com.beyeon.hibernate.fivsdb;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "switch_state_record")
@GenericGenerator(name="genID", strategy="increment")
public class SwitchStateRecord extends BaseEntity{
	private Integer id;
	private String switchid;
	private String memory;
	private String cpu;
	private String portNum;
	private String recordTime;
	private Set<SwitchStateRecordPort> port = new HashSet<SwitchStateRecordPort>();

	public SwitchStateRecord(){
		
	}
	
	public SwitchStateRecord (SwitchStateRecord record){
		this.switchid = record.getSwitchid();
		this.memory = record.getMemory();
		this.cpu = record.getCpu();
		this.portNum = record.getPortNum();
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
	@Column(name = "switchid")	
	public String getSwitchid() {
		return switchid;
	}

	public void setSwitchid(String switchid) {
		this.switchid = switchid;
	}
	@Basic
	@Column(name = "memory")	
	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
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
	@OneToMany   
	@Cascade(value={CascadeType.ALL}) 
	@JoinColumn(name="recordid")    
	public Set<SwitchStateRecordPort> getPort() {
		return port;
	}

	public void setPort(Set<SwitchStateRecordPort> port) {
		this.port = port;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpu == null) ? 0 : cpu.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((memory == null) ? 0 : memory.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((portNum == null) ? 0 : portNum.hashCode());
		result = prime * result + ((recordTime == null) ? 0 : recordTime.hashCode());
		result = prime * result + ((switchid == null) ? 0 : switchid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SwitchStateRecord other = (SwitchStateRecord) obj;
		if (cpu == null) {
			if (other.cpu != null)
				return false;
		} else if (!cpu.equals(other.cpu))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (memory == null) {
			if (other.memory != null)
				return false;
		} else if (!memory.equals(other.memory))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (portNum == null) {
			if (other.portNum != null)
				return false;
		} else if (!portNum.equals(other.portNum))
			return false;
		if (recordTime == null) {
			if (other.recordTime != null)
				return false;
		} else if (!recordTime.equals(other.recordTime))
			return false;
		if (switchid == null) {
			if (other.switchid != null)
				return false;
		} else if (!switchid.equals(other.switchid))
			return false;
		return true;
	}
	

	
	
}
