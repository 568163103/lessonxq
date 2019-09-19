package com.beyeon.general.security.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.web.model.dao.BaseDao;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.general.security.model.dto.MenuTreeNode;
import com.beyeon.hibernate.fivsdb.TMenu;

@Component("menuDao")
public class MenuDao extends BaseDao {

	public void deleteMenuByUrl(String[] urls) {
		String[] paramNames = {"dmlflag","urls"};
		this.getFivs_w().bulkUpdateByParamName("update TMenu t set t.dmlflag = :dmlflag where t.url in (:urls)",paramNames, new Object[] { BaseEntity.delete, urls });
	}

	public void deleteMenuByMid(String mid) {
		this.getFivs_w().bulkUpdate("update TMenu t set t.dmlflag = ? where t.mid = ?",
				new Object[] { BaseEntity.delete, Integer.valueOf(mid) });
	}

	public void deleteMenuByFullMid(String fullMid) {
		this.getFivs_w().bulkUpdate("update TMenu t set t.dmlflag = ? where t.fullMid like ?",
				new Object[] { BaseEntity.delete, fullMid+"-%" });
	}

	public void deletePrivMenuByMid(String mid) {
		this.getFivs_w().bulkUpdateSQL("delete u from t_role_menu u, t_menu t where u.mid = t.mid and t.mid = ?",
				new Object[] {Integer.valueOf(mid) });
	}

	public void deletePrivMenuByFullMid(String fullMid) {
		this.getFivs_w().bulkUpdateSQL("delete u from t_role_menu u, t_menu t where u.mid = t.mid and t.full_mid like ?",
				new Object[] {fullMid+"-%" });
	}
	
	@SuppressWarnings("unchecked")
	public List<TMenu> find() {
		return this.getFivs_r().find("select t from TMenu t where t.dmlflag != ?",
			new Object[] { BaseEntity.delete});
	}
	
	@SuppressWarnings("unchecked")
	public List<TMenu> findByMid(String mid,boolean status) {
		List<Integer> mids = new ArrayList<Integer>();String [] tempMids = mid.split(",");
		for (int i = 0; i < tempMids.length; i++) {
			mids.add(Integer.parseInt(tempMids[i]));
		}
		StringBuilder hql = new StringBuilder();
		hql.append("select t from TMenu t where t.dmlflag != :dmlflag and t.mid in (:mids) ");
		if(status){
			hql.append("and  t.status = 1");
		}
		hql.append(" order by t.serialNo,t.mid");
		return this.getFivs_r().findByParamName(hql.toString(),new String[]{"dmlflag","mids"},
			new Object[]{BaseEntity.delete, mids});
	}

	public List<TMenu> findByMid(String mid) {
		return this.findByMid(mid,false);
	}
	
	public List<TMenu> findValidByMid(String preid) {
		return this.findByMid(preid,true);
	}
	
	@SuppressWarnings("unchecked")
	public List<TMenu> findByPreid(String preid,boolean status) {
		List<Integer> preids = new ArrayList<Integer>();
		String [] tempPreids = preid.split(",");
		for (int i = 0; i < tempPreids.length; i++) {
			preids.add(Integer.parseInt(tempPreids[i]));
		}
		StringBuilder hql = new StringBuilder();
		hql.append("select t from TMenu t where t.dmlflag != :dmlflag and t.preid in (:preids) ");
		if(status){
			hql.append("and  t.status = 1");
		}
		return this.getFivs_r().findByParamName(hql.toString(),new String[]{"dmlflag","preids"},
			new Object[]{BaseEntity.delete, preids});
	}

	public List<TMenu> findByPreid(String mid) {
		return this.findByPreid(mid,false);
	}
	
	public List<MenuTreeNode> findValidByPreid(String preid) {
		List<MenuTreeNode> menuTreeNodes = new ArrayList<MenuTreeNode>();
		List<TMenu> menus = this.findByPreid(preid,true);
		for (TMenu menu : menus) {
			MenuTreeNode tempMenuTreeNode = new MenuTreeNode(menu.getMid(),menu.getName(),
					menu.getUrl(),menu.getLel(),false);
			menuTreeNodes.add(tempMenuTreeNode);
		}
		return menuTreeNodes;
	}

	@SuppressWarnings("unchecked")
	public List<Map> findTopMidCurrUser(String mid, String amid) {
		List<Integer> mids = new ArrayList<Integer>();String [] tempMids = mid.split(",");
		for (int i = 0; i < tempMids.length; i++) {
			mids.add(Integer.parseInt(tempMids[i]));
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct m.mid as mid,m.name as name from t_menu m,t_role_menu pm,t_user_role rm ");
		sql.append("where rm.amid = :amid and rm.rid = pm.rid and pm.mid in (:mids) and pm.mid = m.mid");
		return this.getFivs_r().findSQLToMapByParamName(sql.toString(), new String[]{"mids", "amid"},
				new Object[]{mids, amid});
	}

	@SuppressWarnings("unchecked")
	public List<TMenu> findMenuTreeCurrUserByPreid(String preid, String amid) {
		List<Integer> preids = new ArrayList<Integer>();String [] tempMids = preid.split(",");
		for (int i = 0; i < tempMids.length; i++) {
			preids.add(Integer.parseInt(tempMids[i]));
		}
		StringBuffer hql = new StringBuffer("select distinct m ");
		hql.append("from TMenu m,TRoleMenu rm,TUserRole ra ")
		.append("where m.status = 1 and m.dmlflag != :dmlflag and m.isFunc=0 and m.preid in (:parentIds) ")
		.append("and rm.id.mid = m.mid and ra.id.rid = rm.id.rid and ra.id.amid = :amid order by m.serialNo,m.mid");
		return this.getFivs_r().findByParamName(hql.toString(),new String[]{"dmlflag","parentIds","amid"},
			new Object[] { BaseEntity.delete, preids,amid});
	}

	@SuppressWarnings("unchecked")
	public List<TMenu> findMenuTreeCurrUserByFullMid(String fullMid, String amid) {
		StringBuffer hql = new StringBuffer("select distinct m ");
		hql.append("from TMenu m,TRoleMenu rm,TUserRole ra ")
		.append("where m.status = 1 and m.dmlflag != ? and m.isFunc=0 and m.fullMid like ? ")
		.append("and rm.id.mid = m.mid and ra.id.rid = rm.id.rid and ra.id.amid = ? order by m.serialNo,m.mid");
		return this.getFivs_r().find(hql.toString(),new Object[] { BaseEntity.delete, fullMid+"-%",amid});
	}

	@SuppressWarnings("unchecked")
	public List<TMenu> findMenuByFullMid(String fullMid) {
		return this.getFivs_r().find("from TMenu t where t.dmlflag != ? and t.fullMid like ? order by t.serialNo,t.mid",
				new Object[] { BaseEntity.delete, fullMid+"-%" });
	}

	public List<TMenu> findMenuByRid(String rid) {
		return this.getFivs_r().find("select t from TMenu t,TRoleMenu u where t.mid = u.id.mid and t.dmlflag != ? and u.id.rid = ? order by t.serialNo,t.mid",
				new Object[] { BaseEntity.delete, Integer.valueOf(rid)});
	}


	public List<Integer> getMenuRoleIds(Integer mid) {
		return this.getFivs_r().find("select t.id.rid from TRoleMenu t where t.id.mid =? and t.dmlflag != ?", new Object[]{mid,BaseEntity.delete});
	}

	public void deleteRoleIdsByMid(Integer mid) {
		this.getFivs_w().bulkUpdateSQL("delete t from t_role_menu t,t_role s where t.rid = s.rid and s.app_code = ? and t.mid =?", new Object[]{ResourceUtil.getAppCode(),mid});
	}

	public List findByUrl(Integer mid,String url) {
		StringBuilder sql = new StringBuilder("select t.mid from t_menu t where t.dmlflag != ? and t.url = ? ");
		List params = new ArrayList();
		params.add(BaseEntity.delete);
		params.add(url);
		if(null != mid){
			sql.append("and t.mid != ? ");
			params.add(mid);
		}
		return this.getFivs_r().findSQL(sql.toString(), params.toArray());
	}

}
