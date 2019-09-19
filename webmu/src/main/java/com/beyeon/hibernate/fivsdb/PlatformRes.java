package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "platform_res")
public class PlatformRes extends BaseEntity{
	private String id;
	private String name;
	private String parentId;
	private String serverId;
	private Integer subnum;
	private Integer hasPtz;
	private Integer codec;
	private Double longitude;
	private Double latitude;
//	private Byte status;
	
	private String location;
	private String purpose;
	private String description = "";
	private String encoderName;
	
	private String position;	
	
	@Transient
	public String getPosition() {
		if (StringUtils.isBlank(position) && StringUtils.isNotBlank(id)){
			String pos = id.substring(0, 6);
			setPosition(pos);			
		}
		return position;
	}
	@Transient
	public String getPositionZH(){
		String pos = getPosition();
		return PositionCode.getTypeName(pos);
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "parent_id")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Basic
	@Column(name = "server_id")
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Basic
	@Column(name = "subnum")
	public Integer getSubnum() {
		return subnum;
	}

	public void setSubnum(Integer subnum) {
		this.subnum = subnum;
	}

	@Basic
	@Column(name = "has_ptz")
	public Integer getHasPtz() {
		return hasPtz;
	}

	public void setHasPtz(Integer hasPtz) {
		this.hasPtz = hasPtz;
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
	@Column(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Basic
	@Column(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Basic
	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@Basic
	@Column(name = "location")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Basic
	@Column(name = "purpose")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public String getEncoderName() {
		return encoderName;
	}

	public void setEncoderName(String encoderName) {
		this.encoderName = encoderName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PlatformRes that = (PlatformRes) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
		if (serverId != null ? !serverId.equals(that.serverId) : that.serverId != null) return false;
		if (subnum != null ? !subnum.equals(that.subnum) : that.subnum != null) return false;
		if (hasPtz != null ? !hasPtz.equals(that.hasPtz) : that.hasPtz != null) return false;
		if (codec != null ? !codec.equals(that.codec) : that.codec != null) return false;
		if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
		if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
		result = 31 * result + (serverId != null ? serverId.hashCode() : 0);
		result = 31 * result + (subnum != null ? subnum.hashCode() : 0);
		result = 31 * result + (hasPtz != null ? hasPtz.hashCode() : 0);
		result = 31 * result + (codec != null ? codec.hashCode() : 0);
		result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
		result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		return result;
	}
}
