package com.beyeon.nvss.dmu.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.DmuAlarmRes;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("dmuAlarmResDao")
public class DmuAlarmResDao extends NvsBaseDao {
	public void delete(String[] sids) {
		String[] paramNames = { "sids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete  s from dmu_alarm_res s  where s.id in (:sids)",
				paramNames, new Object[]{sids});
	}
	public void find(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select a.id as resId,b.`name` as alarmType,a.`name` as `name`,c.`name` as description from dmu_equipment a join dmu_alarm_type b  "); 
        sb.append(" join (select * from t_dict where pre_id = '11') c on b.`level` = c.`value` where 1=1 ");
		 List params = new ArrayList();
        Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("resId"))) {
			params.add(paramMap.get("resId"));
			sb.append("and a.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and a.name like ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
			params.add(paramMap.get("alarmType"));
			sb.append("and b.id = ? ");
		}		
        sb.append("order by a.id desc,b.id asc ");
        this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
    }
	
	public DmuAlarmRes findByIdAndType(String id,String type) {
		String hql = new String("select e from DmuAlarmRes e where e.resId=? and e.alarmType=?");
		List params = new ArrayList();
		params.add(id);
		params.add(type);
		List list = this.getFivs_r().find(hql, params.toArray());
		if (list.size() > 0)
			return (DmuAlarmRes)list.get(0);
		return null;
	}
	
}
