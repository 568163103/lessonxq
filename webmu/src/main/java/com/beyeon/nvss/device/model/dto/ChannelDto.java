package com.beyeon.nvss.device.model.dto;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Transient;

import com.beyeon.hibernate.fivsdb.Channel;
import com.beyeon.hibernate.fivsdb.ChannelRecordPlan;
import com.beyeon.hibernate.fivsdb.ImsGisInfo;
import com.beyeon.hibernate.fivsdb.MssChannel;
import com.beyeon.hibernate.fivsdb.Preset;

public class ChannelDto implements Serializable{
	private Channel channel ;
	private MssChannel msuChannel;
	private Preset preset;
	private ImsGisInfo imsGisInfo;
	private ChannelRecordPlan channelRecordPlan;
	private List<Preset> presets;
	private String ids;
	
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
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public ChannelDto() {
		super();
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public MssChannel getMsuChannel() {
		return msuChannel;
	}

	public void setMsuChannel(MssChannel msuChannel) {
		this.msuChannel = msuChannel;
	}

	public Preset getPreset() {
		return preset;
	}

	public void setPreset(Preset preset) {
		this.preset = preset;
	}

	public ImsGisInfo getImsGisInfo() {
		return imsGisInfo;
	}

	public void setImsGisInfo(ImsGisInfo imsGisInfo) {
		this.imsGisInfo = imsGisInfo;
	}

	public ChannelRecordPlan getChannelRecordPlan() {
		return channelRecordPlan;
	}

	public void setChannelRecordPlan(ChannelRecordPlan channelRecordPlan) {
		this.channelRecordPlan = channelRecordPlan;
	}

	public List<Preset> getPresets() {
		return presets;
	}

	public void setPresets(List<Preset> presets) {
		this.presets = presets;
	}
}
