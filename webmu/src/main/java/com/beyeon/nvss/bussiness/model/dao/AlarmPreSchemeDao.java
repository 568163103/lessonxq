package com.beyeon.nvss.bussiness.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("alarmPreSchemeDao")
public class AlarmPreSchemeDao extends NvsBaseDao {

    public void find(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select c from AlarmPreScheme c where 1=1 ");
        List params = new ArrayList();
        Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
			params.add(Integer.valueOf(paramMap.get("alarmType")));
			sb.append("and c.alarmType = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("schemeId"))) {
			params.add(Integer.valueOf(paramMap.get("schemeId")));
			sb.append("and c.schemeId = ? ");
		}
        this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
    }

    public void delete(Object[] ids) {
        String sql = new String("delete c from alarm_pre_scheme c where c.alarm_type = ? and c.source_id = ? and c.schema_id = ? ");
        this.getFivs_w().bulkUpdateSQL(sql, ids);
    }

}
