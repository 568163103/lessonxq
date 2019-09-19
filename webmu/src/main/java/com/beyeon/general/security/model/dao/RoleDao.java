package com.beyeon.general.security.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.dao.BaseDao;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.TRole;

@Component("roleDao")
public class RoleDao extends BaseDao {

	public List<TRole> getAll() {
		List params = new ArrayList();
		params.add(BaseEntity.delete);
		StringBuilder sb = new StringBuilder("select t from TRole t where t.status = 1 and t.dmlflag != ? ");
		if(null == SpringUtil.getCurrUser() || !"user".equals(SpringUtil.getCurrUser().getUsername())){
			sb.append("and t.rname != ? ");
			params.add("超级管理员");
		}
		return this.getFivs_r().find(sb.toString(), params.toArray());
	}

	public TRole get(Integer rid) {
		return this.getFivs_r().get(TRole.class, rid);
	}
	
	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select t from TRole t where (t.appCode = ? or t.isPublic = 1) and t.dmlflag != ? ");
		List params = new ArrayList();
		params.add(ResourceUtil.getAppCode());
		params.add(BaseEntity.delete);
		Map<String,String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("roleName"))) {
			params.add("%"+paramMap.get("roleName")+"%");
			sb.append("and t.rname like ? ");
		}
		String users = ResourceUtil.getCoreConf("app.user");
		if(null == SpringUtil.getCurrUser() || !"superadmin".equalsIgnoreCase(SpringUtil.getCurrUser().getUsername())){
			sb.append("and t.rname != ? ");
			params.add("超级管理员");
			sb.append("and t.rname != ? ");
			params.add("系统管理员");
		}
		sb.append("order by t.dmltime desc ");
		this.getFivs_r().find(pageObject,sb.toString(), params.toArray());
	}
	
	public List<Integer> getRoleMenuIds(Integer rid) {
		return this.getFivs_r().find("select t.id.mid from TRoleMenu t where t.id.rid =?", new Object[]{rid});
	}

	public void deleteMenuIds(Integer rid) {
		this.getFivs_w().bulkUpdate("delete from TRoleMenu t where t.id.rid =?", new Object[]{rid});
	}

	public void deleteRoleByRid(String[] rids) {
		List rid = new ArrayList();
		for (int i = 0; i < rids.length; i++) {
			rid.add(Integer.parseInt(rids[i]));
		}
		String[] paramNames = {"dmlflag","rids"};
		this.getFivs_w().bulkUpdateByParamName(
				"update TRole t set t.dmlflag = :dmlflag where t.rid in (:rids)",
				paramNames, new Object[]{BaseEntity.delete,rid});
	}

	public TRole getRoleByRoleType(Short roleType) {
		List results = this.getFivs_r().find("select t from TRole t where t.appCode = ? and t.roleType = ?",
				new Object[]{ResourceUtil.getAppCode(),roleType});
		if (results != null && results.size() > 0) {
			return (TRole) results.get(0);
		}
		return null;
	}

	public boolean checkRoleUnique(String rolename, String id) {
		List params = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TRole t where t.appCode = ? and t.rname = ? and t.dmlflag != ? ");
		params.add(ResourceUtil.getAppCode());params.add(rolename);params.add(BaseEntity.delete);
		if (StringUtils.isNotBlank(id)){
			sb.append("and t.rid != ? ");
			params.add(Integer.valueOf(id));
		}
		List results = this.getFivs_r().find(sb.toString(),	params.toArray());
		return results.size() == 0;
	}

	public List<TRole> findRoleByRid(Object[] rids) {
		List<String> paramNames = new ArrayList<String>();List params = new ArrayList();
		params.add(ResourceUtil.getAppCode());
		paramNames.add("appCode");
		params.add(BaseEntity.delete);
		paramNames.add("dmlflag");
		StringBuilder hql = new StringBuilder("from TRole t where (t.appCode = :appCode or t.isPublic = 1) and t.dmlflag != :dmlflag ");
		if (null != rids && rids.length > 0 ){
			params.add(rids);
			paramNames.add("rids");
			hql.append(" and t.rid in (:rids)");
		}
		if (null == SpringUtil.getCurrUser() || !"superadmin".equals(SpringUtil.getCurrUser().getUsername())) {
			hql.append("and t.rname != '超级管理员'");
		}
		return this.getFivs_w().findByParamName(hql.toString(),paramNames.toArray(new String[0]),
				params.toArray());
	}

	public void copyRole(String srcrid, String destrid) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_user_role ( rid ,amid,dmlflag,dmltime) select ");
		sql.append(srcrid).append(",amid,1,now() from t_user_role where rid = ?");
		this.getFivs_w().bulkUpdateSQL(sql.toString(),new Object[]{Integer.valueOf(destrid)});
	}
}
