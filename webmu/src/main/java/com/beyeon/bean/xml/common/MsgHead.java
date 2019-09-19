package com.beyeon.bean.xml.common;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: message head
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author liubaohua
 * @version 1.0
 */

/*
 * message head
 */
public class MsgHead {

	private short version;
	private short cmdLen;
	private int sequence;

	public MsgHead(short version, short cmdLen, int sequence) {
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
