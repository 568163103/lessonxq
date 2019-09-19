package com.beyeon.nvss.bussiness.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("userTreeDao")
public class UserTreeDao extends NvsBaseDao {
	
	 public void findPage(PageObject pageObject) {
		    int type = Groups.TYPE_VIDEO;
		    int type2 = Groups.TYPE_PLATFORM;
	        StringBuilder sb = new StringBuilder("select a.id,a.name,a.encoderName,a.description from (SELECT c.id,c.name,g.name AS encoderName,ut2.name as description  ");
	        sb.append("from groups g INNER JOIN group_resource gr ON g.id = gr.group_id ");
	        sb.append("INNER JOIN user_group_right ugr ON g.id = ugr.group_id ");
	        sb.append("INNER JOIN channel c ON c.id = gr.resource_id ");
	        sb.append("LEFT JOIN user_tree ut ON ugr.user_id = ut.user_id AND c.id = ut.res_id ");
	        sb.append("LEFT JOIN user_tree ut2 ON ut.parent_id = ut2.res_id  and ut.user_id = ut2.user_id WHERE 1=1  and g.type = ? ");
	        List params = new ArrayList();
	        params.add(type);
	       
	        Map<String, String> paramMap = pageObject.getParams();
	        if (StringUtils.isNotBlank(paramMap.get("id"))) {
				params.add(paramMap.get("id"));
				sb.append("and c.id = ? ");
			}	        
			if (StringUtils.isNotBlank(paramMap.get("channelName"))) {
				params.add("%" + paramMap.get("channelName") + "%");
				sb.append("and c.name like ? ");
			}
			if (StringUtils.isNotBlank(paramMap.get("encodeName"))) {
				params.add(paramMap.get("encodeName"));
				sb.append("and c.encoder_id = ? ");
			}
			if (StringUtils.isNotBlank(paramMap.get("codec"))) {
				if ("未分组".equals(paramMap.get("codec"))){
					sb.append("and ut2.res_id is null  ");
				}else{
					params.add(paramMap.get("codec"));
					sb.append("and ut2.res_id = ? ");
				}			
			}			
			if (StringUtils.isNotBlank(paramMap.get("userid"))) {
				params.add(paramMap.get("userid"));
				sb.append("and ugr.user_id = ? ");
			}else {
				params.add(SpringUtil.getCurrUser().getId());
				sb.append("and ugr.user_id = ? ");				
			}
	        sb.append(" union SELECT c.id,c.name,g.name as encoderName,ut2.name as description  ");
	        sb.append("from groups g INNER JOIN group_resource gr ON g.id = gr.group_id ");
	        sb.append("INNER JOIN user_group_right ugr ON g.id = ugr.group_id ");
	        sb.append("INNER JOIN platform_res c ON c.server_id = gr.resource_id ");
	        sb.append("LEFT JOIN user_tree ut ON ugr.user_id = ut.user_id AND c.id = ut.res_id ");
	        sb.append("LEFT JOIN user_tree ut2 ON ut.parent_id = ut2.res_id   and ut.user_id = ut2.user_id WHERE 1=1  and g.type = ? ");	       
	        params.add(type2);
	        if (StringUtils.isNotBlank(paramMap.get("id"))) {
				params.add(paramMap.get("id"));
				sb.append("and c.id = ? ");
			}	        
			if (StringUtils.isNotBlank(paramMap.get("channelName"))) {
				params.add("%" + paramMap.get("channelName") + "%");
				sb.append("and c.name like ? ");
			}
			if (StringUtils.isNotBlank(paramMap.get("encodeName"))) {
				params.add(paramMap.get("encodeName"));
				sb.append("and c.server_id = ? ");
			}			

			if (StringUtils.isNotBlank(paramMap.get("codec"))) {
				if ("未分组".equals(paramMap.get("codec"))){
					sb.append("and ut2.res_id is null  ");
				}else{
					params.add(paramMap.get("codec"));
					sb.append("and ut2.res_id = ? ");
				}				
			}			
			if (StringUtils.isNotBlank(paramMap.get("userid"))) {
				params.add(paramMap.get("userid"));
				sb.append("and ugr.user_id = ? ");
			}else {
				params.add(SpringUtil.getCurrUser().getId());
				sb.append("and ugr.user_id = ? ");				
			}
			sb.append(") a order by a.id asc ");
	        this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
	    }
	 
	 public List<Map> getUserGroupResources(String userid) {
		    int type = Groups.TYPE_VIDEO;
		    int type2 = Groups.TYPE_PLATFORM;
	        StringBuilder sb = new StringBuilder("select a.id,a.name,a.encoderName,a.description from (SELECT c.id,c.name,g.name AS encoderName,ut2.name as description  ");
	        sb.append("from groups g INNER JOIN group_resource gr ON g.id = gr.group_id ");
	        sb.append("INNER JOIN user_group_right ugr ON g.id = ugr.group_id ");
	        sb.append("INNER JOIN channel c ON c.id = gr.resource_id ");
	        sb.append("LEFT JOIN user_tree ut ON ugr.user_id = ut.user_id AND c.id = ut.res_id ");
	        sb.append("LEFT JOIN user_tree ut2 ON ut.parent_id = ut2.res_id  and ut.user_id = ut2.user_id WHERE 1=1  and g.type = ? ");
	        
			sb.append("and ugr.user_id = ? ");
		
	        sb.append(" union SELECT c.id,c.name,g.name as encoderName,ut2.name as description  ");
	        sb.append("from groups g INNER JOIN group_resource gr ON g.id = gr.group_id ");
	        sb.append("INNER JOIN user_group_right ugr ON g.id = ugr.group_id ");
	        sb.append("INNER JOIN platform_res c ON c.server_id = gr.resource_id ");
	        sb.append("LEFT JOIN user_tree ut ON ugr.user_id = ut.user_id AND c.id = ut.res_id ");
	        sb.append("LEFT JOIN user_tree ut2 ON ut.parent_id = ut2.res_id   and ut.user_id = ut2.user_id WHERE 1=1  and g.type = ? ");	       
	        
			sb.append("and ugr.user_id = ? ");			
			sb.append(") a order by a.id asc ");
			Object[] obj = new Object[]{type,userid,type2,userid};
			return this.getFivs_r().findSQLToMap(sb.toString(), obj);
	    }
	 public void findPageForUserAlarmRes(PageObject pageObject) {
		    int type = Groups.TYPE_VIDEO;
		    int type2 = Groups.TYPE_PLATFORM;
	        StringBuilder sb = new StringBuilder("select a.id,a.name,a.encoderName,a.description from (SELECT c.id,ar.name,(CASE WHEN uar.alarm_res_id is null then 0 else 1 end ) as status ,g.name AS encoderName,u.name as description  ");
	        sb.append("from groups g INNER JOIN group_resource gr ON g.id = gr.group_id ");
	        sb.append("INNER JOIN user_group_right ugr ON g.id = ugr.group_id ");
	        sb.append("INNER JOIN channel c ON c.id = gr.resource_id  INNER JOIN user u on ugr.user_id = u.id  ");
	        sb.append("left join user_info ui on u.id = ui.id left join t_user_link ul on ul.amid = u.id  ");
	        sb.append("LEFT JOIN tb_alarm_res ar ON  c.id = ar.res_id ");
	        sb.append("LEFT JOIN tb_user_alarm_res uar ON ugr.user_id = uar.user_id AND ar.id = uar.alarm_res_id WHERE 1=1  and g.type = ? ");
	        List params = new ArrayList();
	        params.add(type);
	       
	        Map<String, String> paramMap = pageObject.getParams();
	        if (StringUtils.isNotBlank(paramMap.get("id"))) {
				params.add(paramMap.get("id"));
				sb.append("and c.id = ? ");
			}	        
			if (StringUtils.isNotBlank(paramMap.get("channelName"))) {
				params.add("%" + paramMap.get("channelName") + "%");
				sb.append("and c.name like ? ");
			}
			if (StringUtils.isNotBlank(paramMap.get("encodeName"))) {
				params.add(paramMap.get("encodeName"));
				sb.append("and c.encoder_id = ? ");
			}			
			if (StringUtils.isNotBlank(paramMap.get("status"))) {
				if ("0".equals(paramMap.get("status"))){
					sb.append("and uar.alarm_res_id is null ");
				}else{
					sb.append("and uar.alarm_res_id is not null ");
				}
			}	
			if (StringUtils.isNotBlank(paramMap.get("userid"))) {
				params.add(paramMap.get("userid"));
				sb.append("and ugr.user_id = ? ");
			}
			
			if (null == SpringUtil.getCurrUser() || !"superadmin".equals(SpringUtil.getCurrUser().getUsername())) {
				sb.append("and u.name != 'superadmin'");
			}
			String userNames = ResourceUtil.getCoreConf("app.user");
			if(!userNames.contains(SpringUtil.getCurrUser().getUsername() + ",")){
				if(SpringUtil.getCurrUser().getUserLinks().size() > 0 ){
					sb.append("and (ul.full_amid like ? or ul.full_amid = ?) ");
					params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid()+"-%");
					params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid());
				} else {
					sb.append("and (u.id = ? or ul.preamid = ?) ");
					params.add(SpringUtil.getCurrUser().getId());
					params.add(SpringUtil.getCurrUser().getId());
				}
			}
	        sb.append(" union SELECT c.id,c.name,g.name as encoderName,u.name as description  ");
	        sb.append("from groups g INNER JOIN group_resource gr ON g.id = gr.group_id ");
	        sb.append("INNER JOIN user_group_right ugr ON g.id = ugr.group_id ");
	        sb.append("INNER JOIN platform_res c ON c.server_id = gr.resource_id  INNER JOIN user u on ugr.user_id = u.id  ");
	        sb.append("left join user_info ui on u.id = ui.id left join t_user_link ul on ul.amid = u.id  ");
	        sb.append("LEFT JOIN tb_alarm_res ar ON  c.id = ar.res_id ");
	        sb.append("LEFT JOIN tb_user_alarm_res uar ON ugr.user_id = uar.user_id AND ar.id = uar.alarm_res_id WHERE 1=1  and g.type = ? ");
	      params.add(type2);
	        if (StringUtils.isNotBlank(paramMap.get("id"))) {
				params.add(paramMap.get("id"));
				sb.append("and c.id = ? ");
			}	        
			if (StringUtils.isNotBlank(paramMap.get("channelName"))) {
				params.add("%" + paramMap.get("channelName") + "%");
				sb.append("and c.name like ? ");
			}
			if (StringUtils.isNotBlank(paramMap.get("encodeName"))) {
				params.add(paramMap.get("encodeName"));
				sb.append("and c.server_id = ? ");
			}			
			if (StringUtils.isNotBlank(paramMap.get("status"))) {
				if ("0".equals(paramMap.get("status"))){
					sb.append("and uar.alarm_res_id is null ");
				}else{
					sb.append("and uar.alarm_res_id is not null ");
				}
			}	
				
			if (StringUtils.isNotBlank(paramMap.get("userid"))) {
				params.add(paramMap.get("userid"));
				sb.append("and ugr.user_id = ? ");
			}
			if (null == SpringUtil.getCurrUser() || !"superadmin".equals(SpringUtil.getCurrUser().getUsername())) {
				sb.append("and u.name != 'superadmin'");
			}
			if(!userNames.contains(SpringUtil.getCurrUser().getUsername() + ",")){
				if(SpringUtil.getCurrUser().getUserLinks().size() > 0 ){
					sb.append("and (ul.full_amid like ? or ul.full_amid = ?) ");
					params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid()+"-%");
					params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid());
				} else {
					sb.append("and (u.id = ? or ul.preamid = ?) ");
					params.add(SpringUtil.getCurrUser().getId());
					params.add(SpringUtil.getCurrUser().getId());
				}
			}
			sb.append(") a order by a.id asc ");
	        this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
	    }
	public List<Map> getUserGroupResources(String uid,Boolean isChannel) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select g.id as groupId, g.name as groupName,c.id as channelId,c.name as channelName ");
//		sb.append("from groups g inner join group_resource gr on g.id=gr.group_id inner join user_group_right ugr on g.id = ugr.group_id ");
//		int type = Groups.TYPE_VIDEO;
//		if (isChannel){
//			sb.append("inner join channel ");
//			sb.append("c on c.id = gr.resource_id ");
//			sb.append("LEFT JOIN user_tree ut on ugr.user_id = ut.user_id and c.id = ut.res_id ");
//			sb.append("where ut.res_id is null and g.type = ? and ugr.user_id = ? ");
//		} else {
//			type = Groups.TYPE_PLATFORM;
//			sb.append("inner join platform_res ");
//			sb.append("c on c.server_id = gr.resource_id ");
//			sb.append("LEFT JOIN user_tree ut on ugr.user_id = ut.user_id and c.id = ut.res_id ");
//			sb.append("where ut.res_id is null and g.type = ? and ugr.user_id = ? ");
//		}
		return getUserGroupResources(uid,isChannel,null);
	}
	
	public List<Map> getUserGroupResources(String uid,Boolean isChannel,String param) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select g.id as groupId, g.name as groupName,c.id as channelId,c.name as channelName ");
		sb.append("from groups g inner join group_resource gr on g.id=gr.group_id inner join user_group_right ugr on g.id = ugr.group_id ");
		int type = Groups.TYPE_VIDEO;
		if (isChannel){
			sb.append("inner join channel ");
			sb.append("c on c.id = gr.resource_id ");
			sb.append("LEFT JOIN user_tree ut on ugr.user_id = ut.user_id and c.id = ut.res_id ");
			sb.append("where ut.res_id is null and g.type = ? and ugr.user_id = ? ");
		} else {
			type = Groups.TYPE_PLATFORM;
			sb.append("inner join platform_res ");
			sb.append("c on c.server_id = gr.resource_id ");
			sb.append("LEFT JOIN user_tree ut on ugr.user_id = ut.user_id and c.id = ut.res_id ");
			sb.append("where ut.res_id is null and g.type = ? and ugr.user_id = ? ");
		}
		Object[] obj = new Object[]{type,uid};
		if (StringUtils.isNotBlank(param)){
			param = "%"+param+"%";
			sb.append("and c.name like ?");
			obj = new Object[]{type,uid,param};
		}
		return this.getFivs_r().findSQLToMap(sb.toString(), obj);
	}

	public List<Map> getUserGroupPlatformRess(String uid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select g.id as groupId, g.name as groupName,pr.id as channelId,pr.name as channelName,pr.parent_id as parentId ");
		sb.append("from groups g inner join group_resource gr on g.id=gr.group_id inner join user_group_right ugr on g.id = ugr.group_id ");
		sb.append("left join platform_res pr on pr.server_id = gr.resource_id where g.type = ? and ugr.user_id = ? ");
		return this.getFivs_r().findSQLToMap(sb.toString(),new Object[]{Groups.TYPE_PLATFORM,uid});
	}

	public List<Map<String,Object>> getUserGroupIds(String id) {
		StringBuilder hql = new StringBuilder("select t.group_id as groupId,t.group_right as groupRight from user_group_right t where t.user_id = ? ");
		return this.getFivs_r().findSQLToMap(hql.toString(), new String[]{id});
	}
	
	public List<Map<String,Object>> getUserGroupGroupIds(String id) {
		StringBuilder hql = new StringBuilder("select t.group_id as groupId,t.group_right as groupRight from user_group_relation t where t.user_group_id = ? ");
		return this.getFivs_r().findSQLToMap(hql.toString(), new String[]{id});
	}
	
	public List<Map<String,Object>> getUserAlarmRes(String id) {
		StringBuilder hql = new StringBuilder("select t.user_id as userId,t.alarm_res_id as alarmResId from tb_user_alarm_res t where t.user_id = ? ");
		return this.getFivs_r().findSQLToMap(hql.toString(), new String[]{id});
	}

	public void findUser(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select ui.id,u.name,ui.alias from user_info ui inner join user u on ui.id = u.id  left join t_user_link ul on ul.amid = u.id where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("userName"))) {
			params.add("%" + paramMap.get("userName") + "%");
			sb.append("and u.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("position"))) {
			params.add(paramMap.get("position") + "%");
			sb.append("and u.id like ? ");
		}
		if (null == SpringUtil.getCurrUser() || !"superadmin".equals(SpringUtil.getCurrUser().getUsername())) {
			sb.append("and u.name != 'superadmin'");
		}
		String userNames = ResourceUtil.getCoreConf("app.user");
		if(!userNames.contains(SpringUtil.getCurrUser().getUsername() + ",")){
			if(SpringUtil.getCurrUser().getUserLinks().size() > 0 ){
				sb.append("and (ul.full_amid like ? or ul.full_amid = ?) ");
				params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid()+"-%");
				params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid());
			} else {
				sb.append("and (u.id = ? or ul.preamid = ?) ");
				params.add(SpringUtil.getCurrUser().getId());
				params.add(SpringUtil.getCurrUser().getId());
			}
		}
		sb.append(" order by ui.id desc");
		this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
	}

	public long getUserTreeCountByName(String name) {
		return Long.valueOf(this.getFivs_r().findUnique("select count(u) from UserTree u where u.name like ? ", new String[]{"%" + name + "%"}).toString());
	}

	public List<UserTree> findUserTreeByUid(String uid) {
		return findUserTreeByUid(uid,null);
	}

	public List<UserTree> findUserTreeByUid(String uid,String parentId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select u.user_id as userId,u.res_id as resId,u.name as name,u.parent_id as parentId,u.previous_id as previousId,ifnull(null,pr.status) as status,ifnull(c.has_ptz,pr.has_ptz) as hasPtz ");
		sql.append("from user_tree u left join channel c on u.res_id = c.id left join platform_res pr on u.res_id = pr.id where u.user_id = ? ");
		List params = new ArrayList();params.add(uid);
		if (StringUtils.isNotBlank(parentId)){
			sql.append("and u.parent_id = ? ");
			params.add(parentId);
		}
		return this.getFivs_r().findSQLToBean(sql.toString(), params.toArray(), UserTree.class);
	}

	public List<DmuEquipment> findEquipmentList() {
	 	String type1 = "编码器";
		String type2 ="IP摄像机";
		String hql = new String("select e from DmuEquipment e where e.type !=? and e.type != ?");
		return this.getFivs_r().find(hql, new Object[]{type1,type2});
	}

	public List<UserTree> findAlarmResUserTreeByResids(String resId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select tar.res_id as resId,tar.id as alarmId,tar.alarm_type as alarmType,tar.name as alarmName ");
		sql.append("from tb_alarm_res tar where tar.res_id = ?");
		List params = new ArrayList();params.add(resId);
		return this.getFivs_r().findSQLToBean(sql.toString(), params.toArray(), UserTree.class);
	}

	public List<String> fingChildUserTree(String userId, List<String> parentIds) {
		return this.getFivs_r().findSQLByParamName("select u.res_id as resId from user_tree u where u.user_id = :userId and u.parent_id in (:parentIds)" ,new String[]{"userId","parentIds"},new Object[]{userId,parentIds});
	}

	public void deleteChildUserTree(String userId, List<String> parentIds) {
		this.getFivs_r().bulkUpdateSQLByParamName("delete u from user_tree u where u.user_id = :userId and u.parent_id in (:parentIds)" ,new String[]{"userId","parentIds"},new Object[]{userId,parentIds});
	}

	public void updateUserTreePreviousId(String userId, String parentId, String resId, String previousId) {
		this.getFivs_r().bulkUpdateSQL("update user_tree u set u.previous_id =? where u.user_id = ? and u.parent_id =? and u.previous_id = ?", new Object[]{previousId, userId, parentId, resId});
	}

	public UserTree findUserTreeByTreeKey(String key) {
		StringBuilder sb = new StringBuilder();
		sb.append("select u from UserTree u where u.userId = ? and u.resId = ? ");
		List params = new ArrayList();
		String[] keys = key.split("---");
		params.add(keys[0]);params.add(keys[keys.length-1]);
		if(keys.length>2){
			sb.append("and u.parentId = ? ");
			params.add(keys[1]);
		}
		return (UserTree) this.getFivs_r().findUnique(sb.toString(), params.toArray());
	}

	public UserTree fingNextUserTree(String userId, String parentId, String previousId) {
		return (UserTree) this.getFivs_r().findUnique("select u from UserTree u where u.userId = ? and u.parentId = ? and u.previousId = ? ",new String[]{userId,parentId,previousId});
	}

	public List findUser(String uid) {
		List params = new ArrayList();
		StringBuilder sb = new StringBuilder("select ui.id,ui.alias as name from user_info ui inner join user u on ui.id = u.id  left join t_user_link ul on ul.amid = u.id where u.id != ? ");
		params.add(uid);
		if (null == SpringUtil.getCurrUser() || !"superadmin".equals(SpringUtil.getCurrUser().getUsername())) {
			sb.append("and u.name != 'superadmin'");
		}
		String userNames = ResourceUtil.getCoreConf("app.user");
		if(!userNames.contains(SpringUtil.getCurrUser().getUsername() + ",")){
			if(SpringUtil.getCurrUser().getUserLinks().size() > 0 ){
				sb.append("and (ul.full_amid like ? or ul.full_amid = ?) ");
				params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid()+"-%");
				params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid());
			} else {
				sb.append("and (u.id = ? or ul.preamid = ?) ");
				params.add(SpringUtil.getCurrUser().getId());
				params.add(SpringUtil.getCurrUser().getId());
			}
		}
		sb.append(" order by ui.id desc");
		return this.getFivs_r().findSQLToMap(sb.toString(), params.toArray());
	}

	public void deleteUserTrees(String[] uids) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from UserTree u where u.userId in (:uids) ");
		this.getFivs_w().bulkUpdateByParamName(sb.toString(), new String[]{"uids"}, new Object[]{uids});
	}

	public void deleteUserGroups(String[] uids) {
		StringBuilder hql = new StringBuilder("delete UserGroupRight t where t.userId in (:uids) ");
		this.getFivs_w().bulkUpdateByParamName(hql.toString(), new String[]{"uids"}, new Object[]{uids});
	}
	
	public void deleteUserGroupGroups(String[] ids) {
		List id = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			id.add(ids[i]);
		}
		String[] paramNames = {"ids"};
		this.getFivs_w().bulkUpdateSQLByParamName("delete a,b,c from user_group_relation a LEFT JOIN user_group_resource b on a.user_group_id = b.group_id LEFT JOIN user_group_right c on b.user_id = c.user_id where a.user_group_id in (:ids)", paramNames, new Object[]{id});
	}
	
	public void deleteUserAlarmRes(String uid) {
		StringBuilder hql = new StringBuilder("delete TbUserAlarmRes t where t.userId = ?");
		List params = new ArrayList();
		params.add(uid);
		this.getFivs_w().bulkUpdate(hql.toString(), params.toArray());;
	}

	public Long getGroupCountByName(String name) {
		return Long.valueOf(this.getFivs_r().findUnique("select count(u) from Groups u where u.name like ? ", new String[]{"%" + name+"%"}).toString());
	}

	public List<Map> findGroupsChannel() {
		return this.getFivs_r().findSQLToMap("select g.id, g.name,gr.resource_id as channelId,c.name as channelName from groups g,group_resource gr,channel c where g.type = ? and g.id = gr.group_id and gr.resource_id = c.id",new Object[]{Groups.TYPE_VIDEO});
	}

	public List<Map> findGroupsPlatform() {
		return this.getFivs_r().findSQLToMap("select g.id, g.name,gr.resource_id as channelId,pr.name as channelName from groups g,group_resource gr,platform_res pr where g.type = ? and g.id = gr.group_id and gr.resource_id = pr.id", new Object[]{Groups.TYPE_PLATFORM});
	}

	public List<UserGroupRight> findUserGroupRightByUid(String uid) {
		return this.getFivs_r().find("select t from UserGroupRight t where t.userId = ?", new Object[]{uid});
	}

	public String findTopUserTree(String topParentId) {
		List<String> result = this.getFivs_r().findSQL("SELECT u.`name` FROM `user_tree` u where u.parent_id = ? ",new Object[]{topParentId});
		if (null != result && result.size()>0){
			return result.get(0);
		}
		return "XXXX";
	}

	public List<UserTree> findBusNos(String uid) {
		String sql = "SELECT * FROM user_tree where user_id = ? and res_id not like ? ";
		return this.getFivs_r().findSQL(sql, new Object[]{uid, "______120%"}, UserTree.class);
	}

	public List<UserTree> findLastUserTrees(String uid,String resId){
		List params = new ArrayList();
		params.add(uid);//params.add("______1201%");
		StringBuilder sql = new StringBuilder("SELECT u2.`name`,u2.res_id as resId,u.res_id as previousId FROM `user_tree` u ");
		sql.append("left join user_tree u1 FORCE INDEX (u_p_p) on u1.user_id = u.user_id and u1.parent_id = u.parent_id and u1.previous_id = u.res_id ");
		sql.append("left JOIN user_tree u2 FORCE INDEX (u_r) on u2.user_id = u.user_id and u2.res_id = u.parent_id where u.user_id = ? and u1.user_id is null ");
		if (StringUtils.isNotBlank(resId)){
			sql.append("u2.res_id = ? ");
			params.add(resId);
		}
		return this.getFivs_r().findSQLToBean(sql.toString(), params.toArray(), UserTree.class);
	}


	public List<UserTree> findLastUserTreesIsNull(String uid,String resId){
		List params = new ArrayList();
		params.add(uid);params.add("______7201%");
		StringBuilder sql = new StringBuilder("SELECT u.`name`,u.res_id as resId,'' as previousId FROM `user_tree` u FORCE INDEX (u_r) ");
		sql.append("left join user_tree u1 on u.user_id = u1.user_id and u.res_id = u1.parent_id ");
		sql.append("where u.user_id = ? and u.res_id like ? and u1.user_id is null ");
		if (StringUtils.isNotBlank(resId)){
			sql.append("u.res_id = ? ");
			params.add(resId);
		}
		return this.getFivs_r().findSQLToBean(sql.toString(), params.toArray(), UserTree.class);
	}

	public List<UserTree> findUserTreeByEncoderId(String encoderId) {
		String sql = "select ut.* from user_tree ut ,encoder e where (ut.name = SUBSTRING_INDEX(e.name,'-车载设备-',1) or ut.name = e.name) and e.id = ? ";
		return this.getFivs_r().findSQL(sql, new Object[]{encoderId}, UserTree.class);
	}

	public List<UserTree> findUserTreeByChannelId(String channelId) {
		String sql = "select ut.* from user_tree ut where ut.res_id = ? ";
		return this.getFivs_r().findSQL(sql, new Object[]{channelId}, UserTree.class);
	}

	public void refreshIndex() {
		this.getFivs_w().bulkUpdateSQL("ALTER TABLE `user_tree` DROP INDEX `u_p_p`,DROP INDEX `u_r` ,ADD INDEX `u_p_p` (`user_id`, `parent_id`, `previous_id`),ADD INDEX `u_r` (`user_id`, `res_id`)",null);
	}
	
	public List<UserTree> findUserTreeByUidAndFlag(String uid,String flag) {
		StringBuilder sql = new StringBuilder();
		sql.append("select u.user_id as userId,u.res_id as resId,u.name as name,u.parent_id as parentId,u.previous_id as previousId,ifnull(null,pr.status) as status,ifnull(c.has_ptz,pr.has_ptz) as hasPtz , i.name AS username ");
		sql.append("from user_tree u left join channel c on u.res_id = c.id left join platform_res pr on u.res_id = pr.id LEFT JOIN user i ON i.id = u.user_id where u.user_id = ? and u.res_id like ?");
		List params = new ArrayList();
		params.add(uid);
		if("0".equals(flag)){
			params.add("______7201%");
		}else{
			params.add("______120%");
		}

		return this.getFivs_r().findSQLToBean(sql.toString(), params.toArray(), UserTree.class);
	}
	
	
	public List findUserList() {
		StringBuilder sb = new StringBuilder("select u.id as id,u.name as name from user u left join user_info ui on u.id = ui.id left join t_user_link ul on ul.amid = u.id where 1=1 ");
		List params = new ArrayList();			
		if(null == SpringUtil.getCurrUser() || !"superadmin".equals(SpringUtil.getCurrUser().getUsername())){
			sb.append("and u.name != 'superadmin'");
		}
		String userNames = ResourceUtil.getCoreConf("app.user");
		if(!userNames.contains(SpringUtil.getCurrUser().getUsername() + ",")){
			if(SpringUtil.getCurrUser().getUserLinks().size() > 0 ){
				sb.append("and (ul.full_amid like ? or ul.full_amid = ?) ");
				params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid()+"-%");
				params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid());
			} else {
				sb.append("and (u.id = ? or ul.preamid = ?) ");
				params.add(SpringUtil.getCurrUser().getId());
				params.add(SpringUtil.getCurrUser().getId());
			}
		}
		sb.append(" order by u.id desc");
		return this.getFivs_r().findSQLToBean(sb.toString(), params.toArray(),User.class);
	}
	
	
	public List<UserTree> findMenuByUserid(String userid) {
		StringBuilder sb = new StringBuilder("select a.user_id as userId,a.res_id as resId,a.name  from user_tree a LEFT JOIN platform_res b on a.res_id = b.id LEFT JOIN channel c on a.res_id = c.id where user_id = ? and b.id is null and c.id is null and a.res_id like ? ");
		List params = new ArrayList();
		params.add(userid);
		params.add("______7201%");
		List<UserTree> list = this.getFivs_r().findSQLToBean(sb.toString(),params.toArray(),UserTree.class);
		return list;
	}
	
	public void deleteUserTree(String uid ,String resId) {
		StringBuilder hql = new StringBuilder("delete from UserTree u where u.userId = ? and u.resId = ? ");
		List params = new ArrayList();
		params.add(uid);
		params.add(resId);
		this.getFivs_w().bulkUpdate(hql.toString(), params.toArray());
		
	}
	public void updatePreviousId(String uid,String resId){
		this.getFivs_w().bulkUpdateSQL("update user_tree u set u.previous_id =? where u.user_id = ? and u.previous_id =? ", new Object[]{"", uid,  resId});
		
	}
	public void deleteUserTreeByUserid(String uid ) {
		StringBuilder hql = new StringBuilder("delete from UserTree u where u.userId = ? ");
		List params = new ArrayList();
		params.add(uid);
		this.getFivs_w().bulkUpdate(hql.toString(), params.toArray());;
	}
	
	public List<String> findUserGroup(String groupId) {
		List<String> result = this.getFivs_r().findSQL("SELECT u.`user_id` FROM `user_group_resource` u where u.group_id = ? ",new Object[]{groupId});
		return result;
	}
	
	public List<UserGroup> findUserGroupResource() {
		StringBuilder sb = new StringBuilder("select s from UserGroup s where 1=1 ");
		List params = new ArrayList();					
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}
	
	public String findUserGroupResourceByUserId(String userid) {
		List<Integer> result = this.getFivs_r().findSQL("SELECT u.`group_id` FROM `user_group_resource` u where u.user_id = ? ",new Object[]{userid});
		if (result !=null && result.size()>0){
			return String.valueOf(result.get(0));
		}
		return null;
	}
	
	public void deleteUserGroupById(String[] ids) {
		List id = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			id.add(ids[i]);
		}
		String[] paramNames = {"ids"};
		this.getFivs_w().bulkUpdateSQLByParamName("delete gr,ugr from  user_group_right gr  left join  user_group_resource ugr on gr.user_id = ugr.user_id where gr.user_id in (:ids)", paramNames, new Object[]{id});
	}
	
	public List<UserGroupRelation> findUserGroupRelation(String userid) {
		StringBuilder sb = new StringBuilder("select s from UserGroupRelation s where userGroupId = ? ");
		List params = new ArrayList();			
		params.add(Integer.parseInt(userid));
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}

	public List<UserTree> findBusNosByUid(String uid ,boolean flag) {
		if (!flag){   //目录
			String sql = "SELECT * FROM user_tree where user_id = ? and res_id  like ?  order by parent_id ,previous_id;";
			return this.getFivs_r().findSQL(sql, new Object[]{uid, "______7201%"}, UserTree.class);
		}else{		//全部
			String sql = "SELECT * FROM user_tree where user_id = ?   order by parent_id ,previous_id;";
			return this.getFivs_r().findSQL(sql, new Object[]{uid}, UserTree.class);
		}

	}

	public List<UserTree> findBusByUid(String uid ,boolean flag) {
		if (!flag){   //目录
			String sql = "SELECT * FROM user_tree where user_id = ? and res_id  like ?  order by parent_id ,previous_id;";
			return this.getFivs_r().findSQL(sql, new Object[]{uid, "______7201%"}, UserTree.class);
		}else{		//全部
			String sql = "SELECT * FROM user_tree where user_id = ? and res_id  not like ?  order by parent_id ,previous_id;";
			return this.getFivs_r().findSQL(sql, new Object[]{uid, "______7201%"}, UserTree.class);
		}

	}
}
