package com.beyeon.hibernate.fivsdb;

import java.util.Arrays;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "preset_jpg")
public class PresetJpg {
	private String channelId;
	private Integer num;
	private Integer dataLen;
	private byte[] dataBuffer;

	@Id
	@Column(name = "channel_id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Basic
	@Column(name = "num")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Basic
	@Column(name = "data_len")
	public Integer getDataLen() {
		return dataLen;
	}

	public void setDataLen(Integer dataLen) {
		this.dataLen = dataLen;
	}

	@Basic
	@Column(name = "data_buffer")
	public byte[] getDataBuffer() {
		return dataBuffer;
	}

	public void setDataBuffer(byte[] dataBuffer) {
		this.dataBuffer = dataBuffer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PresetJpg presetJpg = (PresetJpg) o;

		if (channelId != null ? !channelId.equals(presetJpg.channelId) : presetJpg.channelId != null) return false;
		if (num != null ? !num.equals(presetJpg.num) : presetJpg.num != null) return false;
		if (dataLen != null ? !dataLen.equals(presetJpg.dataLen) : presetJpg.dataLen != null) return false;
		if (!Arrays.equals(dataBuffer, presetJpg.dataBuffer)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (num != null ? num.hashCode() : 0);
		result = 31 * result + (dataLen != null ? dataLen.hashCode() : 0);
		result = 31 * result + (dataBuffer != null ? Arrays.hashCode(dataBuffer) : 0);
		return result;
	}
}
