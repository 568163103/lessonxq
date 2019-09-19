package com.beyeon.hibernate.fivsdb;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "channel")
public class Channel extends BaseEntity {
	private static Map<String,String> objectMap = new HashMap<String,String>();

	public static String getObjectName(String id){
		return objectMap.get(id);
	}

	public static Map<String,String> getObjectMap(){
		return objectMap;
	}

	public static void setObjectMap(Map<String, String> currObjectMap){
		Map<String,String> oldObjectMap = objectMap;
		objectMap = currObjectMap;
		oldObjectMap.clear();
	}

	private String id;
	private String name;
	private String encoderId;
	private Integer num;
	private Integer hasPtz = 0;
	private Integer hasAudio = 0;
	private Integer streamCount = 0;
	private String description = "";

	private String serverId;
	private String recordPlanName;
	private String encoderName;

	private String cameraType;

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
	public Channel() {
	}

	public Channel(String id, String name, String encoderId, Integer num) {
		this.id = id;
		this.name = name;
		this.encoderId = encoderId;
		this.num = num;
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
	@Column(name = "enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Basic
	@Column(name = "encoder_id")
	public String getEncoderId() {
		return encoderId;
	}

	public void setEncoderId(String encoderId) {
		this.encoderId = encoderId;
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
	@Column(name = "has_ptz")
	public Integer getHasPtz() {
		return hasPtz;
	}

	@Transient
	public String getHasPtzZh() {
		return hasPtz==1?"是":"否";
	}


	public void setHasPtz(Integer hasPtz) {
		this.hasPtz = hasPtz;
	}

	@Basic
	@Column(name = "has_audio")
	public Integer getHasAudio() {
		return hasAudio;
	}

	@Transient
	public String getHasAudioZh() {
		return hasAudio==1?"是":"否";
	}


	public void setHasAudio(Integer hasAudio) {
		this.hasAudio = hasAudio;
	}

	@Basic
	@Column(name = "stream_count")
	public Integer getStreamCount() {
		return streamCount;
	}

	public void setStreamCount(Integer streamCount) {
		this.streamCount = streamCount;
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
	public String getCameraType() {
		return cameraType;
	}

	public void setCameraType(String cameraType) {
		this.cameraType = cameraType;
	}

	@Transient
	public String getServerId() {
		return serverId;
	}

	@Transient
	public String getServerName() {
		return Server.getObjectName(ServerType.MSU.toString(), serverId);
	}

	@Transient
	public String getCameraTypeZh() {
		if (null != cameraType) {
			return DictBpo.getDictName(TDict.CAMERA_TYPE,cameraType.toString());
		}
		return "";
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Transient
	public String getRecordPlanName() {
		return recordPlanName;
	}

	public void setRecordPlanName(String recordPlanName) {
		this.recordPlanName = recordPlanName;
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

		Channel channel = (Channel) o;

		if (id != null ? !id.equals(channel.id) : channel.id != null) return false;
		if (name != null ? !name.equals(channel.name) : channel.name != null) return false;
		if (enabled != null ? !enabled.equals(channel.enabled) : channel.enabled != null) return false;
		if (encoderId != null ? !encoderId.equals(channel.encoderId) : channel.encoderId != null) return false;
		if (num != null ? !num.equals(channel.num) : channel.num != null) return false;
		if (hasPtz != null ? !hasPtz.equals(channel.hasPtz) : channel.hasPtz != null) return false;
		if (hasAudio != null ? !hasAudio.equals(channel.hasAudio) : channel.hasAudio != null) return false;
		if (streamCount != null ? !streamCount.equals(channel.streamCount) : channel.streamCount != null) return false;
		if (description != null ? !description.equals(channel.description) : channel.description != null) return false;


		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
		result = 31 * result + (encoderId != null ? encoderId.hashCode() : 0);
		result = 31 * result + (num != null ? num.hashCode() : 0);
		result = 31 * result + (hasPtz != null ? hasPtz.hashCode() : 0);
		result = 31 * result + (hasAudio != null ? hasAudio.hashCode() : 0);
		result = 31 * result + (streamCount != null ? streamCount.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
