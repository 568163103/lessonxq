package com.socket.sip.bean;

import java.io.Serializable;

public abstract class SIPHead implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4006668387L;
	
	private String via;
	private String to;
	private String from ;
	private String call_id;
	private String cseq;
	private String content_type;
	private String content_length;
	
	public SIPHead(){
		
	}
	public SIPHead(String via,String to,String from ,String call_id,String cseq,String content_type,String content_length){
		this.via =via;
		this.to  = to;
		this.from = from ;
		this.call_id = call_id;
		this.cseq = cseq;
		this.content_length = content_length;
		this.content_type = content_type;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getCall_id() {
		return call_id;
	}
	public void setCall_id(String call_id) {
		this.call_id = call_id;
	}
	public String getCseq() {
		return cseq;
	}
	public void setCseq(String cseq) {
		this.cseq = cseq;
	}
	public String getContent_type() {
		return content_type;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	public String getContent_length() {
		return content_length;
	}
	public void setContent_length(String content_length) {
		this.content_length = content_length;
	}
	
}
