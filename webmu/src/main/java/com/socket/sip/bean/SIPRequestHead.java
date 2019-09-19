package com.socket.sip.bean;

public class SIPRequestHead extends SIPHead {
	private String sip;
	private String max_forwards;
	
	public SIPRequestHead (){
		super();
	}
	
	public String getSip() {
		return sip;
	}
	public void setSip(String sip) {
		this.sip = sip;
	}
	
	
	public String getMax_forwards() {
		return max_forwards;
	}
	public void setMax_forwards(String max_forwards) {
		this.max_forwards = max_forwards;
	}
	@Override
	public  String toString(){
		StringBuffer str = new StringBuffer();
		str.append("INVITE").append(sip).append(" SIP/2.0").append("\n").append("Via: SIP/2.0/TCP ")
			.append(this.getVia()).append("To:").append(this.getTo()).append("From:").append(this.getFrom())
			.append("Max-Forwards:").append(max_forwards).append("Call-ID:").append(this.getCall_id())
			.append("CSeq:").append(this.getCseq()).append("Content-Type:").append(this.getContent_type())
			.append("Content-Length:").append(this.getContent_length()).append("\r\n\r\n");
		
		return str.toString();
	}
}
