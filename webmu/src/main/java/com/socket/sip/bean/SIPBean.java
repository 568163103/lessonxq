package com.socket.sip.bean;

import java.io.Serializable;
import java.util.Map;

public abstract class SIPBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SIPType type;
	private SIPHead head;
	private Map<String, Object> map;
	
	public SIPBean(){
		
	}
	public SIPHead getHead() {
		return head;
	}
	public void setHead(SIPHead head) {
		this.head = head;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public SIPType getType() {
		return type;
	}
	public void setType(SIPType type) {
		this.type = type;
	}
	
	
}
