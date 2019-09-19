package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "channel_param")
public class ChannelParam {
	private String channelId;
	private Integer channelType;
	private Integer sceneType;
	private Integer decoderAddress;
	private Integer ptzModel;
	private Integer ptzProtocol;
	private Integer transComType;
	private Integer baudrate;
	private Integer checkbit;
	private Integer databit;
	private Integer flowcontrol;
	private Integer stopbit;
	private String vaparam;
	private Integer interDeviceId;

	@Id
	@Column(name = "channel_id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Basic
	@Column(name = "channel_type")
	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	@Basic
	@Column(name = "scene_type")
	public Integer getSceneType() {
		return sceneType;
	}

	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}

	@Basic
	@Column(name = "decoder_address")
	public Integer getDecoderAddress() {
		return decoderAddress;
	}

	public void setDecoderAddress(Integer decoderAddress) {
		this.decoderAddress = decoderAddress;
	}

	@Basic
	@Column(name = "ptz_model")
	public Integer getPtzModel() {
		return ptzModel;
	}

	public void setPtzModel(Integer ptzModel) {
		this.ptzModel = ptzModel;
	}

	@Basic
	@Column(name = "ptz_protocol")
	public Integer getPtzProtocol() {
		return ptzProtocol;
	}

	public void setPtzProtocol(Integer ptzProtocol) {
		this.ptzProtocol = ptzProtocol;
	}

	@Basic
	@Column(name = "trans_com_type")
	public Integer getTransComType() {
		return transComType;
	}

	public void setTransComType(Integer transComType) {
		this.transComType = transComType;
	}

	@Basic
	@Column(name = "baudrate")
	public Integer getBaudrate() {
		return baudrate;
	}

	public void setBaudrate(Integer baudrate) {
		this.baudrate = baudrate;
	}

	@Basic
	@Column(name = "checkbit")
	public Integer getCheckbit() {
		return checkbit;
	}

	public void setCheckbit(Integer checkbit) {
		this.checkbit = checkbit;
	}

	@Basic
	@Column(name = "databit")
	public Integer getDatabit() {
		return databit;
	}

	public void setDatabit(Integer databit) {
		this.databit = databit;
	}

	@Basic
	@Column(name = "flowcontrol")
	public Integer getFlowcontrol() {
		return flowcontrol;
	}

	public void setFlowcontrol(Integer flowcontrol) {
		this.flowcontrol = flowcontrol;
	}

	@Basic
	@Column(name = "stopbit")
	public Integer getStopbit() {
		return stopbit;
	}

	public void setStopbit(Integer stopbit) {
		this.stopbit = stopbit;
	}

	@Basic
	@Column(name = "vaparam")
	public String getVaparam() {
		return vaparam;
	}

	public void setVaparam(String vaparam) {
		this.vaparam = vaparam;
	}

	@Basic
	@Column(name = "inter_device_id")
	public Integer getInterDeviceId() {
		return interDeviceId;
	}

	public void setInterDeviceId(Integer interDeviceId) {
		this.interDeviceId = interDeviceId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ChannelParam that = (ChannelParam) o;

		if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
		if (channelType != null ? !channelType.equals(that.channelType) : that.channelType != null) return false;
		if (sceneType != null ? !sceneType.equals(that.sceneType) : that.sceneType != null) return false;
		if (decoderAddress != null ? !decoderAddress.equals(that.decoderAddress) : that.decoderAddress != null)
			return false;
		if (ptzModel != null ? !ptzModel.equals(that.ptzModel) : that.ptzModel != null) return false;
		if (ptzProtocol != null ? !ptzProtocol.equals(that.ptzProtocol) : that.ptzProtocol != null) return false;
		if (transComType != null ? !transComType.equals(that.transComType) : that.transComType != null) return false;
		if (baudrate != null ? !baudrate.equals(that.baudrate) : that.baudrate != null) return false;
		if (checkbit != null ? !checkbit.equals(that.checkbit) : that.checkbit != null) return false;
		if (databit != null ? !databit.equals(that.databit) : that.databit != null) return false;
		if (flowcontrol != null ? !flowcontrol.equals(that.flowcontrol) : that.flowcontrol != null) return false;
		if (stopbit != null ? !stopbit.equals(that.stopbit) : that.stopbit != null) return false;
		if (vaparam != null ? !vaparam.equals(that.vaparam) : that.vaparam != null) return false;
		if (interDeviceId != null ? !interDeviceId.equals(that.interDeviceId) : that.interDeviceId != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (channelType != null ? channelType.hashCode() : 0);
		result = 31 * result + (sceneType != null ? sceneType.hashCode() : 0);
		result = 31 * result + (decoderAddress != null ? decoderAddress.hashCode() : 0);
		result = 31 * result + (ptzModel != null ? ptzModel.hashCode() : 0);
		result = 31 * result + (ptzProtocol != null ? ptzProtocol.hashCode() : 0);
		result = 31 * result + (transComType != null ? transComType.hashCode() : 0);
		result = 31 * result + (baudrate != null ? baudrate.hashCode() : 0);
		result = 31 * result + (checkbit != null ? checkbit.hashCode() : 0);
		result = 31 * result + (databit != null ? databit.hashCode() : 0);
		result = 31 * result + (flowcontrol != null ? flowcontrol.hashCode() : 0);
		result = 31 * result + (stopbit != null ? stopbit.hashCode() : 0);
		result = 31 * result + (vaparam != null ? vaparam.hashCode() : 0);
		result = 31 * result + (interDeviceId != null ? interDeviceId.hashCode() : 0);
		return result;
	}
}
