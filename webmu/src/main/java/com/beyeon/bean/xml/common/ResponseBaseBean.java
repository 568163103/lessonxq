package com.beyeon.bean.xml.common;

import java.nio.charset.Charset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.beyeon.common.util.JaxbUtils;

/**
 * 
 *    
 * 项目名称：mu   
 * 类名称：ResponseBaseBean   
 * 类描述：响应公共基础类
 * 创建人：gm   
 * 日期：2015年4月3日 下午4:49:16   
 * 版本：V2.9.4
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"command", "result"})
public abstract class ResponseBaseBean extends BaseBean{
	

	public ResponseBaseBean() {
		super();
	}
	
	public ResponseBaseBean(String command) {
		super();
		this.command = command;
	}

	@XmlAttribute
	private String command = "";
	
	@XmlElement
	private Result result = new Result();
	
	@XmlTransient
	private String message;
	

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
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
	public void preSendResMessage(MsgHead msgHead, String message) {
		getMsgHead().setSequence(msgHead.getSequence());
		setXmlBody(message.getBytes(Charset.forName(JaxbUtils.getDefaultEncoding())));
	}
	
	/**
	 * 
	 * 方法名： setValue 
	 * 方法描述： 为ResponseBaseBean对象赋值
	 * 参数说明：
	 * 返回类型： void 
	 *
	 */
	public void setValue(RequestBaseBean req, String resultCode, String resultContent) {
		this.getResult().setResultCode(resultCode);
		this.getResult().setResultContent(resultContent);
		this.setCommand(req.getCommand());
		this.setMessage(JaxbUtils.marshaller(this));
	}
	
	

}
