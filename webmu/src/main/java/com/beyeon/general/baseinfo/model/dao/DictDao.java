package com.beyeon.general.baseinfo.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.dao.BaseDao;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.hibernate.fivsdb.TDict;

/**
 * User: lwf
 * Date: 12-5-23
 * Time: 上午10:00
 */
@Component("dictDao")
public class DictDao extends BaseDao {
	public List<TDict> find(){
		return this.getFivs_r().find(
				"select t from TDict t where t.dmlflag != ? ",
				new Object[]{BaseEntity.delete});
	}
	@SuppressWarnings("unchecked")
	public List<TDict> findById(String id) {
		List<Integer> ids = new ArrayList<Integer>();String [] tempDids = id.split(",");
		for (int i = 0; i < tempDids.length; i++) {
			ids.add(Integer.parseInt(tempDids[i]));
		}
		StringBuilder hql = new StringBuilder();
		hql.append("select t from TDict t where t.dmlflag != :dmlflag and t.did in (:dids) ");
		return this.getFivs_r().findByParamName(hql.toString(),new String[]{"dmlflag","dids"},
				new Object[]{BaseEntity.delete, ids});
	}
	@SuppressWarnings("unchecked")
	public List<TDict> findByPid(String id) {
		List<Integer> ids = new ArrayList<Integer>();String [] tempIds = id.split(",");
		for (int i = 0; i < tempIds.length; i++) {
			ids.add(Integer.parseInt(tempIds[i]));
		}
		StringBuilder hql = new StringBuilder();
		hql.append("select t from TDict t where t.dmlflag != :dmlflag and t.preId in (:pids) ");
		return this.getFivs_r().findByParamName(hql.toString(),new String[]{"dmlflag","pids"},
				new Object[]{BaseEntity.delete, ids});
	}

	public void deleteByDid(String id) {
		this.getFivs_w().bulkUpdateSQL("delete from t_dict where did = ? or pre_id = ?", new String[]{id,id});
	}

	public int findMaxDidByPreId(Integer pid) {
		StringBuilder hql = new StringBuilder();
		List params = new ArrayList();
		hql.append("select max(t.did) from TDict t where t.did != 100000 ");
		if (null != pid){
			hql.append("and t.preId = ? ");
			params.add(pid);
		}
		Object id = this.getFivs_r().findUnique(hql.toString(),params.toArray());
		if (null == id || ((Integer)id).intValue() == 0){
			if (null == pid){
				return 100001;
			} else {
				return 1;
			}
		}
		return ((Integer)id).intValue();
	}

	public void save(TDict dict) {
		StringBuilder sb = new StringBuilder();
		this.getFivs_w().bulkUpdateSQL("INSERT INTO t_dict (did,pre_id, name, value, dmlflag, dmltime) VALUES (?, ?, ?, ?, ?, ?);",
				new Object[]{dict.getDid(),dict.getPreId(),dict.getName(),dict.getValue(),dict.getDmlflag(),dict.getDmltime()});
	}
}
