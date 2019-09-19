package com.beyeon.nvss.dmu.control.action;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.hibernate.fivsdb.DmuAlarmType;
import com.beyeon.nvss.dmu.model.bpo.DmuAlarmResBpo;

@Component("dmuAlarmResAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DmuAlarmResAction extends BaseAction {
	private DmuAlarmResBpo dmuAlarmResBpo;

	public void setDmuAlarmResBpo(DmuAlarmResBpo dmuAlarmResBpo) {
		this.dmuAlarmResBpo = dmuAlarmResBpo;
	}
	
	public String findPage(){
		dmuAlarmResBpo.findAlarmInfo(this.getPageObject());
		this.setReqAttr("alarmTypes", DmuAlarmType.getTypeList());
		return "findPage";
	}
	
	public String flow(){
		String flag = this.getReqParam("flag");
		String url = ResourceUtil.getPassenger();
		url = url + flag +".html";
		this.setReqAttr("flag", flag);
		this.setReqAttr("url",url);
		return "flow";
	}
	
	public String delete(){
		String[] ids = this.getReqParams("items");
		dmuAlarmResBpo.delete(ids);
		return findPage();
	}
}
