package com.beyeon.bean.xml.cInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.beyeon.bean.xml.common.RequestBaseBean;

/**
 * 类名称：UserResReportReqBean   
 * 类描述：用户动态信息获取请求Bean
 * 创建人：CP   
 * 日期：2017年4月11日 上午11:34:18   
 */
@XmlRootElement(name="request")
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlType(propOrder={"parameters"})
public class GetUserCurStateReqBean extends RequestBaseBean {
	
	@XmlElement
	private Parameter parameters = new Parameter();
	
	public Parameter getParameters() {
		return parameters;
	}
	public void setParameters(Parameter parameters) {
		this.parameters = parameters;
	}
	

	@XmlType(propOrder={"saId", "curUserId"})
	public static class Parameter {
		
		private String saId = "";
		private String curUserId = "";
		
		public Parameter() {
			super();
		}

		public String getSaId() {
			return saId;
		}

		public void setSaId(String saId) {
			this.saId = saId;
		}

		public String getCurUserId() {
			return curUserId;
		}

		public void setCurUserId(String curUserId) {
			this.curUserId = curUserId;
		}
		
	}
	
}
