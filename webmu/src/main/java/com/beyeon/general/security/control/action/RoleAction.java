package com.beyeon.general.security.control.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.scheduling.SLSchedulerFactoryBean;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.general.security.model.SecurityFacade;
import com.beyeon.general.security.model.dto.MenuTreeNode;
import com.beyeon.hibernate.fivsdb.TRole;

@Component("roleAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RoleAction extends BaseAction {
	private static final long serialVersionUID = 7961097651034770324L;
	private static Logger logger =LoggerFactory.getLogger(RoleAction.class);
	private SecurityFacade securityFacade;
	private TRole role ;

	public Object getModel() {
		if(null == role){
			String rid = this.getReqParam("rid");
			if(StringUtils.isNotBlank(rid)){
				role = securityFacade.getRoleBpo().get(Integer.valueOf(rid));
			} else {
				role = new TRole();
			}
		}
		return role;
	}

	public void setSecurityFacade(SecurityFacade securityFacade) {
		this.securityFacade = securityFacade;
	}

	public TRole getRole() {
		return role;
	}
	public void setRole(TRole Role) {
		this.role = Role;
	}
	
	public String beforeUpdate(){
		return "beforeUpdate";
	}
	public String update(){
		role.setDmlflag(BaseEntity.edit);
		role.setDmltime(new Date());
		securityFacade.getRoleBpo().update(role);
		return findPage();
	}
	
	public String save(){
		role.setDmlflag(BaseEntity.insert);
		role.setDmltime(new Date());
		securityFacade.getRoleBpo().save(role);
		return findPage();
	}
	
	public String delete(){
		String[] roles = this.getReqParams("items");
		securityFacade.getRoleBpo().delete(roles);
		return findPage();
	}
	
	public String findPage(){
		securityFacade.getRoleBpo().find(this.getPageObject());
		return "findPage";
	}

	public String beforeAuthForRole(){
		String rid = this.getReqParam("rid");
		List<Integer> roleMenuIds = securityFacade.getRoleBpo().getRoleMenuIds(Integer.valueOf(rid));
		String username = SpringUtil.getCurrUser().getUsername();
		List<Integer> adminMenuIds = securityFacade.getRoleBpo().getRoleMenuIds(2); 
		List<MenuTreeNode> menuTreeNodes = new ArrayList<MenuTreeNode>();
		if ("superadmin".equalsIgnoreCase(username)){
			menuTreeNodes = securityFacade.getMenuBpo().findMenuTreeAndSelect(ResourceUtil.getCoreConf("app.all.module"),roleMenuIds);
		}else {
			menuTreeNodes = securityFacade.getMenuBpo().findOtherMenuTreeAndSelect(ResourceUtil.getCoreConf("app.all.module"), roleMenuIds, adminMenuIds);
		}
		List<String> menuTreeJsons = new ArrayList();
		for (Iterator iterator = menuTreeNodes.iterator(); iterator.hasNext();) {
			MenuTreeNode menuTreeNode = (MenuTreeNode) iterator.next();
			menuTreeJsons.add(toJSON(menuTreeNode));
		}
		this.setReqAttr("menuTreeJsons", menuTreeJsons);
		return "beforeAuthForRole";
	}

	public String authForRoleUP(){
		String mid = this.getReqParam("mids");
		String rid = this.getReqParam("rid");
		this.securityFacade.getRoleBpo().authForRole(Integer.valueOf(rid),mid);
		//this.refreshRole();
		return findPage();
	}

	public String refreshRole(){
		SLSchedulerFactoryBean.runJob("SecurityCacheManager.init");
		responseHTML("ok");
		return null;
	}

	public String beforeCopyRole(){
		List<TRole> roles = securityFacade.getRoleBpo().getRoles();
		this.setReqAttr("roles", roles);
		return "beforeCopyRole";
	}

	public String copyRoleUP(){
		String srcrid = this.getReqParam("srcrid");
		String destrid = this.getReqParam("destrid");
		this.securityFacade.getRoleBpo().copyRole(srcrid,destrid);
		return findPage();
	}
}
