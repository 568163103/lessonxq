package com.beyeon.general.security.model.bpo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.general.security.model.dao.MenuDao;
import com.beyeon.general.security.model.dao.RoleDao;
import com.beyeon.general.security.model.dto.MenuTreeNode;
import com.beyeon.hibernate.fivsdb.TMenu;
import com.beyeon.hibernate.fivsdb.TRole;
import com.beyeon.hibernate.fivsdb.TRoleMenu;
import com.beyeon.hibernate.fivsdb.TRoleMenuId;

@Component("menuBpo")
public class MenuBpo {
	private MenuDao menuDao;
	private RoleDao roleDao;
	
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public TMenu get(Integer mid) {
		return this.menuDao.getFivs(TMenu.class,mid);
	}
	
	public void save(TMenu menu) {
        this.menuDao.saveFivs(menu);
        this.menuDao.freshFivs(menu);
        menu.setFullMid(menu.getFullMid()+"-"+menu.getMid().toString());
        menu.setSerialNo(menu.getMid());
	}
	
	public void update(TMenu menu) {
        this.menuDao.updateFivs(menu);
	}
	
	public void deleteMenuByFullMid(String fullMid) {
		this.menuDao.deleteMenuByMid(fullMid.substring(fullMid.lastIndexOf("-")+1));
		this.menuDao.deleteMenuByFullMid(fullMid);
		this.menuDao.deletePrivMenuByMid(fullMid.substring(fullMid.lastIndexOf("-")+1));
		this.menuDao.deletePrivMenuByFullMid(fullMid);
	}

	public List<MenuTreeNode> findByMid(String mids) {
		List<MenuTreeNode> menuTreeNodes = new ArrayList<MenuTreeNode>();
		List<TMenu> menus = this.menuDao.findByMid(mids);
		for (TMenu menu : menus) {
			MenuTreeNode tempMenuTreeNode = new MenuTreeNode(menu.getMid(),menu.getName(),
					menu.getUrl(),menu.getLel(),false);
			menuTreeNodes.add(tempMenuTreeNode);
		}
		return menuTreeNodes;
	}

	public void initMenuTree(String parentIds) {
		List<TMenu> parentMenus = menuDao.findByMid(parentIds);
		for (TMenu menu : parentMenus) {
			menu.setFullMid(menu.getMid().toString());
			this.menuDao.updateFivs(menu);
			initMenuTree(menu);
		}
	}

	public void initMenuTree(TMenu parentMenu) {
		List<TMenu> menus = menuDao.findByPreid(parentMenu.getMid().toString());
		for (TMenu menu : menus) {
			menu.setFullMid(parentMenu.getFullMid()+"-"+menu.getMid().toString());
			this.menuDao.updateFivs(menu);
			initMenuTree(menu);
		}
	}
	
	public List<MenuTreeNode> findMenuTree(String parentIds) {
		Map<Integer, MenuTreeNode> menuTreeNodeMap = new HashMap<Integer, MenuTreeNode>();
		List<TMenu> menus = new ArrayList<TMenu>();
		List<MenuTreeNode> menuTreeNodes = new ArrayList<MenuTreeNode>();
		List<TMenu> parentMenus = menuDao.findByMid(parentIds);
		for (TMenu parentMenu : parentMenus) {
			MenuTreeNode menuTreeNode = new MenuTreeNode(parentMenu.getMid(),parentMenu.getName(),
					parentMenu.getUrl(),parentMenu.getLel(),false,parentMenu.getFullMid());
			menuTreeNodeMap.put(parentMenu.getMid(), menuTreeNode);
			menuTreeNodes.add(menuTreeNode);
		}
		for (MenuTreeNode menuTreeNode : menuTreeNodes) {
			List<TMenu> tempMenus = menuDao.findMenuByFullMid(menuTreeNode.getFullId());
			for (TMenu menu : tempMenus) {
				MenuTreeNode tempMenuTreeNode = new MenuTreeNode(menu.getMid(),menu.getName(),
						menu.getUrl(),menu.getLel(),false,menu.getFullMid());
				menuTreeNodeMap.put(menu.getMid(), tempMenuTreeNode);
				menus.add(menu);
			}
		}
		for (TMenu menu : menus) {
			MenuTreeNode parentTreeNode = menuTreeNodeMap.get(menu.getPreid());
			MenuTreeNode currTreeNode = menuTreeNodeMap.get(menu.getMid());
			if(null != parentTreeNode && null != currTreeNode){
				parentTreeNode.getChildren().add(currTreeNode);
			}
		}
		return menuTreeNodes;
	}
	
	public List<MenuTreeNode> findMenuTreeAndSelect(String parentIds,List<Integer> menuIds){
		Map<Integer, MenuTreeNode> menuMap = new HashMap<Integer, MenuTreeNode>();
		List<TMenu> menus = new ArrayList<TMenu>();
		List<MenuTreeNode> menuTreeNodes = new ArrayList<MenuTreeNode>();
		List<TMenu> parentMenus = menuDao.findValidByMid(parentIds);
		for (TMenu parentMenu : parentMenus) {
			MenuTreeNode menuTreeNode = new MenuTreeNode(parentMenu.getMid(),parentMenu.getName(),
					parentMenu.getUrl(),parentMenu.getLel(),false,parentMenu.getFullMid());
			menuTreeNode.setExpand(true);
			if (menuIds.contains(Integer.valueOf(menuTreeNode.getKey()))) {
				menuTreeNode.setSelect(true);
			}
			menuMap.put(parentMenu.getMid(), menuTreeNode);
			menuTreeNodes.add(menuTreeNode);
		}
		for (MenuTreeNode menuTreeNode : menuTreeNodes) {
			List<TMenu> tempMenus = menuDao.findMenuByFullMid(menuTreeNode.getFullId());
			for (TMenu menu : tempMenus) {
				MenuTreeNode tempMenuTreeNode = new MenuTreeNode(menu.getMid(),menu.getName(),
						menu.getUrl(),menu.getLel(),false,menu.getFullMid());
				tempMenuTreeNode.setExpand(true);
				if (menuIds.contains(Integer.valueOf(tempMenuTreeNode.getKey()))) {
					tempMenuTreeNode.setSelect(true);
				}
				menuMap.put(menu.getMid(), tempMenuTreeNode);
				menus.add(menu);
			}
		}
		for (TMenu menu : menus) {
			MenuTreeNode parentTreeNode = menuMap.get(menu.getPreid());
			MenuTreeNode currTreeNode = menuMap.get(menu.getMid());
			if(null != parentTreeNode && null != currTreeNode){
				parentTreeNode.getChildren().add(currTreeNode);
			}
		}
		return menuTreeNodes;
	}
	
	
	public List<MenuTreeNode> findOtherMenuTreeAndSelect(String parentIds,List<Integer> menuIds ,List<Integer> adminMenus){
		Map<Integer, MenuTreeNode> menuMap = new HashMap<Integer, MenuTreeNode>();
		List<TMenu> menus = new ArrayList<TMenu>();
		List<MenuTreeNode> menuTreeNodes = new ArrayList<MenuTreeNode>();
		List<TMenu> parentMenus = menuDao.findValidByMid(parentIds);
		for (TMenu parentMenu : parentMenus) {
			if (adminMenus.contains(parentMenu.getMid())){
				MenuTreeNode menuTreeNode = new MenuTreeNode(parentMenu.getMid(),parentMenu.getName(),
						parentMenu.getUrl(),parentMenu.getLel(),false,parentMenu.getFullMid());
				menuTreeNode.setExpand(true);
				if (menuIds.contains(Integer.valueOf(menuTreeNode.getKey()))) {
					menuTreeNode.setSelect(true);
				}
				menuMap.put(parentMenu.getMid(), menuTreeNode);
				menuTreeNodes.add(menuTreeNode);
			}
			
			
		}
		for (MenuTreeNode menuTreeNode : menuTreeNodes) {
			List<TMenu> tempMenus = menuDao.findMenuByFullMid(menuTreeNode.getFullId());
			for (TMenu menu : tempMenus) {
				if (adminMenus.contains(menu.getMid())){
					MenuTreeNode tempMenuTreeNode = new MenuTreeNode(menu.getMid(),menu.getName(),
							menu.getUrl(),menu.getLel(),false,menu.getFullMid());
					tempMenuTreeNode.setExpand(true);
					if (menuIds.contains(Integer.valueOf(tempMenuTreeNode.getKey()))) {
						tempMenuTreeNode.setSelect(true);
					}
					menuMap.put(menu.getMid(), tempMenuTreeNode);
					menus.add(menu);
				}
			}
		}
		for (TMenu menu : menus) {
			MenuTreeNode parentTreeNode = menuMap.get(menu.getPreid());
			MenuTreeNode currTreeNode = menuMap.get(menu.getMid());
			if(null != parentTreeNode && null != currTreeNode){
				parentTreeNode.getChildren().add(currTreeNode);
			}
		}
		return menuTreeNodes;
	}

	public List<Map> findTopMidCurrUser(String mids, String amid){
		return menuDao.findTopMidCurrUser(mids, amid);
	}
	public List<TreeNode> findMenuTreeCurrUser(String parentIds, String amid) {
		Map<Integer, MenuTreeNode> menuMap = new HashMap<Integer, MenuTreeNode>();
		List<TMenu> menus = new ArrayList<TMenu>();
		List<TreeNode> menuTreeNodes = new ArrayList<TreeNode>();
		List<TMenu> parentMenus = menuDao.findMenuTreeCurrUserByPreid(parentIds,amid);
		for (TMenu parentMenu : parentMenus) {
			MenuTreeNode menuTreeNode = new MenuTreeNode(parentMenu.getMid(),parentMenu.getName(),
					parentMenu.getUrl(),parentMenu.getLel(),false,parentMenu.getIconPath(),parentMenu.getFullMid());
			menuMap.put(parentMenu.getMid(), menuTreeNode);
			menuTreeNodes.add(menuTreeNode);
		}
		for (TreeNode menuTreeNode : menuTreeNodes) {
			List<TMenu> tempMenus = menuDao.findMenuTreeCurrUserByFullMid(menuTreeNode.getFullId(),amid);
			for (TMenu menu : tempMenus) {
				MenuTreeNode tempMenuTreeNode = new MenuTreeNode(menu.getMid(),menu.getName(),
						menu.getUrl(),menu.getLel(),false,menu.getIconPath(),menu.getFullMid());
				menuMap.put(menu.getMid(), tempMenuTreeNode);
				menus.add(menu);
			}
		}
		for (TMenu menu : menus) {
			MenuTreeNode parentTreeNode = menuMap.get(menu.getPreid());
			MenuTreeNode currTreeNode = menuMap.get(menu.getMid());
			if(null != parentTreeNode && null != currTreeNode){
				parentTreeNode.getChildren().add(currTreeNode);
			}
		}
		return menuTreeNodes;
	}
	
	public Map<String, TMenu> find() {
		Map<String, TMenu> map = new HashMap<String, TMenu>();
		Map<Integer, TMenu> mapTemp = new HashMap<Integer, TMenu>();
		List<TMenu> list = this.menuDao.find();
		for (TMenu menu : list) {
			mapTemp.put(menu.getMid(), menu);
		}
		for (TMenu menu : list) {
			String modName = getParentName(mapTemp,menu);
			menu.setModName(modName);
			map.put(menu.getUrl(), menu);
		}
		return map;
	}
	
	private String getParentName(Map<Integer, TMenu> mapTemp,TMenu menu){
		StringBuilder modName = new StringBuilder(menu.getName());
		TMenu tempMenu = mapTemp.get(menu.getPreid());
		if(null != tempMenu){
			modName.insert(0,"--").insert(0,getParentName(mapTemp,tempMenu));
		}
		return modName.toString();
	}
	
	public List<MenuTreeNode> findMenuTreeByRid(String rid) {
		Map<Integer, MenuTreeNode> menuTreeNodeMap = new HashMap<Integer, MenuTreeNode>();
		List<TMenu> menus = new ArrayList<TMenu>();
		List<MenuTreeNode> menuTreeNodes = new ArrayList<MenuTreeNode>();
		List<TMenu> tempMenus = menuDao.findMenuByRid(rid);
		for (TMenu menu : tempMenus) {
			MenuTreeNode tempMenuTreeNode = new MenuTreeNode(menu.getMid(),menu.getName(),
					menu.getUrl(),menu.getLel(),false,menu.getFullMid());
			menuTreeNodeMap.put(menu.getMid(), tempMenuTreeNode);
			menus.add(menu);
		}
		for (TMenu menu : menus) {
			MenuTreeNode parentTreeNode = menuTreeNodeMap.get(menu.getPreid());
			MenuTreeNode currTreeNode = menuTreeNodeMap.get(menu.getMid());
			if(null != parentTreeNode && null != currTreeNode){
				parentTreeNode.getChildren().add(currTreeNode);
			}
			if(null != currTreeNode && currTreeNode.getLevel() == 0){
				menuTreeNodes.add(currTreeNode);
			}
		}
		return menuTreeNodes;
	}

	public List<TRole> getRoles() {
		List<Integer> rids = new ArrayList<Integer>();
		String users = ResourceUtil.getCoreConf("app.user");
		if(!users.contains(SpringUtil.getCurrUser().getUsername()+",")){
			for (Iterator<GrantedAuthority> iterator = SpringUtil.getCurrUser().getAuthorities().iterator(); iterator.hasNext(); ) {
				GrantedAuthority next =  iterator.next();
				if(!"public".equals(next.getAuthority()))
					rids.add(Integer.valueOf(next.getAuthority().split("-")[0]));
			}
		}
		return this.roleDao.findRoleByRid(rids.toArray());
	}

	public List<Integer> getMenuRoleIds(Integer mid) {
		return this.menuDao.getMenuRoleIds(mid);
	}

	public void authForMenu(Integer mid, String[] rids) {
		this.menuDao.deleteRoleIdsByMid(mid);
		if (null != rids && rids.length>0) {
			List<TRoleMenu> meunRoles = new ArrayList<TRoleMenu>();
			for (int i = 0; i < rids.length; i++) {
				TRoleMenu menuRole = new TRoleMenu(new TRoleMenuId(Integer.valueOf(rids[i]), mid), (short) 1, new Date());
				meunRoles.add(menuRole);
			}
			this.menuDao.saveAllFivs(meunRoles);
		}
	}


	public void menuMove(String[] siblingIds) {
		List<TMenu> menus = this.menuDao.findByMid(StringUtils.join(siblingIds, ","));
		if(menus.size() == 2){
			int serialNo = menus.get(0).getSerialNo()==0?menus.get(0).getMid():menus.get(0).getSerialNo();
			int serialNo1 = menus.get(1).getSerialNo()==0?menus.get(1).getMid():menus.get(1).getSerialNo();
			menus.get(0).setSerialNo(serialNo1);
			menus.get(1).setSerialNo(serialNo);
			this.menuDao.saveAllFivs(menus);
		}
	}

}
