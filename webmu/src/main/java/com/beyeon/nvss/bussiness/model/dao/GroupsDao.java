package com.beyeon.nvss.bussiness.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.Groups;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("groupsDao")
public class GroupsDao extends NvsBaseDao {

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select t from Groups t where 1=1 ");
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
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(Integer.valueOf(paramMap.get("type")));
			sb.append("and t.type = ? ");
		}
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}
	
	public List<String> getGroupResourceIds(String id) {
		return this.getFivs_r().find("select t.resourceId from GroupResource t where t.groupId =?", new Object[]{id});
	}

	public List<String> getGroupPlatformIds(String id) {
		return this.getFivs_r().find("select pr.id from GroupResource t,PlatformRes pr where t.groupId =? and t.resourceId = pr.serverId ", new Object[]{id});
	}

	public void deleteResourceIds(String id) {
		this.getFivs_w().bulkUpdate("delete from GroupResource t where t.groupId =?", new Object[]{id});
	}

	public void deleteGroupsById(String[] ids) {
		List id = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			id.add(ids[i]);
		}
		String[] paramNames = {"ids"};
		this.getFivs_w().bulkUpdateSQLByParamName("delete g,gr,ugr from groups g left join group_resource gr on g.id = gr.group_id left join user_group_right ugr on g.id = ugr.group_id where g.id in (:ids)", paramNames, new Object[]{id});
	}

	public boolean checkGroupsUnique(String id, String name) {
		StringBuilder sql = new StringBuilder("select t from Groups t where t.name = ? ");
		List params = new ArrayList();
		params.add(name);
		if (StringUtils.isNotBlank(id)){
			sql.append("and id != ? ");
			params.add(id);
		}
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		return list.size() == 0;
	}

	public List<Groups> findGroupsById(Object[] ids) {
		List<String> paramNames = new ArrayList<String>();List params = new ArrayList();
		StringBuilder hql = new StringBuilder("from Groups t where 1=1 ");
		if (null != ids && ids.length > 0) {
			params.add(ids);
			paramNames.add("ids");
			hql.append(" and t.id in (:ids)");
		}
		return this.getFivs_r().findByParamName(hql.toString(), paramNames.toArray(new String[0]),
				params.toArray());
	}

	public List<String> findGroupsByChannelId(Object[] ids) {
		List<String> paramNames = new ArrayList<String>();List params = new ArrayList();
		StringBuilder hql = new StringBuilder("select distinct gr.groupId from GroupResource gr where 1=1 ");
		if (null != ids && ids.length > 0 ){
			params.add(ids);
			paramNames.add("ids");
			hql.append("and gr.resourceId in (:ids) ");
		}
		return this.getFivs_r().findByParamName(hql.toString(),paramNames.toArray(new String[0]),params.toArray());
	}

	public List<String> findGroupsByPlatformId(Object[] ids) {
		List<String> paramNames = new ArrayList<String>();List params = new ArrayList();
		StringBuilder hql = new StringBuilder("select distinct gr.groupId from GroupResource gr,PlatformRes pr where pr.serverId = gr.resourceId ");
		if (null != ids && ids.length > 0 ){
			params.add(ids);
			paramNames.add("ids");
			hql.append("and pr.id in (:ids) ");
		}
		return this.getFivs_r().findByParamName(hql.toString(),paramNames.toArray(new String[0]),params.toArray());
	}

	public List<Map> findServer() {
		return this.getFivs_r().findSQLToMap("select s.id,s.name,s.cmu_id as parentId from server s where s.type = ? ", new Object[]{ServerType.MDU});
	}

	public List<Map> findEncoder() {
		return this.getFivs_r().findSQLToMap("select e.id,e.name,se.server_id as serverId from encoder e ,server_encoder se where e.id = se.encoder_id ", null);
	}

	public List<Map> findChannel() {
		return this.getFivs_r().findSQLToMap("select c.id,c.name,c.encoder_id as encoderId from channel c " ,null);
	}

	public List<TreeNode> findPlatform() {
		return this.getFivs_r().findSQLToBean("select pr.id as `key`,pr.name as title,pr.parent_id as parentId,pr.server_id as topParentId from platform_res pr ", null, TreeNode.class);
	}

	public Groups findGroupsByEncoderId(String encoderId) {
		StringBuilder sql = new StringBuilder("select g from Groups g,EncoderGroups eg where g.id = eg.groupId and eg.encoderId = ? ");
		List<Groups> list = this.getFivs_r().find(sql.toString(), new Object[]{encoderId});
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
