package com.beyeon.hibernate.fivsdb;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "server")
public class Server extends BaseEntity{
	private static Map<String,String> typeMap = new HashMap<String,String>();
	private static Map<String,Map<String,String>> objectMap = new HashMap<String,Map<String,String>>();
	private String id;
	private String name;
	private Integer type;
	private String ip;
	private Integer port;
	private String username;
	private String password;
	private Integer maxConnection;
	private String address;
	private String description;
	private ServerExtra serverExtra;
	private String position;
	private Integer prohibited_status;

	public static String getTypeName(String id){
		return typeMap.get(id);
	}

	public static Map<String,String> getTypeMap(){
		return typeMap;
	}

	public static Map<String,String> getObjectMap(String type){
		Map<String,String> childServerMap = objectMap.get(type);
		if (null == childServerMap){
			childServerMap = new HashMap<String, String>();
			objectMap.put(type, childServerMap);
		}
		return childServerMap;
	}

	public static String getObjectName(String id){
		for (String t : typeMap.keySet()) {
			String serverName = getObjectName(t,id);
			if(StringUtils.isNotBlank(serverName)){
				return serverName;
			}
		}
		return "";
	}

	public static String getObjectName(String type, String id){
		if(null == objectMap.get(type)){
			return "";
		}
		return objectMap.get(type).get(id);
	}

	public static void setObjectMap(Map<String, Map<String, String>> currObjectMap){
		Map<String,Map<String,String>> oldObjectMap = objectMap;
		objectMap = currObjectMap;
		oldObjectMap.clear();
	}

	public static Map<String,String> getObjectMap(Map<String, Map<String, String>> currObejctMap, String type){
		Map<String,String> childServerMap = currObejctMap.get(type);
		if (null == childServerMap){
			childServerMap = new HashMap<String, String>();
			currObejctMap.put(type, childServerMap);
		}
		return childServerMap;
	}

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	@Transient
	public String getTypeName() {
		return typeMap.get(type.toString());
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Transient
	public String getPosition() {
		if (StringUtils.isBlank(position) && StringUtils.isNotBlank(id)){
			String pos = id.substring(0, 6);
			setPosition(pos);			
		}
		return position;
	}
	@Transient
	public String getPositionZH(){
		String pos = getPosition();
		return PositionCode.getTypeName(pos);
	}
	public void setPosition(String position) {
		this.position = position;
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
	@Column(name = "port")
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Basic
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Basic
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "max_connection")
	public Integer getMaxConnection() {
		return maxConnection;
	}

	public void setMaxConnection(Integer maxConnection) {
		this.maxConnection = maxConnection;
	}

	@Basic
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Basic
	@Column(name = "prohibited_status")
	public Integer getProhibited_status() {
		return prohibited_status;
	}

	public void setProhibited_status(Integer prohibited_status) {
		this.prohibited_status = prohibited_status;
	}

	@OneToOne(cascade=CascadeType.ALL,optional=true)
	@JoinColumn(name="id")
	public ServerExtra getServerExtra() {
		return serverExtra;
	}

	public void setServerExtra(ServerExtra serverExtra) {
		this.serverExtra = serverExtra;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Server server = (Server) o;

		if (id != null ? !id.equals(server.id) : server.id != null) return false;
		if (name != null ? !name.equals(server.name) : server.name != null) return false;
		if (type != null ? !type.equals(server.type) : server.type != null) return false;
		if (ip != null ? !ip.equals(server.ip) : server.ip != null) return false;
		if (port != null ? !port.equals(server.port) : server.port != null) return false;
		if (username != null ? !username.equals(server.username) : server.username != null) return false;
		if (password != null ? !password.equals(server.password) : server.password != null) return false;
		if (maxConnection != null ? !maxConnection.equals(server.maxConnection) : server.maxConnection != null)
			return false;
		if (address != null ? !address.equals(server.address) : server.address != null) return false;
		if (description != null ? !description.equals(server.description) : server.description != null) return false;
		if (status != null ? !status.equals(server.status) : server.status != null) return false;
		if (serverExtra != null ? !serverExtra.equals(server.serverExtra) : server.serverExtra != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (ip != null ? ip.hashCode() : 0);
		result = 31 * result + (port != null ? port.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (maxConnection != null ? maxConnection.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (serverExtra != null ? serverExtra.hashCode() : 0);
		return result;
	}	
	
}
