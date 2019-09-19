package com.socket.sip.bean;

import java.net.Socket;
import java.nio.channels.SelectionKey;

public class SIPSessionBean {
	private Integer keepAlivePeriod;
	private long lastLoginTime;
	private String username;
	private String password;
	private String ip;
	private String port;
	private String version;
	private String id;
	private SelectionKey key;
	private Socket socket;
	
	public Integer getKeepAlivePeriod() {
		return keepAlivePeriod;
	}
	public void setKeepAlivePeriod(Integer keepAlivePeriod) {
		this.keepAlivePeriod = keepAlivePeriod;
	}
	public long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SelectionKey getKey() {
		return key;
	}
	public void setKey(SelectionKey key) {
		this.key = key;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
}
