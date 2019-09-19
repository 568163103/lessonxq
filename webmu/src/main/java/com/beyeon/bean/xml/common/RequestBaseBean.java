package com.beyeon.bean.xml.common;

import java.nio.charset.Charset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import com.beyeon.common.util.JaxbUtils;

/**
 * 
 *    
 * 项目名称：mu   
 * 类名称：RequestBaseBean   
 * 类描述：请求公共基础类
 * 创建人：gm   
 * 日期：2015年4月3日 下午4:09:02   
 * 版本：V2.9.4
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class RequestBaseBean extends BaseBean{
	

	public RequestBaseBean() {
		super();
	}
	
	public RequestBaseBean(String command) {
		super();
		this.command = command;
	}

	@XmlAttribute
	private String command = "";
	
	@XmlTransient
	private String message;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * 方法名： preSendResMessage 
	 * 方法描述： 预处理返回消息，为baseMsg对象赋值
	 * 参数说明：
	 * 返回类型： void 
	 *
	 */
	public void preSendReqMessage(String message) {
		setXmlBody(message.getBytes(Charset.forName(JaxbUtils.getDefaultEncoding())));
	}
	
	
	
}
