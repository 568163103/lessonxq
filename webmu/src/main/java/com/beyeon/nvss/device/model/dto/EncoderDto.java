package com.beyeon.nvss.device.model.dto;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.beyeon.hibernate.fivsdb.*;

public class EncoderDto implements Serializable{
	private Encoder encoder;
	private ServerEncoder serverEncoder;
	private EncoderExtra encoderExtra;
	private Groups groups;
	private List<GroupResource> groupsResources = new ArrayList<GroupResource>();
	private List<Channel> channels = new ArrayList<Channel>();
	private MssChannel msuChannel;
	
	/** 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致*/
	private InputStream inputStream;
	/** 这个名称就是用来传给上面struts.xml中的${fileName}的*/
	private String fileName; 

	@Transient
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Transient
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public EncoderDto() {
		super();
	}

	public Encoder getEncoder() {
		return encoder;
	}

	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
	}

	public ServerEncoder getServerEncoder() {
		return serverEncoder;
	}

	public void setServerEncoder(ServerEncoder serverEncoder) {
		this.serverEncoder = serverEncoder;
	}

	
	public EncoderExtra getEncoderExtra() {
		return encoderExtra;
	}

	public void setEncoderExtra(EncoderExtra encoderExtra) {
		this.encoderExtra = encoderExtra;
	}

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public List<GroupResource> getGroupsResources() {
		return groupsResources;
	}

	public void setGroupsResources(List<GroupResource> groupsResources) {
		this.groupsResources = groupsResources;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public MssChannel getMsuChannel() {
		return msuChannel;
	}

	public void setMsuChannel(MssChannel msuChannel) {
		this.msuChannel = msuChannel;
	}
}
