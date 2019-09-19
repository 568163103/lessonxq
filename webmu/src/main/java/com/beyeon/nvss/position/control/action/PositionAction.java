package com.beyeon.nvss.position.control.action;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.hibernate.fivsdb.PositionCode;
import com.beyeon.nvss.position.model.bpo.PositionBpo;

@Component("positionAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PositionAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(PositionAction.class);
	private PositionBpo positionBpo;
	private PositionCode position;
	
	public PositionBpo getPositionBpo() {
		return positionBpo;
	}
	public void setPositionBpo(PositionBpo positionBpo) {
		this.positionBpo = positionBpo;
	}

	public PositionCode getPosition() {
		return position;
	}
	public void setPosition(PositionCode position) {
		this.position = position;
	}
	public Object getModel() {
		if(null == position){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				position = positionBpo.get(id);
			} else {
				position = new PositionCode();
			}
		}
		return position;
	}
	
	public String beforeUpdate(){
		return "beforeUpdate";
	}

	public String update(){
		positionBpo.update(position);
		return findPage();
	}

	public String beforeSave(){
		return "beforeSave";
	}
	
	public String save(){
		positionBpo.save(position);
		return findPage();
	}
	
	public String delete(){
		String[] ids = this.getReqParams("items");
		positionBpo.delete(ids);
		return findPage();
	}
	
	public String findPage(){
		positionBpo.find(getPageObject());
		return "findPage";
	}

}
