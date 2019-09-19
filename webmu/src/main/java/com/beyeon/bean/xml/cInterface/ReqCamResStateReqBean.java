package com.beyeon.bean.xml.cInterface;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.beyeon.bean.xml.common.RequestBaseBean;
import com.beyeon.common.util.JaxbUtils;

/**
 * 
 *      
 * 类名称：ReqCamResStateReqBean   
 * 类描述：摄像机状态查询请求Bean
 * 创建人：CP   
 * 日期：2017年4月11日 下午5:36:31   
 *
 */
@XmlRootElement(name="request")
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlType(propOrder={"parameters"})
public class ReqCamResStateReqBean extends RequestBaseBean {
	
	@XmlElement
	private Parameter parameters = new Parameter();
	
	public Parameter getParameters() {
		return parameters;
	}
	public void setParameters(Parameter parameters) {
		this.parameters = parameters;
	}
	
	@XmlAccessorType(XmlAccessType.FIELD) 
	@XmlType(propOrder={"saId", "group"})
	public static class Parameter {
		private String saId = "";
		
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
		private String resId = "";
		
		public String getResId() {
			return resId;
		}
		public void setResId(String resId) {
			this.resId = resId;
		}

	}
	
	public static void main(String[] args) {
		ReqCamResStateReqBean bean = new ReqCamResStateReqBean();
		bean.setCommand("ReqCamResState");
		ReqCamResStateReqBean.Group group = new ReqCamResStateReqBean.Group();
		
		List<ReqCamResStateReqBean.Url> urllist = new ArrayList<ReqCamResStateReqBean.Url>();
		ReqCamResStateReqBean.Url url = new ReqCamResStateReqBean.Url();
		url.setResId("1");
		urllist.add(url);
	
		group.setUrls(urllist);
		bean.getParameters().setGroup(group);
		bean.getParameters().setSaId("XXXXXX");
		
		System.out.println(JaxbUtils.marshaller(bean));
	}
	
}
