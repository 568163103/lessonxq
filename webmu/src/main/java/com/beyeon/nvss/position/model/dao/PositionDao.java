package com.beyeon.nvss.position.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.PositionCode;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("positionDao")
public class PositionDao extends NvsBaseDao {

	public void updatePosition(PositionCode position) {
		String code=position.getCode();
		String oldcode = position.getOldcode();
		String name = position.getName();
		String oldname = position.getOldname();
		if(!oldcode.equals(code) ||  StringUtils.isBlank(oldname) ||!oldname.equals(name)){
			String sql = "update position_code set code=?,name = ? where code=?";
			List params = new ArrayList();
			params.add(code);
			params.add(name);
			params.add(oldcode);
			this.getFivs_w().bulkUpdateSQL(sql, params.toArray());
		}
	}


	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from PositionCode s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		sb.append(" order by s.id desc");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}

	public void delete(String[] ids) {
		StringBuilder sql = new StringBuilder("delete p from position_code p ");
		sql.append("where p.code in (:ids)");
		this.getFivs_w().bulkUpdateSQLByParamName(sql.toString(), new String[]{"ids"}, new Object[]{ids});
	}
	
	
}
