package com.beyeon.nvss.bussiness.control.action;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.hibernate.fivsdb.RecordPlan;
import com.beyeon.hibernate.fivsdb.Resolution;
import com.beyeon.nvss.bussiness.model.BussinessFacade;

@Component("recordPlanAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RecordPlanAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(RecordPlanAction.class);
	private BussinessFacade bussinessFacade;
	private RecordPlan recordPlan;

	public Object getModel() {
		if(null == recordPlan){
			String name = this.getReqParam("oname");
			if(StringUtils.isNotBlank(name)){
				recordPlan = bussinessFacade.getRecordPlanBpo().get(name);
			} else {
				recordPlan = new RecordPlan();
			}
		}
		return recordPlan;
	}
	
	public RecordPlan getRecordPlan() {
		return recordPlan;
	}
	public void setRecordPlan(RecordPlan recordPlan) {
		this.recordPlan = recordPlan;
	}

	public void setBussinessFacade(BussinessFacade bussinessFacade) {
		this.bussinessFacade = bussinessFacade;
	}

	public String beforeUpdate(){
		this.setReqAttr("resolutions", Resolution.getTypeMap());
		return "beforeUpdate";
	}
	public String update(){
		this.bussinessFacade.getRecordPlanBpo().update(recordPlan);
		return findPage();
	}

	public String beforeSave(){
		this.setReqAttr("resolutions", Resolution.getTypeMap());
		return "beforeSave";
	}

	public String save(){
		this.bussinessFacade.getRecordPlanBpo().save(recordPlan);
		return findPage();
	}
	
	public String delete(){
		String[] recordPlan = this.getReqParams("items");
		this.bussinessFacade.getRecordPlanBpo().delete(recordPlan);
		return findPage();
	}

	public String findPage(){
		this.bussinessFacade.getRecordPlanBpo().find(this.getPageObject());
		return "findPage";
	}

}
