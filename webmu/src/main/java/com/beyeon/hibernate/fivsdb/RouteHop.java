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
@Table(name = "route_hop")
public class RouteHop {
	private Integer routeId;
	private Integer hopNum;
	private String serverId;

	@Id
	@Column(name = "route_id")
	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	@Basic
	@Column(name = "hop_num")
	public Integer getHopNum() {
		return hopNum;
	}

	public void setHopNum(Integer hopNum) {
		this.hopNum = hopNum;
	}

	@Basic
	@Column(name = "server_id")
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RouteHop routeHop = (RouteHop) o;

		if (routeId != null ? !routeId.equals(routeHop.routeId) : routeHop.routeId != null) return false;
		if (hopNum != null ? !hopNum.equals(routeHop.hopNum) : routeHop.hopNum != null) return false;
		if (serverId != null ? !serverId.equals(routeHop.serverId) : routeHop.serverId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = routeId != null ? routeId.hashCode() : 0;
		result = 31 * result + (hopNum != null ? hopNum.hashCode() : 0);
		result = 31 * result + (serverId != null ? serverId.hashCode() : 0);
		return result;
	}
}
