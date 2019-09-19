package com.socket.sip.bean;

public class SIPResponseHead extends SIPHead{	
	
	
	public SIPResponseHead(){
		super();
	}
	
	@Override
	public  String toString(){
		StringBuffer str = new StringBuffer();
		str.append("SIP/2.0 200 OK").append("\n").append("Via: SIP/2.0/TCP ").append(this.getVia())
			.append("To:").append(this.getTo()).append("From:").append(this.getFrom())
			.append("Call-ID:").append(this.getCall_id()).append("CSeq:").append(this.getCseq())
			.append("Content-Type:").append(this.getContent_type())
			.append("Content-Length:").append(this.getContent_length()).append("\r\n\r\n");
		return str.toString();
	}
	
}
