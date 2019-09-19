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
import com.beyeon.hibernate.fivsdb.Groups;
import com.beyeon.hibernate.fivsdb.PositionCode;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.nvss.bussiness.model.BussinessFacade;

@Component("groupsAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GroupsAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(GroupsAction.class);
	private BussinessFacade bussinessFacade;
	private Groups groups;

	public Object getModel() {
		if(null == groups){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				groups = bussinessFacade.getGroupsBpo().get(id);
			} else {
				groups = new Groups();
			}
		}
		return groups;
	}
	
	public Groups getGroups() {
		return groups;
	}
	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public void setBussinessFacade(BussinessFacade bussinessFacade) {
		this.bussinessFacade = bussinessFacade;
	}
	
	public String beforeUpdate(){
		this.setReqAttr("types", DictBpo.find(TDict.GROUPS_TYPE));
		beforeAuthForGroups();
		return "beforeUpdate";
	}
	public String update(){
		this.bussinessFacade.getGroupsBpo().update(groups);
		return authForGroupsUP();
	}

	public String beforeSave(){
		this.setReqAttr("types", DictBpo.find(TDict.GROUPS_TYPE));
		this.setReqAttr("positions",PositionCode.getTypeMap());
		beforeAuthForGroups();
		return "beforeSave";
	}
	
	public String save(){
		bussinessFacade.getGroupsBpo().save(groups);
		return authForGroupsUP();
	}
	
	public String delete(){
		String[] groups = this.getReqParams("items");
		bussinessFacade.getGroupsBpo().delete(groups);
		return findPage();
	}
	
	public String findPage(){
		this.setReqAttr("types", DictBpo.find(TDict.GROUPS_TYPE));
		this.setReqAttr("positions",PositionCode.getTypeMap());
		bussinessFacade.getGroupsBpo().find(this.getPageObject());
		return "findPage";
	}

	public String beforeAuthForGroups(){
		String id = this.getReqParam("id");
		List<String> groupResourceIds = bussinessFacade.getGroupsBpo().getGroupResourceIds(id);
		List<TreeNode> treeNode = bussinessFacade.getGroupsBpo().findResourceTreeAndSelect(groupResourceIds);
		this.setReqAttr("channelValue", "120");
		this.setReqAttr("treeNode", toJSON(treeNode));
		return "beforeAuthForGroups";
	}

	public String authForGroupsUP(){
		if (!groups.getType().equals(Groups.TYPE_PLATFORM)) {
			String rid = this.getReqParam("rids");
			if (null != rid) {
				String[] rids = rid.split(",");
				groups.setSubnum(rids.length);
				this.bussinessFacade.getGroupsBpo().authForGroups(groups.getId(), rids);
			}
		}
		return findPage();
	}

}
