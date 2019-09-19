package com.beyeon.hibernate.fivsdb;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.beyeon.common.web.model.entity.BaseEntity;


/**
 * The persistent class for the channel_snapshot database table.
 * 
 */
@Entity
@Table(name="channel_snapshot")
public class ChannelSnapshot extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="create_time")
	private String createTime;

	@Lob
	private String remark;

	@Column(name="event_type")
	private String eventType;

	@Column(name="ot_id")
	private String otId;

	@Column(name="res_id")
	private String resId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="upload_time")
	private Date uploadTime;

	private String url;

	@Transient
	private String beginTime;

	@Transient
	private String endTime;

	@Transient
	private String isNew;

	@Transient
	private String encoderName;

	public ChannelSnapshot() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEventType() {
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getOtId() {
		return this.otId;
	}

	public void setOtId(String otId) {
		this.otId = otId;
	}

	public String getResId() {
		return this.resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getName(){
		return Channel.getObjectName(resId);
	}

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		if (null != status) {
			this.status = status;
		}
	}

	public Boolean getPlatStatus() {
		return this.status;
	}

	public void setPlatStatus(Boolean platStatus) {
		if (null != platStatus) {
			this.status = platStatus;
		}
	}

	public String getEncoderName() {
		return encoderName;
	}

	public void setEncoderName(String encoderName) {
		this.encoderName = encoderName;
	}
}