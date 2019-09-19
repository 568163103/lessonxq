package com.beyeon.general.common.control.action;

import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;

/**
 * User: Administrator
 * Date: 2016/3/16
 * Time: 17:02
 */
@Component("formInterfaceAction")
public class FormInterfaceAction extends BaseAction {
	public String execute() throws Exception {
		return this.getReqParam("cmd");
	}

}
