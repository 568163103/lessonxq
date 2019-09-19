package com.beyeon.nvss.fault.model.dao;

import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.dao.BaseDao;
import com.beyeon.common.web.model.util.PageObject;

@Component("alarmLevelDao")
public class AlarmLevelDao extends BaseDao {
	

	public void findAlarmLevel(PageObject pageObject,String[] ids) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t from TbAlarmType t where t.id in (:ids) ");
		sb.append("order by t.typeId asc ");
		this.getFivs_r().findByParamName(pageObject, sb.toString(), new String[]{"ids"},  new Object[]{ids}, false);
	}

	
}
