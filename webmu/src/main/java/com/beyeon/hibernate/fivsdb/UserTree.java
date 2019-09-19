package com.beyeon.hibernate.fivsdb;

import java.io.InputStream;
import java.util.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.swing.text.html.HTMLDocument;

import org.apache.commons.lang3.StringUtils;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "user_tree")
@IdClass(UserTreePK.class)
public class UserTree {
	private String userId;
	private String resId;
	private String name;
	private String parentId;
	private String previousId;
	private Boolean status = false;
	private String hasPtz = "0";
	
	/** 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致*/
	private InputStream inputStream;
	/** 这个名称就是用来传给上面struts.xml中的${fileName}的*/
	private String fileName; 
	
	private String username;
	private String cameraType;
	private Integer alarmId;
	private Integer alarmType;
	private String alarmName;
	
	private String position;	
	
	@Transient
	public String getPosition() {
		if (StringUtils.isBlank(position) ){
			Map<String,String> map = Server.getObjectMap(ServerType.CMU.toString());
			for (String id : map.keySet()){
				String pos = id.substring(0,6);
				setPosition(pos);
			}

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
	@Transient
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Transient
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Transient
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Transient
	public String getCameraType() {
		return cameraType;
	}

	public void setCameraType(String cameraType) {
		this.cameraType = cameraType;
	}

	@Id
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Id
	@Column(name = "res_id")
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		/*if(null != resId && resId.length() > 10 && resId.substring(6, 10).equals("1201")){
			String channelName = Channel.getObjectName(resId);
			if (null != channelName && !name.equals(channelName)){
				return channelName;
			}
		}*/
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Id
	@Column(name = "parent_id")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Basic
	@Column(name = "previous_id")
	public String getPreviousId() {
		return previousId;
	}

	public void setPreviousId(String previousId) {
		this.previousId = previousId;
	}

	@Transient
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		if (null != status) {
			if (!(status instanceof Boolean))
				this.status = "1".equals(status.toString());
			else
				this.status = (Boolean) status;
		}
	}

	@Transient
	public String getHasPtz() {
		return hasPtz;
	}

	public void setHasPtz(Object hasPtz) {
		if (null != hasPtz)
			this.hasPtz = hasPtz.toString();
	}	
	
	@Transient
	public Integer getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(Integer alarmId) {
		this.alarmId = alarmId;
	}

	@Transient
	public Integer getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}

	@Transient
	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserTree userTree = (UserTree) o;

		if (userId != null ? !userId.equals(userTree.userId) : userTree.userId != null) return false;
		if (resId != null ? !resId.equals(userTree.resId) : userTree.resId != null) return false;
		if (name != null ? !name.equals(userTree.name) : userTree.name != null) return false;
		if (parentId != null ? !parentId.equals(userTree.parentId) : userTree.parentId != null) return false;
		if (previousId != null ? !previousId.equals(userTree.previousId) : userTree.previousId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userId != null ? userId.hashCode() : 0;
		result = 31 * result + (resId != null ? resId.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
		result = 31 * result + (previousId != null ? previousId.hashCode() : 0);
		return result;
	}
}
