package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
public class ResDecoderEncoderPK implements Serializable {
	private String decoderChannelId;
	private String encoderChannelId;

	@Column(name = "decoder_channel_id")
	@Id
	public String getDecoderChannelId() {
		return decoderChannelId;
	}

	public void setDecoderChannelId(String decoderChannelId) {
		this.decoderChannelId = decoderChannelId;
	}

	@Column(name = "encoder_channel_id")
	@Id
	public String getEncoderChannelId() {
		return encoderChannelId;
	}

	public void setEncoderChannelId(String encoderChannelId) {
		this.encoderChannelId = encoderChannelId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ResDecoderEncoderPK that = (ResDecoderEncoderPK) o;

		if (decoderChannelId != null ? !decoderChannelId.equals(that.decoderChannelId) : that.decoderChannelId != null)
			return false;
		if (encoderChannelId != null ? !encoderChannelId.equals(that.encoderChannelId) : that.encoderChannelId != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = decoderChannelId != null ? decoderChannelId.hashCode() : 0;
		result = 31 * result + (encoderChannelId != null ? encoderChannelId.hashCode() : 0);
		return result;
	}
}
