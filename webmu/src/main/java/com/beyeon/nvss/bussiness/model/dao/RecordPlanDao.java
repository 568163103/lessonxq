package com.beyeon.nvss.bussiness.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("recordPlanDao")
public class RecordPlanDao extends NvsBaseDao {

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select t from RecordPlan t where 1=1 ");
		List params = new ArrayList();
		Map<String,String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and t.name like ? ");
		}
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}

	public boolean checkRecordPlanUnique(String id, String username) {
		StringBuilder sql = new StringBuilder("select t from RecordPlan t where t.name = ? ");
		List params = new ArrayList();
		params.add(username);
		if (StringUtils.isNotBlank(id)){
			sql.append("and name != ? ");
			params.add(id);
		}
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		return list.size() == 0;
	}

	public void deleteByName(String[] names) {
		String[] paramNames = {"names"};
		this.getFivs_w().bulkUpdateSQLByParamName("delete r from record_plan r where r.name in (:names)", paramNames, new Object[]{names});
	}

	public List findRecordPlanName() {
		return this.getFivs_r().find("select t.name from RecordPlan t ",null);
	}
}
