package com.beyeon.general.baseinfo.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.dao.BaseDao;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.hibernate.fivsdb.TDept;

@Component("deptDao")
public class DeptDao extends BaseDao {
	@SuppressWarnings("unchecked")
	public List<TDept> findByDid(String did) {
		List<Integer> dids = new ArrayList<Integer>();String [] tempDids = did.split(",");
		for (int i = 0; i < tempDids.length; i++) {
			dids.add(Integer.parseInt(tempDids[i]));
		}
		StringBuilder hql = new StringBuilder();
		hql.append("select t from TDept t where t.dmlflag != :dmlflag and t.deptId in (:dids) ");
		return this.getFivs_r().findByParamName(hql.toString(),new String[]{"dmlflag","dids"},
			new Object[]{BaseEntity.delete, dids});
	}
	
	public List<TDept> findDeptByFullDid(String fullDid) {
		StringBuilder sb = new StringBuilder("select t from TDept t where t.dmlflag != ?");
		List params = new ArrayList();
		params.add(BaseEntity.delete);
		if (StringUtils.isNotBlank(fullDid)) {
			sb.append(" and t.fullDid like ?");
			params.add(fullDid + "-%");
		}
		return this.getFivs_r().find(sb.toString(), params.toArray());
	}

	public void deleteDeptByDeptId(String deptId) {
		this.getFivs_w().bulkUpdate("update TDept t set t.dmlflag = ? where t.deptId = ?",
				new Object[] { BaseEntity.delete, Integer.valueOf(deptId) });
	}

	public void deleteDeptByFullDid(String fullDid) {
		this.getFivs_w().bulkUpdate("update TDept t set t.dmlflag = ? where t.fullDid like ?",
				new Object[] { BaseEntity.delete, fullDid+"-%" });
	}

}
