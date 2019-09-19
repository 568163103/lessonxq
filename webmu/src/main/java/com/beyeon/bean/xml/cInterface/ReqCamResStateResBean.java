package com.beyeon.bean.xml.cInterface;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.beyeon.bean.xml.common.ResponseBaseBean;

/**
 * 
 *     
 * 类名称：ReqCamResStateResBean   
 * 类描述：摄像机状态查询响应Bean
 * 创建人：CP   
 * 日期：2017年4月11日 下午5:39:30   
 *
 */
@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlType(propOrder={"parameters"})
public class ReqCamResStateResBean extends ResponseBaseBean {
	
	@XmlElement
	private Parameter parameters = new Parameter();
	
	public Parameter getParameters() {
		return parameters;
	}
	public void setParameters(Parameter parameters) {
		this.parameters = parameters;
	}
	
	@XmlAccessorType(XmlAccessType.FIELD) 
	@XmlType
	public static class Parameter {
		
		private Group group = new Group();

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
	@XmlType(propOrder={"resId", "state"})
	public static class Url {
		
		private String resId = "";
		private String state = "";
		
		public String getResId() {
			return resId;
		}
		public void setResId(String resId) {
			this.resId = resId;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}

	}
	
}
