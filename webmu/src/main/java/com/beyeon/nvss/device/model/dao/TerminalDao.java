package com.beyeon.nvss.device.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.hibernate.fivsdb.Terminal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("terminalDao")
public class TerminalDao extends NvsBaseDao {

	public void delete(String[] sids) {
		String[] paramNames = { "sids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete s from terminal s where s.id in (:sids)",
				paramNames, new Object[]{sids});
	}


	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from Terminal s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("id"))) {
			params.add(paramMap.get("id"));
			sb.append("and s.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and s.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("enabled"))) {
			params.add(Boolean.valueOf(paramMap.get("enabled")));
			sb.append("and s.enabled = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("position"))) {
			params.add(paramMap.get("position") + "%");
			sb.append("and s.id like ? ");
		}
		sb.append(" order by s.id desc");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}

	public List findTerminalType() {
		String hql = new String("select s from TerminalType s ");
		return this.getFivs_r().find(hql,null);
	}

	public boolean checkTerminalUnique(String id, String terminalname) {
		StringBuilder sql = new StringBuilder("select t from Terminal t where t.name = ? ");
		List params = new ArrayList();
		params.add(terminalname);
		if (StringUtils.isNotBlank(id)){
			sql.append("and t.id != ? ");
			params.add(id);
		}
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		return list.size() == 0;
	}


	public List<Terminal> findByNoPageWithExcel(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from Terminal s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("id"))) {
			params.add(paramMap.get("id"));
			sb.append("and s.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and s.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("enabled"))) {
			params.add(Boolean.valueOf(paramMap.get("enabled")));
			sb.append("and s.enabled = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("position"))) {
			params.add(paramMap.get("position") + "%");
			sb.append("and s.id like ? ");
		}
		sb.append(" order by s.id desc");
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}

}
