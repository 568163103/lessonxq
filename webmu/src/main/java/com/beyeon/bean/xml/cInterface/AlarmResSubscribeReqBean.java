package com.beyeon.bean.xml.cInterface;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.beyeon.bean.xml.common.RequestBaseBean;

/**
 * 类名称：AlarmResSubscribeReqBean   
 * 类描述：告警信息订阅 请求Bean
 * 创建人：CP  
 * 日期：2015年4月11日 21:34:18 
 */
@XmlRootElement(name="request")
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlType(propOrder={"parameters"})
public class AlarmResSubscribeReqBean extends RequestBaseBean {
	
	@XmlElement
	private Parameter parameters = new Parameter();
	
	
	
	public Parameter getParameters(){
		return parameters;
	}
	public void setParameters(Parameter parameters) {
		this.parameters = parameters;
	}
	

	@XmlType(propOrder={"saId", "saName", "action", "group"})
	public static class Parameter {
		
		private String saId = "";
		private String saName = "";
		private String action;
		private Group group;
		
		public Parameter() {
			super();
		}

		public String getSaId() {
			return saId;
		}

		public void setSaId(String saId) {
			this.saId = saId;
		}

		public String getSaName() {
			return saName;
		}

		public void setSaName(String saName) {
			this.saName = saName;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public Group getGroup() {
			return group;
		}

		public void setGroup(Group group) {
			this.group = group;
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
	@XmlType
	public static class Url {
		
		private String id = "";
		private String type = "";
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	}

	
}
