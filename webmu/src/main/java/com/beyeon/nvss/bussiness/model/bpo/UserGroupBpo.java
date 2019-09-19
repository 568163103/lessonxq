package com.beyeon.nvss.bussiness.model.bpo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.UserGroup;
import com.beyeon.nvss.bussiness.model.dao.UserGroupDao;

@Cache(cacheName = "用户组")
@Component("userGroupBpo")
public class UserGroupBpo extends BaseBpo{
	private static Logger logger =LoggerFactory.getLogger(UserGroupBpo.class);
	private UserGroupDao userGroupDao;
	
	public void setUserGroupDao(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}

	public UserGroup get(Integer id) {
		return this.userGroupDao.getFivs(UserGroup.class, id);
	}
	
	public void save(UserGroup userGroup) {		
        this.userGroupDao.saveFivs(userGroup);
	}
	
	public void update(UserGroup userGroup) {
        this.userGroupDao.updateFivs(userGroup);
	}
	
	public void delete(String[] userGroups) {
		this.userGroupDao.deleteUserGroupById(userGroups);
	}

	public void find(PageObject pageObject) {
		this.userGroupDao.find(pageObject);
	}

	public List<String> getGroupResourceIds(String id) {
		List<String> channelIds = this.userGroupDao.getGroupResourceIds(id);
		channelIds.addAll(this.userGroupDao.getGroupPlatformIds(id));
		return channelIds;
	}

	public boolean checkUnique(String attrName,String value,String id) {
		return this.userGroupDao.checkUserGroupUnique(id, value);
	}
	
	public void findUserGroup(PageObject pageObject) {
		this.userGroupDao.findUserGroup(pageObject);
	}

}
