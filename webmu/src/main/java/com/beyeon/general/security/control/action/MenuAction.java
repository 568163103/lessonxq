package com.beyeon.general.security.control.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.general.security.model.SecurityFacade;
import com.beyeon.general.security.model.dto.MenuTreeNode;
import com.beyeon.hibernate.fivsdb.TMenu;
import com.beyeon.hibernate.fivsdb.TRole;

@Component("menuAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MenuAction extends BaseAction {
	private static final long serialVersionUID = -7914762527533273510L;
	private static Logger logger =LoggerFactory.getLogger(MenuAction.class);
	private SecurityFacade securityFacade;
	private TMenu menu ;

	public void setSecurityFacade(SecurityFacade securityFacade) {
		this.securityFacade = securityFacade;
	}

	public TMenu getMenu() {
		return menu;
	}
	public void setMenu(TMenu menu) {
		this.menu = menu;
	}

	public Object getModel() {
		if(null == menu){
			String mid = this.getReqParam("menu.mid");
			if(StringUtils.isNotBlank(mid)){
				menu = securityFacade.getMenuBpo().get(Integer.valueOf(mid));
			} else {
				menu = new TMenu();
			}
		}
		return menu;
	}
	
	public String beforeUpdate(){
		responseJSON(menu);
		return null;
	}

	public String update(){
		menu.setDmlflag(BaseEntity.edit);
		menu.setDmltime(new Date());
		securityFacade.getMenuBpo().update(menu);
		List list = new ArrayList();
		list.add(new MenuTreeNode(menu));
		responseJSON(list);
		return null;
	}
	
	public String save(){
		menu.setDmlflag(BaseEntity.insert);
		securityFacade.getMenuBpo().save(menu);
		List list = new ArrayList();
		list.add(new MenuTreeNode(menu,"false"));
		responseJSON(list,true);
		return null;
	}
	
	public String delete(){
		String fullMid = this.getReqParam("menu.fullMid");
		securityFacade.getMenuBpo().deleteMenuByFullMid(fullMid);
		responseHTML("ok");
		return null;
	}
	
	public String findPage(){
		String parentId = getReqParam("menu.preid");
		if (StringUtils.isBlank(parentId)) {
			parentId = ResourceUtil.getCoreConf("app.all.module");
		}
		List<MenuTreeNode> list = securityFacade.getMenuBpo().findMenuTree(parentId);
		if(null != list && list.size()>0){
			list.get(0).setExpand(true);
		}
		setReqAttr("json", toJSON(list));
		return "findPage";
	}

	public String viewMenu(){
		String rid = getReqParam("rid");
		List<MenuTreeNode> list = securityFacade.getMenuBpo().findMenuTreeByRid(rid);
		if(null != list && list.size()>0){
			list.get(0).setExpand(true);
			setReqAttr("json", toJSON(list));
		} else {
			setReqAttr("json", toJSON(new ArrayList<MenuTreeNode>()));
		}
		return "viewMenu";
	}

	public String beforeAuthForMenu(){
		String mid = this.getReqParam("menu.mid");
		List<TRole> roles = securityFacade.getMenuBpo().getRoles();
		List<Integer> menuRoleIds = securityFacade.getMenuBpo().getMenuRoleIds(Integer.valueOf(mid));
		this.setReqAttr("roles", roles);
		this.setReqAttr("menuRoleIds", "[\""+StringUtils.join(menuRoleIds,"\",\"")+"\"]");
		return "beforeAuthForMenu";
	}

	public String authForMenu(){
		String[] rids = this.getReqParams("rids");
		String mid = this.getReqParam("mid");
		this.securityFacade.getMenuBpo().authForMenu(Integer.valueOf(mid),rids);
		return findPage();
	}
	
	public String menuMove(){
		String[] siblingIds = this.getReqParams("siblingIds");
		this.securityFacade.getMenuBpo().menuMove(siblingIds);
		responseHTML("ok");
		return null;
	}
	
	public String initMenuTree() {
		String parentIds = ResourceUtil.getCoreConf("app.all.module");
		this.securityFacade.getMenuBpo().initMenuTree(parentIds);
		this.responseHTML("ok");
		return null;
	}
}
