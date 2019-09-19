package com.beyeon.hibernate.fivsdb;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "encoder")
public class Encoder extends BaseEntity {
	private static Map<String,String> encoderModelMap = new HashMap<String,String>();
	private static Map<String,String> platformMap = new HashMap<String,String>();
	private String id;
	private String name;
	private Boolean hasAudio = false;
	private String model;
	private String ip;
	private Integer port;
	private String username;
	private String password;
	private Integer channelCount = 0;
	private Integer inputCount = 0;
	private Integer outputCount = 0;
	private String address = "";
	private String description = "";
	private EncoderExtra encoderExtra;

	private String updateChannel = "0";
	
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
	
	public static String getEncoderModelName(String id){
		return encoderModelMap.get(id);
	}

	public static Map<String,String> getEncoderModelMap(){
		return encoderModelMap;
	}
	
	public static String getTypeName(String id){
		return platformMap.get(id);
	}

	public static Map<String,String> getTypeMap(){
		return platformMap;
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
	@Column(name = "has_audio")
	public Boolean getHasAudio() {
		return hasAudio;
	}

	public void setHasAudio(Boolean hasAudio) {
		this.hasAudio = hasAudio;
	}

	@Basic
	@Column(name = "model")
	public String getModel() {
		return model;
	}

	@Transient
	public String getModelName() {
		return getEncoderModelName(model);
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Basic
	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Basic
	@Column(name = "port")
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Basic
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Basic
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "channel_count")
	public Integer getChannelCount() {
		return channelCount;
	}

	public void setChannelCount(Integer channelCount) {
		this.channelCount = channelCount;
	}

	@Basic
	@Column(name = "input_count")
	public Integer getInputCount() {
		return inputCount;
	}

	public void setInputCount(Integer inputCount) {
		this.inputCount = inputCount;
	}

	@Basic
	@Column(name = "output_count")
	public Integer getOutputCount() {
		return outputCount;
	}

	public void setOutputCount(Integer outputCount) {
		this.outputCount = outputCount;
	}

	@Basic
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}


	@Transient
	public String getUpdateChannel() {
		return updateChannel;
	}

	public void setUpdateChannel(String updateChannel) {
		this.updateChannel = updateChannel;
	}
	
	@OneToOne(cascade=CascadeType.ALL,optional=true)  
	@JoinColumn(name="id")
	public EncoderExtra getEncoderExtra() {
		return encoderExtra;
	}

	public void setEncoderExtra(EncoderExtra encoderExtra) {
		this.encoderExtra = encoderExtra;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Encoder encoder = (Encoder) o;

		if (id != null ? !id.equals(encoder.id) : encoder.id != null) return false;
		if (name != null ? !name.equals(encoder.name) : encoder.name != null) return false;
		if (enabled != null ? !enabled.equals(encoder.enabled) : encoder.enabled != null) return false;
		if (hasAudio != null ? !hasAudio.equals(encoder.hasAudio) : encoder.hasAudio != null) return false;
		if (model != null ? !model.equals(encoder.model) : encoder.model != null) return false;
		if (ip != null ? !ip.equals(encoder.ip) : encoder.ip != null) return false;
		if (port != null ? !port.equals(encoder.port) : encoder.port != null) return false;
		if (username != null ? !username.equals(encoder.username) : encoder.username != null) return false;
		if (password != null ? !password.equals(encoder.password) : encoder.password != null) return false;
		if (channelCount != null ? !channelCount.equals(encoder.channelCount) : encoder.channelCount != null)
			return false;
		if (inputCount != null ? !inputCount.equals(encoder.inputCount) : encoder.inputCount != null) return false;
		if (outputCount != null ? !outputCount.equals(encoder.outputCount) : encoder.outputCount != null) return false;
		if (address != null ? !address.equals(encoder.address) : encoder.address != null) return false;
		if (description != null ? !description.equals(encoder.description) : encoder.description != null) return false;
		if (status != null ? !status.equals(encoder.status) : encoder.status != null) return false;
		if (encoderExtra != null ? !encoderExtra.equals(encoder.encoderExtra) : encoder.encoderExtra != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
		result = 31 * result + (hasAudio != null ? hasAudio.hashCode() : 0);
		result = 31 * result + (model != null ? model.hashCode() : 0);
		result = 31 * result + (ip != null ? ip.hashCode() : 0);
		result = 31 * result + (port != null ? port.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (channelCount != null ? channelCount.hashCode() : 0);
		result = 31 * result + (inputCount != null ? inputCount.hashCode() : 0);
		result = 31 * result + (outputCount != null ? outputCount.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (encoderExtra != null ? encoderExtra.hashCode() : 0);
		return result;
	}
}
