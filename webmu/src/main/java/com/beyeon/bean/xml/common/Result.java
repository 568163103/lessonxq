package com.beyeon.bean.xml.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;


/**
 * 
 *    
 * 项目名称：dlu   
 * 类名称：Result   
 * 类描述：
 * 创建人：gm   
 * 日期：2015年2月26日 上午11:24:51   
 * 版本：V2.9.3.001
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {
	
	@XmlAttribute(name="code")
	private String resultCode = "0";
	
	@XmlValue
	private String resultContent;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultContent() {
		return resultContent;
	}
	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}
	
	
}
