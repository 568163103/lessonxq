package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "res_decoder_encoder")
@IdClass(ResDecoderEncoderPK.class)
public class ResDecoderEncoder {
	private String decoderChannelId;
	private String encoderChannelId;

	@Id
	@Column(name = "decoder_channel_id")
	public String getDecoderChannelId() {
		return decoderChannelId;
	}

	public void setDecoderChannelId(String decoderChannelId) {
		this.decoderChannelId = decoderChannelId;
	}

	@Id
	@Column(name = "encoder_channel_id")
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

		ResDecoderEncoder that = (ResDecoderEncoder) o;

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
