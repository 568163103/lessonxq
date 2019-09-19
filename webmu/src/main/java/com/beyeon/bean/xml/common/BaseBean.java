package com.beyeon.bean.xml.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 *    
 * 项目名称：mu   
 * 类名称：BaseBean   
 * 类描述：公共基础类
 * 创建人：gm   
 * 日期：2015年4月6日 上午9:55:15   
 * 版本：V2.9.4
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseBean extends BaseMsg {

	
	/**
	 * mina长连接产生的session编号
	 */
	@XmlTransient
	private String sessionId;

	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
//	/**
//	 * 
//	 * 方法名： setBaseMsg 
//	 * 方法描述： 为baseBean赋值
//	 * 参数说明：
//	 * 返回类型： void 
//	 *
//	 */
//	public void setBaseMsg(MsgHead head, byte[] bytebody) {
//		baseMsg.setMsgHead(head);
//		baseMsg.setXmlBody(bytebody);
//	}
//	
	
}
