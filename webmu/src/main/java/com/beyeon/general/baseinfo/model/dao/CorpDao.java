package com.beyeon.general.baseinfo.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.Equipment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.dao.BaseDao;
import com.beyeon.common.web.model.util.PageObject;

@Component("corpDao")
public class CorpDao extends BaseDao {

	public void delete(String[] cids) {
		String[] paramNames = { "cids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete s from t_corp s where s.cid in (:cids)",
				paramNames, new Object[]{cids});
	}


	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from TCorp s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("address"))) {
			params.add(paramMap.get("address"));
			sb.append("and s.address = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("cname"))) {
			params.add("%" + paramMap.get("cname") + "%");
			sb.append("and s.cname like ? ");
		}
		sb.append(" order by s.cid desc");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}

	public boolean checkCorpUnique(String cname, String id) {
		StringBuilder sql = new StringBuilder("select t from TCorp t where t.cname = ? ");
		List params = new ArrayList();
		params.add(cname);
		if (StringUtils.isNotBlank(id)){
			sql.append("and t.cid != ? ");
			params.add(Integer.valueOf(id));
		}
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		return list.size() == 0;
	}

	public Equipment findIdByIp(String ip){
		StringBuilder sql = new StringBuilder("select t from Equipment t where t.ip = ? ");
		List params = new ArrayList();
		params.add(ip);
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		if (list!=null && list.size()>0){
			Equipment equipment = (Equipment) list.get(0);
			return equipment;
		}
		return null;
	}
}
