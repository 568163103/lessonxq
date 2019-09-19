package com.beyeon.bean.xml.common;

/**
 * 
 *    
 * 项目名称：mu   
 * 类名称：MsgHeadBean   
 * 类描述：协议头Bean
 * 创建人：gm   
 * 日期：2015年4月3日 下午3:30:31   
 * 版本：V2.9.4
 *
 */
public class MsgHeadBean {
	
	private short version;
	private short cmdLen;
	private int sequence;

	public MsgHeadBean() {
	
	}
	
	public MsgHeadBean(short version, short cmdLen, int sequence) {
		this.version = version;
		this.cmdLen = cmdLen;
		this.sequence = sequence;
	}

	public short getVersion() {
		return version;
	}

	public void setVersion(short version) {
		this.version = version;
	}

	public short getCmdLen() {
		return cmdLen;
	}

	public void setCmdLen(short cmdLen) {
		this.cmdLen = cmdLen;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
