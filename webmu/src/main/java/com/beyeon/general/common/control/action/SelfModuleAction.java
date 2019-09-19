package com.beyeon.general.common.control.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;

@Component("moduleAction")
public class SelfModuleAction extends ModuleAction {
	private static final long serialVersionUID = 3538103684645657687L;
	private static final String SELF_MANAGE_NAME = ResourceUtil.getAppName();
	private final String LOGO_PATH = "/css/common/images/logo";

	public String selfManageTop(){
		String moduleName = SELF_MANAGE_NAME;
		String logoPath = ServletActionContext.getRequest().getContextPath()+LOGO_PATH+ResourceUtil.getIdentityCode()+".png";
		super.setReqAttr("moduleName", moduleName);
		super.setReqAttr("logoPath", logoPath);
		return "selfManageTop";
	}

	public String selfManageIndex(){
		super.index();
		return "selfManageIndex";
	}

	public String selfManageLeft(){
		this.left();
		return "selfManageLeft";
	}
}
