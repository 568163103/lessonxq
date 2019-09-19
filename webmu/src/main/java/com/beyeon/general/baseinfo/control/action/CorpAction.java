package com.beyeon.general.baseinfo.control.action;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.general.baseinfo.model.BaseinfoFacade;
import com.beyeon.hibernate.fivsdb.TCorp;

@Component("corpAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CorpAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(CorpAction.class);
	private BaseinfoFacade baseinfoFacade;
	private TCorp corp;

	public void setBaseinfoFacade(BaseinfoFacade baseinfoFacade) {
		this.baseinfoFacade = baseinfoFacade;
	}

	public TCorp getCorp() {
		return corp;
	}
	public void setCorp(TCorp corp) {
		this.corp = corp;
	}

	public Object getModel() {
		if(null == corp){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				corp = baseinfoFacade.getCorpBpo().get(Integer.valueOf(id));
			} else {
				corp = new TCorp();
			}
		}
		return corp;
	}

	public String beforeUpdate(){
		return "update";
	}

	public String update(){
		baseinfoFacade.getCorpBpo().update(corp);
		baseinfoFacade.getCorpBpo().initCorps();
		return findPage();
	}

	public String beforeSave(){
		return "save";
	}

	public String save(){
		baseinfoFacade.getCorpBpo().save(corp);
		baseinfoFacade.getCorpBpo().initCorps();
		return findPage();
	}

	public String delete(){
		String[] ids = this.getReqParams("items");
		baseinfoFacade.getCorpBpo().delete(ids);
		return findPage();
	}

	public String findPage(){
		baseinfoFacade.getCorpBpo().find(getPageObject());
		return "findPage";
	}
}
