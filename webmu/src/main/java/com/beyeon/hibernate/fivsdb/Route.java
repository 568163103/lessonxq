package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "route")
public class Route {
	private Integer routeId;
	private String terminalId;
	private String serverId;
	private Integer hop;

	@Id
	@Column(name = "route_id")
	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	@Basic
	@Column(name = "terminal_id")
	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	@Basic
	@Column(name = "server_id")
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Basic
	@Column(name = "hop")
	public Integer getHop() {
		return hop;
	}

	public void setHop(Integer hop) {
		this.hop = hop;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Route route = (Route) o;

		if (routeId != null ? !routeId.equals(route.routeId) : route.routeId != null) return false;
		if (terminalId != null ? !terminalId.equals(route.terminalId) : route.terminalId != null) return false;
		if (serverId != null ? !serverId.equals(route.serverId) : route.serverId != null) return false;
		if (hop != null ? !hop.equals(route.hop) : route.hop != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = routeId != null ? routeId.hashCode() : 0;
		result = 31 * result + (terminalId != null ? terminalId.hashCode() : 0);
		result = 31 * result + (serverId != null ? serverId.hashCode() : 0);
		result = 31 * result + (hop != null ? hop.hashCode() : 0);
		return result;
	}
}
