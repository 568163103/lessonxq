package com.beyeon.nvss.bussiness.control.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.UserGroup;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.nvss.bussiness.model.BussinessFacade;

@Component("userGroupAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserGroupAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(UserGroupAction.class);
	private BussinessFacade bussinessFacade;
	private UserGroup userGroup;

	public Object getModel() {
		if(null == userGroup){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				userGroup = bussinessFacade.getUserGroupBpo().get(Integer.parseInt(id));
			} else {
				userGroup = new UserGroup();
			}
		}
		return userGroup;
	}
	
	public UserGroup getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public void setBussinessFacade(BussinessFacade bussinessFacade) {
		this.bussinessFacade = bussinessFacade;
	}
	
	public String beforeUpdate(){
		this.setReqAttr("types", DictBpo.find(TDict.GROUPS_TYPE));
		return "beforeUpdate";
	}
	public String update(){
		this.bussinessFacade.getUserGroupBpo().update(userGroup);
		return findPage();
	}

	public String beforeSave(){
		this.setReqAttr("types", DictBpo.find(TDict.GROUPS_TYPE));		
		return "beforeSave";
	}
	
	public String save(){
		bussinessFacade.getUserGroupBpo().save(userGroup);
		return findPage();
	}
	
	public String delete(){
		String[] userGroup = this.getReqParams("items");
		bussinessFacade.getUserGroupBpo().delete(userGroup);
		return findPage();
	}
	
	public String findPage(){
		this.setReqAttr("types", DictBpo.find(TDict.GROUPS_TYPE));
		bussinessFacade.getUserGroupBpo().find(this.getPageObject());
		return "findPage";
	}

	

	

}
