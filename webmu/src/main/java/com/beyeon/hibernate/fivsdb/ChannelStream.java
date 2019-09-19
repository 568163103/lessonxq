package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "channel_stream")
@IdClass(ChannelStreamPK.class)
public class ChannelStream {
	private String channelId;
	private Integer num;
	private Integer codec;
	private Integer width;
	private Integer height;
	private Double fps;
	private Integer bps;
	private Integer iframe;
	private Integer audioCodec;
	private Integer audioChannel;
	private Integer audioSample;

	@Id
	@Column(name = "channel_id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Id
	@Column(name = "num")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Basic
	@Column(name = "codec")
	public Integer getCodec() {
		return codec;
	}

	public void setCodec(Integer codec) {
		this.codec = codec;
	}

	@Basic
	@Column(name = "width")
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Basic
	@Column(name = "height")
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Basic
	@Column(name = "fps")
	public Double getFps() {
		return fps;
	}

	public void setFps(Double fps) {
		this.fps = fps;
	}

	@Basic
	@Column(name = "bps")
	public Integer getBps() {
		return bps;
	}

	public void setBps(Integer bps) {
		this.bps = bps;
	}

	@Basic
	@Column(name = "iframe")
	public Integer getIframe() {
		return iframe;
	}

	public void setIframe(Integer iframe) {
		this.iframe = iframe;
	}

	@Basic
	@Column(name = "audio_codec")
	public Integer getAudioCodec() {
		return audioCodec;
	}

	public void setAudioCodec(Integer audioCodec) {
		this.audioCodec = audioCodec;
	}

	@Basic
	@Column(name = "audio_channel")
	public Integer getAudioChannel() {
		return audioChannel;
	}

	public void setAudioChannel(Integer audioChannel) {
		this.audioChannel = audioChannel;
	}

	@Basic
	@Column(name = "audio_sample")
	public Integer getAudioSample() {
		return audioSample;
	}

	public void setAudioSample(Integer audioSample) {
		this.audioSample = audioSample;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ChannelStream that = (ChannelStream) o;

		if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
		if (num != null ? !num.equals(that.num) : that.num != null) return false;
		if (codec != null ? !codec.equals(that.codec) : that.codec != null) return false;
		if (width != null ? !width.equals(that.width) : that.width != null) return false;
		if (height != null ? !height.equals(that.height) : that.height != null) return false;
		if (fps != null ? !fps.equals(that.fps) : that.fps != null) return false;
		if (bps != null ? !bps.equals(that.bps) : that.bps != null) return false;
		if (iframe != null ? !iframe.equals(that.iframe) : that.iframe != null) return false;
		if (audioCodec != null ? !audioCodec.equals(that.audioCodec) : that.audioCodec != null) return false;
		if (audioChannel != null ? !audioChannel.equals(that.audioChannel) : that.audioChannel != null) return false;
		if (audioSample != null ? !audioSample.equals(that.audioSample) : that.audioSample != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (num != null ? num.hashCode() : 0);
		result = 31 * result + (codec != null ? codec.hashCode() : 0);
		result = 31 * result + (width != null ? width.hashCode() : 0);
		result = 31 * result + (height != null ? height.hashCode() : 0);
		result = 31 * result + (fps != null ? fps.hashCode() : 0);
		result = 31 * result + (bps != null ? bps.hashCode() : 0);
		result = 31 * result + (iframe != null ? iframe.hashCode() : 0);
		result = 31 * result + (audioCodec != null ? audioCodec.hashCode() : 0);
		result = 31 * result + (audioChannel != null ? audioChannel.hashCode() : 0);
		result = 31 * result + (audioSample != null ? audioSample.hashCode() : 0);
		return result;
	}
}
