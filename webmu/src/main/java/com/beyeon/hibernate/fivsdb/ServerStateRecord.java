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
@Table(name = "server_state_record")
@GenericGenerator(name="genID", strategy="increment")
public class ServerStateRecord extends BaseEntity{
	private Integer id;
	private String serverid;
	private String systemInfo;
	private String diskNum;
	private String totalMem;
	private String usedMem;
	private String leftMem;
	private String cpuNum;
	private String networkCard;
	private String recordTime;
	private Set<ServerStateRecordCpu> cpu = new HashSet<ServerStateRecordCpu>();
	private Set<ServerStateRecordDisk> disk = new HashSet<ServerStateRecordDisk>();
	private Set<ServerStateRecordNetcard> netcard = new HashSet<ServerStateRecordNetcard>();
	
	public ServerStateRecord (){}
	
	public ServerStateRecord(ServerStateRecord record ){
		this.serverid = record.getServerid();
		this.systemInfo = record.getSystemInfo();
		this.diskNum = record.getDiskNum();
		this.totalMem = record.getTotalMem();
		this.usedMem = record.getUsedMem();
		this.leftMem = record.getLeftMem();
		this.cpuNum = record.getCpuNum();
		this.networkCard = record.getNetworkCard();
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
	@Column(name = "serverid")
	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	@Basic
	@Column(name = "systemInfo")
	public String getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(String systemInfo) {
		this.systemInfo = systemInfo;
	}
	@Basic
	@Column(name = "diskNum")
	public String getDiskNum() {
		return diskNum;
	}

	public void setDiskNum(String diskNum) {
		this.diskNum = diskNum;
	}
	@Basic
	@Column(name = "totalMem")
	public String getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(String totalMem) {
		this.totalMem = totalMem;
	}
	@Basic
	@Column(name = "usedMem")
	public String getUsedMem() {
		return usedMem;
	}

	public void setUsedMem(String usedMem) {
		this.usedMem = usedMem;
	}
	@Basic
	@Column(name = "leftMem")
	public String getLeftMem() {
		return leftMem;
	}

	public void setLeftMem(String leftMem) {
		this.leftMem = leftMem;
	}
	@Basic
	@Column(name = "cpuNum")
	public String getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}
	@Basic
	@Column(name = "networkCard")
	public String getNetworkCard() {
		return networkCard;
	}

	public void setNetworkCard(String networkCard) {
		this.networkCard = networkCard;
	}

	@Basic
	@Column(name = "recordTime")
	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	
	@OneToMany   
	@Cascade(value={CascadeType.ALL}) 
	@JoinColumn(name="recordid")     
	public Set<ServerStateRecordCpu> getCpu() {
		return cpu;
	}

	public void setCpu(Set<ServerStateRecordCpu> cpu) {
		this.cpu = cpu;
	}
	@OneToMany   
	@Cascade(value={CascadeType.ALL}) 
	@JoinColumn(name="recordid")  
	public Set<ServerStateRecordDisk> getDisk() {
		return disk;
	}

	public void setDisk(Set<ServerStateRecordDisk> disk) {
		this.disk = disk;
	}
	@OneToMany   
	@Cascade(value={CascadeType.ALL}) 
	@JoinColumn(name="recordid")  
	public Set<ServerStateRecordNetcard> getNetcard() {
		return netcard;
	}

	public void setNetcard(Set<ServerStateRecordNetcard> netcard) {
		this.netcard = netcard;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpu == null) ? 0 : cpu.hashCode());
		result = prime * result + ((cpuNum == null) ? 0 : cpuNum.hashCode());
		result = prime * result + ((disk == null) ? 0 : disk.hashCode());
		result = prime * result + ((diskNum == null) ? 0 : diskNum.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((leftMem == null) ? 0 : leftMem.hashCode());
		result = prime * result + ((netcard == null) ? 0 : netcard.hashCode());
		result = prime * result + ((networkCard == null) ? 0 : networkCard.hashCode());
		result = prime * result + ((recordTime == null) ? 0 : recordTime.hashCode());
		result = prime * result + ((serverid == null) ? 0 : serverid.hashCode());
		result = prime * result + ((systemInfo == null) ? 0 : systemInfo.hashCode());
		result = prime * result + ((totalMem == null) ? 0 : totalMem.hashCode());
		result = prime * result + ((usedMem == null) ? 0 : usedMem.hashCode());
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
		ServerStateRecord other = (ServerStateRecord) obj;
		if (cpu == null) {
			if (other.cpu != null)
				return false;
		} else if (!cpu.equals(other.cpu))
			return false;
		if (cpuNum == null) {
			if (other.cpuNum != null)
				return false;
		} else if (!cpuNum.equals(other.cpuNum))
			return false;
		if (disk == null) {
			if (other.disk != null)
				return false;
		} else if (!disk.equals(other.disk))
			return false;
		if (diskNum == null) {
			if (other.diskNum != null)
				return false;
		} else if (!diskNum.equals(other.diskNum))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (leftMem == null) {
			if (other.leftMem != null)
				return false;
		} else if (!leftMem.equals(other.leftMem))
			return false;
		if (netcard == null) {
			if (other.netcard != null)
				return false;
		} else if (!netcard.equals(other.netcard))
			return false;
		if (networkCard == null) {
			if (other.networkCard != null)
				return false;
		} else if (!networkCard.equals(other.networkCard))
			return false;
		if (recordTime == null) {
			if (other.recordTime != null)
				return false;
		} else if (!recordTime.equals(other.recordTime))
			return false;
		if (serverid == null) {
			if (other.serverid != null)
				return false;
		} else if (!serverid.equals(other.serverid))
			return false;
		if (systemInfo == null) {
			if (other.systemInfo != null)
				return false;
		} else if (!systemInfo.equals(other.systemInfo))
			return false;
		if (totalMem == null) {
			if (other.totalMem != null)
				return false;
		} else if (!totalMem.equals(other.totalMem))
			return false;
		if (usedMem == null) {
			if (other.usedMem != null)
				return false;
		} else if (!usedMem.equals(other.usedMem))
			return false;
		return true;
	}

	
}
