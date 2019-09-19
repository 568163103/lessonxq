package com.beyeon.bean.xml.cInterface;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.beyeon.bean.xml.common.RequestBaseBean;

/** 
 * 类名称：ResReportReqBean   
 * 类描述：用户摄像机资源屏蔽请求Bean
 * 创建人：CP  
 * 日期：2015年4月11日  21:44:18   
 */
@XmlRootElement(name="request")
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlType(propOrder={"parameters"})
public class SetUserCamManageReqBean extends RequestBaseBean {
	
	@XmlElement
	private Parameter parameters = new Parameter();
	
	
	public Parameter getParameters() {
		return parameters;
	}
	public void setParameters(Parameter parameters) {
		this.parameters = parameters;
	}
	

	@XmlType(propOrder={"cuId", "cuLevel", "action", "startTime", "endTime", "schduleCreatTime", "group", "whiteUser"})
	public static class Parameter {
		
		private String cuId = "";
		private String cuLevel = "";
		private String action = "";
		private String startTime = "";
		private String endTime = "";
		private String schduleCreatTime = "";
		private Group group;
		private WhiteUser whiteUser;
		
		public Parameter() {
			super();
		}
		
		public String getCuId() {
			return cuId;
		}

		public void setCuId(String cuId) {
			this.cuId = cuId;
		}

		public String getCuLevel() {
			return cuLevel;
		}

		public void setCuLevel(String cuLevel) {
			this.cuLevel = cuLevel;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getSchduleCreatTime() {
			return schduleCreatTime;
		}

		public void setSchduleCreatTime(String schduleCreatTime) {
			this.schduleCreatTime = schduleCreatTime;
		}

		public Group getGroup() {
			return group;
		}

		public void setGroup(Group group) {
			this.group = group;
		}

		public WhiteUser getWhiteUser() {
			return whiteUser;
		}

		public void setWhiteUser(WhiteUser whiteUser) {
			this.whiteUser = whiteUser;
		}
		
	}
	
	@XmlAccessorType(XmlAccessType.FIELD) 
	@XmlType
	public static class Group {
		
		@XmlElement(name="URL")
		private List<Url> urls;

		public List<Url> getUrls() {
			return urls;
		}

		public void setUrls(List<Url> urls) {
			this.urls = urls;
		}	
	}
	
	@XmlAccessorType(XmlAccessType.FIELD) 
	@XmlType(propOrder={"camId"})
	public static class Url {
		
		private String camId = "";

		public String getCamId() {
			return camId;
		}

		public void setCamId(String camId) {
			this.camId = camId;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD) 
	@XmlType
	public static class WhiteUser {
		
		@XmlElement(name="URL")
		private List<UserUrl> urls;

		public List<UserUrl> getUrls() {
			return urls;
		}

		public void setUrls(List<UserUrl> urls) {
			this.urls = urls;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD) 
	@XmlType(propOrder={"id"})
	public static class UserUrl {
		
		private String id = "";

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}
	
}
