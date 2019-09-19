package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "encoder_state_record")
@GenericGenerator(name="genID", strategy="increment")
public class EncoderStateRecord extends BaseEntity{
	private Integer id;
	private String encoderid;
	private String type;
	private String codeType;
	private String streamType;
	private String mainStreamRateType;
	private String mainStreamResolution;
	private String mainStreamFrameRate;
	private String mainStreamGOP;
	private String childStreamResolution;
	private String recordTime;
	
	public EncoderStateRecord(){
		
	}
	
	public EncoderStateRecord(EncoderStateRecord record){
		this.encoderid = record.getEncoderid();
		this.type = record.getType();
		this.codeType = record.getCodeType();
		this.streamType = record.getStreamType();
		this.mainStreamRateType =record.getMainStreamRateType();
		this.mainStreamResolution = record.getMainStreamResolution();
		this.mainStreamFrameRate = record.getMainStreamFrameRate();
		this.mainStreamGOP = record.getMainStreamGOP();
		this.childStreamResolution = record.getChildStreamResolution();	
	}
	@Id
	@GeneratedValue(generator="genID")
	@Column(name = "id")
	public Integer  getId() {
		return id;
	}

	public void setId(Integer  id) {
		this.id = id;
	}
	
	@Basic
	@Column(name = "encoderid")
	public String getEncoderid() {
		return encoderid;
	}

	public void setEncoderid(String encoderid) {
		this.encoderid = encoderid;
	}
	@Basic
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Basic
	@Column(name = "codeType")
	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	@Basic
	@Column(name = "streamType")
	public String getStreamType() {
		return streamType;
	}

	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}
	@Basic
	@Column(name = "mainStreamRateType")
	public String getMainStreamRateType() {
		return mainStreamRateType;
	}

	public void setMainStreamRateType(String mainStreamRateType) {
		this.mainStreamRateType = mainStreamRateType;
	}
	@Basic
	@Column(name = "mainStreamResolution")
	public String getMainStreamResolution() {
		return mainStreamResolution;
	}

	public void setMainStreamResolution(String mainStreamResolution) {
		this.mainStreamResolution = mainStreamResolution;
	}
	@Basic
	@Column(name = "mainStreamFrameRate")
	public String getMainStreamFrameRate() {
		return mainStreamFrameRate;
	}

	public void setMainStreamFrameRate(String mainStreamFrameRate) {
		this.mainStreamFrameRate = mainStreamFrameRate;
	}
	@Basic
	@Column(name = "mainStreamGOP")
	public String getMainStreamGOP() {
		return mainStreamGOP;
	}

	public void setMainStreamGOP(String mainStreamGOP) {
		this.mainStreamGOP = mainStreamGOP;
	}
	@Basic
	@Column(name = "childStreamResolution")
	public String getChildStreamResolution() {
		return childStreamResolution;
	}

	public void setChildStreamResolution(String childStreamResolution) {
		this.childStreamResolution = childStreamResolution;
	}

	@Basic
	@Column(name = "recordTime")
	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EncoderStateRecord server = (EncoderStateRecord) o;

		if (id != null ? !id.equals(server.id) : server.id != null) return false;
		if (encoderid != null ? !encoderid.equals(server.encoderid) : server.encoderid != null) return false;
		if (type != null ? !type.equals(server.type) : server.type != null) return false;
		if (codeType != null ? !codeType.equals(server.codeType) : server.codeType != null) return false;
		if (streamType != null ? !streamType.equals(server.streamType) : server.streamType != null) return false;
		if (mainStreamRateType != null ? !mainStreamRateType.equals(server.mainStreamRateType) 
				: server.mainStreamRateType != null) return false;
		if (mainStreamResolution != null ? !mainStreamResolution.equals(server.mainStreamResolution)
				: server.mainStreamResolution != null) return false;
		if (mainStreamFrameRate != null ? !mainStreamFrameRate.equals(server.mainStreamFrameRate) 
				: server.mainStreamFrameRate != null) return false;
		if (mainStreamGOP != null ? !mainStreamGOP.equals(server.mainStreamGOP) 
				: server.mainStreamGOP != null)	return false;
		if (childStreamResolution != null ? !childStreamResolution.equals(server.childStreamResolution) 
				: server.childStreamResolution != null)	return false;
		if (recordTime != null ? !recordTime.equals(server.recordTime) : server.recordTime != null) return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		int result =  id != null ? id.hashCode() : 0 ;
		result = 31 * result + (encoderid != null ? encoderid.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (codeType != null ? codeType.hashCode() : 0);
		result = 31 * result + (streamType != null ? streamType.hashCode() : 0);
		result = 31 * result + (mainStreamRateType != null ? mainStreamRateType.hashCode() : 0);
		result = 31 * result + (mainStreamResolution != null ? mainStreamResolution.hashCode() : 0);
		result = 31 * result + (mainStreamFrameRate != null ? mainStreamFrameRate.hashCode() : 0);
		result = 31 * result + (mainStreamGOP != null ? mainStreamGOP.hashCode() : 0);
		result = 31 * result + (childStreamResolution != null ? childStreamResolution.hashCode() : 0);
		result = 31 * result + (recordTime != null ? recordTime.hashCode() : 0);
		return result;
	}
}
