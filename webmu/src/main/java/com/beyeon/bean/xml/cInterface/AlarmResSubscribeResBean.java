package com.beyeon.bean.xml.cInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.beyeon.bean.xml.common.ResponseBaseBean;
import com.beyeon.constants.CommandConstant;

/** 
 * 类名称：AlarmResSubscribeResBean   
 * 类描述：告警信息订阅 响应Bean
 * 创建人：CP   
 * 日期：2015年4月11日 22:08:43   
 */
@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD) 
public class AlarmResSubscribeResBean extends ResponseBaseBean {
	
	@XmlElement
	private Parameter parameters = new Parameter();
	
	
	public Parameter getParameters() {
		return parameters;
	}
	public void setParameters(Parameter parameters) {
		this.parameters = parameters;
	}
	

	@XmlType(propOrder={"saId"})
	public static class Parameter {
		
		private String saId = "";
		 
		public Parameter() {
			super();
		}

		public String getSaId() {
			return saId;
		}

		public void setSaId(String saId) {
			this.saId = saId;
		}
	}
	
	/**
	 * 方法名： setValue 
	 * 方法描述： 设置对象返回信息
	 * 参数说明：
	 * 返回类型： void 
	 */
	public void setValue(String resultCode, String resultContent) {
		this.getResult().setResultCode(resultCode);
		this.getResult().setResultContent(resultContent);
		this.setCommand(CommandConstant.ALARMRESSUBSCRIBE);
	}
	 
}
