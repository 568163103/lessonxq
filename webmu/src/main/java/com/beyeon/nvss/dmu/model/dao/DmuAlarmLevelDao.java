package com.beyeon.nvss.dmu.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.dao.BaseDao;
import com.beyeon.common.web.model.util.PageObject;

@Component("dmuAlarmLevelDao")
public class DmuAlarmLevelDao extends BaseDao {
	

	public void findAlarmLevel(PageObject pageObject) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t from DmuAlarmType t where 1=1 ");
		sb.append("order by t.id asc ");
		this.getFivs_r().findByParamName(pageObject, sb.toString(), null,  null, false);
	}
	
	public List findAlarmLevel() {
		String hql = new String("select s from DmuAlarmType s ");
		return this.getFivs_r().find(hql,null);
	}
	
}
