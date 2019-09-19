package com.beyeon.bean.xml.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.beyeon.common.util.NumberUtil;


@XmlAccessorType(XmlAccessType.FIELD) 
public class BaseMsg {

	@XmlTransient
	private MsgHead head;
	@XmlTransient
	private byte[] xmlBody = null;
	
	public BaseMsg() {
		this.head = new MsgHead((short) 0x100, (short) 0, (int) System
				.currentTimeMillis()+(++NumberUtil.seqNum));
		this.xmlBody = new byte[0];

	}

	public MsgHead getMsgHead() {
		return head;
	}

	public void setMsgHead(MsgHead head) {
		this.head = head;
	}

	public byte[] getXmlBody() {
		return xmlBody;
	}

	public void setXmlBody(byte[] bytebody) {
		this.xmlBody = bytebody;
		this.head.setCmdLen((short)bytebody.length);
	}
}
