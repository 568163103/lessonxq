package com.beyeon.hibernate.fivsdb;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.beyeon.nvss.common.model.dto.CmuResultDto;

/**
 * User: Administrator
 * Date: 2015/12/31
 * Time: 16:27
 */
/*@Entity
@Table(name = "server_status")*/
public class ServerStatus extends CmuResultDto {
	private String serverId;
	private String cpu;
	private String cpuTotal;
	private String memory;
	private String memoryFree;
	private String memoryTotal;
	private String networkIn;
	private String networkOut;
	private String onlineClient;
	private String onlineServer;
	private List<Map> diskItemList;
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());

	@Id
	@Basic
	@Column(name = "server_id")
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
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
	@Column(name = "cpu_total")
	public String getCpuTotal() {
		return cpuTotal;
	}

	public void setCpuTotal(String cpuTotal) {
		this.cpuTotal = cpuTotal;
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
	@Column(name = "memory_free")
	public String getMemoryFree() {
		return memoryFree;
	}

	public void setMemoryFree(String memoryFree) {
		this.memoryFree = memoryFree;
	}

	@Basic
	@Column(name = "memory_total")
	public String getMemoryTotal() {
		return memoryTotal;
	}

	public void setMemoryTotal(String memoryTotal) {
		this.memoryTotal = memoryTotal;
	}

	@Basic
	@Column(name = "network_in")
	public String getNetworkIn() {
		return networkIn;
	}

	public void setNetworkIn(String networkIn) {
		this.networkIn = networkIn;
	}

	@Basic
	@Column(name = "network_out")
	public String getNetworkOut() {
		return networkOut;
	}

	public void setNetworkOut(String networkOut) {
		this.networkOut = networkOut;
	}

	@Basic
	@Column(name = "online_client")
	public String getOnlineClient() {
		return onlineClient;
	}

	public void setOnlineClient(String onlineClient) {
		this.onlineClient = onlineClient;
	}

	@Basic
	@Column(name = "online_server")
	public String getOnlineServer() {
		return onlineServer;
	}

	public void setOnlineServer(String onlineServer) {
		this.onlineServer = onlineServer;
	}

	@Basic
	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Transient
	public List getDiskItemList() {
		return diskItemList;
	}

	public void setDiskItemList(List diskItemList) {
		this.diskItemList = diskItemList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ServerStatus that = (ServerStatus) o;

		if (serverId != null ? !serverId.equals(that.serverId) : that.serverId != null) return false;
		if (cpu != null ? !cpu.equals(that.cpu) : that.cpu != null) return false;
		if (cpuTotal != null ? !cpuTotal.equals(that.cpuTotal) : that.cpuTotal != null) return false;
		if (memory != null ? !memory.equals(that.memory) : that.memory != null) return false;
		if (memoryFree != null ? !memoryFree.equals(that.memoryFree) : that.memoryFree != null) return false;
		if (memoryTotal != null ? !memoryTotal.equals(that.memoryTotal) : that.memoryTotal != null) return false;
		if (networkIn != null ? !networkIn.equals(that.networkIn) : that.networkIn != null) return false;
		if (networkOut != null ? !networkOut.equals(that.networkOut) : that.networkOut != null) return false;
		if (onlineClient != null ? !onlineClient.equals(that.onlineClient) : that.onlineClient != null) return false;
		if (onlineServer != null ? !onlineServer.equals(that.onlineServer) : that.onlineServer != null) return false;
		if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = serverId != null ? serverId.hashCode() : 0;
		result = 31 * result + (cpu != null ? cpu.hashCode() : 0);
		result = 31 * result + (cpuTotal != null ? cpuTotal.hashCode() : 0);
		result = 31 * result + (memory != null ? memory.hashCode() : 0);
		result = 31 * result + (memoryFree != null ? memoryFree.hashCode() : 0);
		result = 31 * result + (memoryTotal != null ? memoryTotal.hashCode() : 0);
		result = 31 * result + (networkIn != null ? networkIn.hashCode() : 0);
		result = 31 * result + (networkOut != null ? networkOut.hashCode() : 0);
		result = 31 * result + (onlineClient != null ? onlineClient.hashCode() : 0);
		result = 31 * result + (onlineServer != null ? onlineServer.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		return result;
	}
}
