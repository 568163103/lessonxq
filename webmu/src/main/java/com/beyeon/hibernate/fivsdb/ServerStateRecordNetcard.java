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
@Table(name = "server_state_record_netcard")
@GenericGenerator(name="genID", strategy="increment")
public class ServerStateRecordNetcard extends BaseEntity{
	private Integer id;
	private Integer recordid;
	private String port;
	private String portState;	
	private String bandwidth;
	private String dataFlow;
	private String ip;
	private String mac;

	public ServerStateRecordNetcard(){}
	
	public ServerStateRecordNetcard(ServerStateRecordNetcard net){
		this.port = net.getPort();
		this.portState = net.getPortState();
		this.bandwidth = net.getBandwidth();
		this.dataFlow = net.getBandwidth();
		this.ip = net.getIp();
		this.mac = net.getMac();
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
	@Column(name = "port")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	@Basic
	@Column(name = "portState")
	public String getPortState() {
		return portState;
	}

	public void setPortState(String portState) {
		this.portState = portState;
	}
	@Basic
	@Column(name = "bandwidth")
	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	@Basic
	@Column(name = "dataFlow")
	public String getDataFlow() {
		return dataFlow;
	}

	public void setDataFlow(String dataFlow) {
		this.dataFlow = dataFlow;
	}
	@Basic
	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	@Basic
	@Column(name = "mac")
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bandwidth == null) ? 0 : bandwidth.hashCode());
		result = prime * result + ((dataFlow == null) ? 0 : dataFlow.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((mac == null) ? 0 : mac.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((portState == null) ? 0 : portState.hashCode());
		result = prime * result + ((recordid == null) ? 0 : recordid.hashCode());
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
		ServerStateRecordNetcard other = (ServerStateRecordNetcard) obj;
		if (bandwidth == null) {
			if (other.bandwidth != null)
				return false;
		} else if (!bandwidth.equals(other.bandwidth))
			return false;
		if (dataFlow == null) {
			if (other.dataFlow != null)
				return false;
		} else if (!dataFlow.equals(other.dataFlow))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (mac == null) {
			if (other.mac != null)
				return false;
		} else if (!mac.equals(other.mac))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (portState == null) {
			if (other.portState != null)
				return false;
		} else if (!portState.equals(other.portState))
			return false;
		if (recordid == null) {
			if (other.recordid != null)
				return false;
		} else if (!recordid.equals(other.recordid))
			return false;
		return true;
	}
	
	

	
	
	
	
	

	
}
