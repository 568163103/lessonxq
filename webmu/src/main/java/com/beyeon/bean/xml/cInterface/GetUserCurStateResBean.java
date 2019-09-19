package com.beyeon.bean.xml.cInterface;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.beyeon.bean.xml.common.ResponseBaseBean;
import com.beyeon.constants.CommandConstant;

/**
 * 类名称：GetUserCurStateResBean   
 * 类描述：用户动态信息获取响应Bean
 * 创建人：CP  
 * 日期：2017年4月11日 下午5:28:15   
 */
@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD) 
@XmlType(propOrder={"parameters"})
public class GetUserCurStateResBean extends ResponseBaseBean {
	
	@XmlElement
	private Parameter parameters = new Parameter();
	
	public Parameter getParameters() {
		return parameters;
	}
	public void setParameters(Parameter parameters) {
		this.parameters = parameters;
	}
	
	@XmlType(propOrder={"saId", "curUserId", "userIp", "userState", "group"})
	public static class Parameter {
		private String saId = "";
		private String curUserId = "";
		private String userIp = "";
		private String userState = "";
		private Group group = new Group();
		
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

		public String getUserIp() {
			return userIp;
		}

		public void setUserIp(String userIp) {
			this.userIp = userIp;
		}

		public String getUserState() {
			return userState;
		}

		public void setUserState(String userState) {
			this.userState = userState;
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
		
		private String camId = "";
		private String camName  = "";
		
		public String getCamId() {
			return camId;
		}
		public void setCamId(String camId) {
			this.camId = camId;
		}
		public String getCamName() {
			return camName;
		}
		public void setCamName(String camName) {
			this.camName = camName;
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
		this.setCommand(CommandConstant.GETUSERCURSTATE);
	}
}
