package com.beyeon.general.security.model.bpo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.general.security.model.dao.RoleDao;
import com.beyeon.hibernate.fivsdb.TRole;
import com.beyeon.hibernate.fivsdb.TRoleMenu;
import com.beyeon.hibernate.fivsdb.TRoleMenuId;

@Component("roleBpo")
public class RoleBpo extends BaseBpo {
	private RoleDao roleDao;
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public TRole get(Integer rid) {
		return this.roleDao.get(rid);
	}
	
	public void save(TRole role) {
		role.setAppCode(ResourceUtil.getAppCode());
        this.roleDao.saveFivs(role);
	}
	
	public void update(TRole role) {
        this.roleDao.updateFivs(role);
	}
	
	public void delete(String[] roles) {
		this.roleDao.deleteRoleByRid(roles);
	}

	public void find(PageObject pageObject) {
		this.roleDao.find(pageObject);
	}
	
	public List<Integer> getRoleMenuIds(Integer rid) {
		return this.roleDao.getRoleMenuIds(rid);
	}

	public void authForRole(Integer rid, String mid) {
		this.roleDao.deleteMenuIds(rid);
		if(StringUtils.isNotBlank(mid)){
			String[] mids = mid.split(",");
			List<TRoleMenu> roleMenus = new ArrayList<TRoleMenu>();
			for (int i = 0; i < mids.length; i++) {
				TRoleMenu roleMenu = new TRoleMenu(
					new TRoleMenuId(rid,Integer.valueOf(mids[i])),
					(short)1, new Date());
				roleMenus.add(roleMenu);
			}
			this.roleDao.saveAllFivs(roleMenus);
		}
	}

	public boolean checkUnique(String attrName,String value,String id) {
		return this.roleDao.checkRoleUnique(value,id);
	}

	public List<TRole> getRoles() {
		return this.roleDao.findRoleByRid(null);
	}

	public void copyRole(String srcrid, String destrid) {
		this.roleDao.copyRole(srcrid,destrid);
	}
}
