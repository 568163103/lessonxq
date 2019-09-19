package com.beyeon.nvss.fault.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.hibernate.fivsdb.TbAlarmsubcriType;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("tbAlarmsubcriTypeDao")
public class TbAlarmsubcriTypeDao extends NvsBaseDao {

	public void delete(String[] sids) {
		String[] paramNames = { "sids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete  s from tb_alarmsubcri_type s  where s.alarm_type in (:sids)",
				paramNames, new Object[]{sids});
	}
	
	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from TbAlarmsubcriType s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();		
		if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
			params.add(paramMap.get("alarmType"));
			sb.append("and s.alarmType = ? ");
		}
		
		if (StringUtils.isNotBlank(paramMap.get("beginTime"))) {
			params.add(paramMap.get("beginTime"));
			sb.append("and s.beginTime <= ? ");
		}
		
		if (StringUtils.isNotBlank(paramMap.get("endTime"))) {
			params.add(paramMap.get("endTime"));
			sb.append("and s.endTime >= ? ");
		}
		
		sb.append(" order by s.alarmType desc");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}
	
	public TbAlarmsubcriType findById(String id) {
		String hql = new String("select e from TbAlarmsubcriType e where e.id=?");
		List resList = this.getFivs_r().find(hql, new Object[]{id});
		if (resList.size() > 0)
			return (TbAlarmsubcriType)resList.get(0);
		return null;
	}
	
	
	public List findAllTbAlarmsubcriType() {
		String sql = "select c from TbTbAlarmsubcriType c";
		return this.getFivs_r().find(sql, null);
	}
	
	public List findAlarmLevel() {
		String hql = new String("select s from DmuAlarmType s ");
		return this.getFivs_r().find(hql,null);
	}
	
	public List<Map> getAlarmType() {
		StringBuilder sb = new StringBuilder();		
		sb.append("select a.alarmType,a.name from (select d.id as alarmType,d.name from dmu_alarm_type d union ");
		sb.append("select ta.type_id as alarmType,t.name from tb_alarm_type ta inner join t_dict t on ta.id = t.`value` ");
		sb.append("where t.pre_id = ? ) a order by a.alarmType");
		String type = TDict.ALARM_TYPE;		
		Object[] obj = new Object[]{type};
		return this.getFivs_r().findSQLToMap(sb.toString(), obj);
	}
	
	
	
	
}
