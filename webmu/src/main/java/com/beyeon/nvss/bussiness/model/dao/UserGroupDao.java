package com.beyeon.nvss.bussiness.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("userGroupDao")
public class UserGroupDao extends NvsBaseDao {

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select t from UserGroup t where 1=1 ");
		List params = new ArrayList();
		Map<String,String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("id"))) {
			params.add(paramMap.get("id"));
			sb.append("and t.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%"+paramMap.get("name")+"%");
			sb.append("and t.name like ? ");
		}
		sb.append("order by name ");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}
	
	public List<String> getGroupResourceIds(String id) {
		
		return this.getFivs_r().find("select t.userId from user_group_res u left join  where t.groupId =?", new Object[]{id});
	}

	public List<String> getGroupPlatformIds(String id) {
		return this.getFivs_r().find("select pr.id from GroupResource t,PlatformRes pr where t.groupId =? and t.resourceId = pr.serverId ", new Object[]{id});
	}
	
	public void deleteUserGroupById(String[] ids) {
		List id = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			id.add(ids[i]);
		}
		String[] paramNames = {"ids"};
		this.getFivs_w().bulkUpdateSQLByParamName("delete g,gr,ugr from user_group g left join user_group_resource gr on g.id = gr.group_id left join user_group_right ugr on gr.user_id = ugr.user_id where g.name in (:ids)", paramNames, new Object[]{id});
	}

	public boolean checkUserGroupUnique(String id, String name) {
		StringBuilder sql = new StringBuilder("select t from UserGroup t where t.name = ? ");
		List params = new ArrayList();
		params.add(name);
		if (StringUtils.isNotBlank(id)){
			sql.append("and id != ? ");
			params.add(id);
		}
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		return list.size() == 0;
	}

	public void findUserGroup(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select u.id,u.name,u.description from user_group u where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and u.name like ? ");
		}		
		sb.append(" order by name ");
		this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
	}
	
}
