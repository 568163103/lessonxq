package com.beyeon.nvss.device.model.dto;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.hibernate.fivsdb.PositionCode;

/**
 * User: Administrator
 * Date: 2015/11/3
 * Time: 15:29
 */
public class EncoderImpDto {
	String encoderNo;
	String encoderName;
	String model;
	String username;
	String password;
	String ip = "10.255.0.1";
	Integer port = 0;
	int outputCount;
	String description;
	String channelType;
	String channelName;
	int indexCount;
	String mduId;
	String msuId;
	String position;
	
	public String getPosition() {
		if (StringUtils.isBlank(position) && StringUtils.isNotBlank(encoderNo)){
			String pos = encoderNo.substring(0, 6);
			setPosition(pos);			
		}
		return position;
	}
	public String getPositionZH(){
		String pos = getPosition();
		return PositionCode.getTypeName(pos);
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getEncoderNo() {
		if (null == encoderNo){
			return encoderName;
		}
		return encoderNo;
	}

	public void setEncoderNo(Object encoderNo) {
		if (null != encoderNo)
			this.encoderNo = encoderNo.toString();
	}

	public String getEncoderName() {
		return encoderName;
	}

	public void setEncoderName(String encoderName) {
		this.encoderName = encoderName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public int getOutputCount() {
		return outputCount;
	}

	public void setOutputCount(int outputCount) {
		this.outputCount = outputCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public int getIndexCount() {
		return indexCount;
	}

	public void setIndexCount(int indexCount) {
		this.indexCount = indexCount;
	}

	public String getMduId() {
		return mduId;
	}

	public void setMduId(String mduId) {
		this.mduId = mduId;
	}

	public String getMsuId() {
		return msuId;
	}

	public void setMsuId(String msuId) {
		this.msuId = msuId;
	}
	
	
	
}
