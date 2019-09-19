package com.beyeon.nvss.fault.model.dao;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("alarmInfoDao")
public class AlarmInfoDao extends NvsBaseDao {

    public void findAlarmInfo(PageObject pageObject) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from (select t.id as id,t.source_id as sourceId,t.alarm_type as alarmType,t.name as name,t.begin_time as beginTime,"
                + "t.end_time as endTime,t.state as state,t.deal_state as dealState,"
                + "t.deal_user_id as dealUserId,t.deal_time as dealTime,t.description as description,t.memo as memo,b.level as alarmLevel,dt.name as alarmTypeName,dt2.name as alarmLevelName,'0' as type  from alarm_info t "
                + "left join tb_alarm_type b on t.alarm_type = b.id  "
                + "left join (select * from t_dict where pre_id = ?) dt on t.alarm_type = dt.value "
                + "left join (select * from t_dict where pre_id = ?) dt2 on b.level = dt2.value where 1 = 1 ");

        List params = new ArrayList();
        params.add(TDict.ALARM_TYPE);
        params.add(TDict.ALARM_LEVEL);
        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("sourceId"))) {
            params.add(paramMap.get("sourceId"));
            sb.append("and t.source_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("dealState"))) {
            params.add(paramMap.get("dealState"));
            sb.append("and t.deal_state = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("name"))) {
            params.add("%" + paramMap.get("name") + "%");
            sb.append("and t.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(Integer.valueOf(paramMap.get("alarmType")));
            sb.append("and t.alarm_type = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmLevel"))) {
            params.add(Integer.valueOf(paramMap.get("alarmLevel")));
            sb.append("and b.level = ? ");
        }
        if (StringUtils.isBlank(paramMap.get("beginTime"))) {
            paramMap.put("beginTime", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("beginTime"));
        sb.append("and t.begin_time > ? ");
        if (StringUtils.isBlank(paramMap.get("endTime"))) {
            paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("endTime"));
        sb.append("and t.end_time < ? ");
        sb.append(" UNION ALL ");
        sb.append("SELECT o.id AS id,o.source_id AS sourceId,tb.id AS alarmType,o.name AS name,o.begin_time AS beginTime,o.end_time AS end_time,o.state AS state,o.deal_state AS dealState, o.deal_user_id AS dealUserId,o.deal_time AS dealTime,");
        sb.append("o.description as description,o.memo as memo,tb.level as alarmLevel,dt.name as alarmTypeName,dt2.name as alarmLevelName,'1' as type from dmu_alarm_info o ");
        sb.append("Left Join  tb_alarm_type tb on o.alarm_type = tb.type_id ");
        sb.append("left join (select * from t_dict where pre_id = ?) dt on tb.id = dt.value ");
        sb.append("left join (select * from t_dict where pre_id = ?) dt2 on tb.level = dt2.value where 1 = 1 ");
        params.add(TDict.ALARM_TYPE);
        params.add(TDict.ALARM_LEVEL);
        if (StringUtils.isNotBlank(paramMap.get("sourceId"))) {
            params.add(paramMap.get("sourceId"));
            sb.append("and o.source_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("dealState"))) {
            params.add(paramMap.get("dealState"));
            sb.append("and o.deal_state = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("name"))) {
            params.add("%" + paramMap.get("name") + "%");
            sb.append("and o.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(Integer.valueOf(paramMap.get("alarmType")));
            sb.append("and tb.id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmLevel"))) {
            params.add(Integer.valueOf(paramMap.get("alarmLevel")));
            sb.append("and tb.level = ? ");
        }
        if (StringUtils.isBlank(paramMap.get("beginTime"))) {
            paramMap.put("beginTime", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("beginTime"));
        sb.append("and o.begin_time > ? ");
        if (StringUtils.isBlank(paramMap.get("endTime"))) {
            paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("endTime"));
        sb.append("and o.end_time < ? ");
        sb.append(" ) a order by a.endTime desc");
        this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
    }

    public void handleAlarm(String status, String uid, String memo, String id, String type) {
        if ("0".equals(type)) {
            this.getFivs_w().bulkUpdateSQL("update alarm_info t set t.deal_state = ?,t.deal_user_id = ?,t.deal_time = ?,t.memo = ? where t.id = ? ",
                    new Object[]{Integer.parseInt(status), uid, DateUtil.format(new Date(), DateUtil.yyyyMMddHHmmssSpt), memo, Integer.parseInt(id)});

        } else if ("1".equals(type)) {
            this.getFivs_w().bulkUpdateSQL("update dmu_alarm_info t set t.deal_state = ?,t.deal_user_id = ?,t.deal_time = ?,t.memo = ? where t.id = ? ",
                    new Object[]{Integer.parseInt(status), uid, DateUtil.format(new Date(), DateUtil.yyyyMMddHHmmssSpt), memo, Integer.parseInt(id)});

        }
    }

    public List findAlarmInfoStatistics(Map<String, String> paramMap) {
        List params = new ArrayList();
        StringBuffer sb = new StringBuffer("select * from (select t.alarm_type as alarmType,count(1) from alarm_info t left join tb_alarm_type b on t.alarm_type = b.id where 1=1  ");
        if (StringUtils.isNotBlank(paramMap.get("statisticsTime"))) {
            params.add(paramMap.get("statisticsTime"));
            sb.append(" and t.begin_time >= ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("sourceId"))) {
            params.add(paramMap.get("sourceId"));
            sb.append(" and t.source_id = ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmLevel"))) {
            params.add(Integer.parseInt(paramMap.get("alarmLevel")));
            sb.append(" and b.level = ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("status"))) {
            params.add(paramMap.get("status"));
            sb.append(" and t.deal_state = ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(paramMap.get("alarmType"));
            sb.append(" and t.alarm_type = ?");
        }
        if (StringUtils.isBlank(paramMap.get("beginTime"))) {
            paramMap.put("beginTime", DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("beginTime"));
        sb.append(" and t.begin_time > ? ");
        if (StringUtils.isBlank(paramMap.get("endTime"))) {
            paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("endTime"));
        sb.append(" and t.end_time < ?  group by t.alarm_type ");
        sb.append(" union all ");
        sb.append(" select b.id as alarmType,count(1) from dmu_alarm_info t left join tb_alarm_type b on t.alarm_type = b.type_id where 1=1  ");
        if (StringUtils.isNotBlank(paramMap.get("statisticsTime"))) {
            params.add(paramMap.get("statisticsTime"));
            sb.append(" and t.begin_time >= ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("sourceId"))) {
            params.add(paramMap.get("sourceId"));
            sb.append(" and t.source_id = ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmLevel"))) {
            params.add(Integer.parseInt(paramMap.get("alarmLevel")));
            sb.append(" and b.level = ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("status"))) {
            params.add(paramMap.get("status"));
            sb.append(" and t.deal_state = ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(paramMap.get("alarmType"));
            sb.append(" and b.id = ?");
        }
        if (StringUtils.isBlank(paramMap.get("beginTime"))) {
            paramMap.put("beginTime", DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("beginTime"));
        sb.append(" and t.begin_time > ? ");
        if (StringUtils.isBlank(paramMap.get("endTime"))) {
            paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("endTime"));
        sb.append(" and t.end_time < ?  group by b.id  ");
        sb.append("  ) a order by alarmType ");
        return this.getFivs_r().findSQL(sb.toString(), params.toArray());
    }

    public List<Map<String, Object>> findByNoPageWithExcel(PageObject pageObject) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from (select t.id as id,t.source_id as sourceId,t.alarm_type as alarmType,t.name as name,t.begin_time as beginTime,"
                + "t.end_time as endTime,t.state as state,t.deal_state as dealState,"
                + "t.deal_user_id as dealUserId,t.deal_time as dealTime,t.description as description,t.memo as memo,concat(b.level,'') as alarmLevel,dt.name as alarmTypeName,dt2.name as alarmLevelName  from alarm_info t "
                + "left join tb_alarm_type b on t.alarm_type = b.id  "
                + "left join (select * from t_dict where pre_id = ?) dt on t.alarm_type = dt.value "
                + "left join (select * from t_dict where pre_id = ?) dt2 on b.level = dt2.value where 1 = 1 ");

        List params = new ArrayList();
        params.add(TDict.ALARM_TYPE);
        params.add(TDict.ALARM_LEVEL);
        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("sourceId"))) {
            params.add(paramMap.get("sourceId"));
            sb.append("and t.source_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("dealState"))) {
            params.add(paramMap.get("dealState"));
            sb.append("and t.deal_state = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("name"))) {
            params.add("%" + paramMap.get("name") + "%");
            sb.append("and t.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(Integer.valueOf(paramMap.get("alarmType")));
            sb.append("and t.alarm_type = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmLevel"))) {
            params.add(Integer.valueOf(paramMap.get("alarmLevel")));
            sb.append("and b.level = ? ");
        }
        if (StringUtils.isBlank(paramMap.get("beginTime"))) {
            paramMap.put("beginTime", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("beginTime"));
        sb.append("and t.begin_time > ? ");
        if (StringUtils.isBlank(paramMap.get("endTime"))) {
            paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("endTime"));
        sb.append("and t.end_time < ? ");
        sb.append(" UNION ALL ");
        sb.append("SELECT o.id AS id,o.source_id AS sourceId,tb.id AS alarmType,o.name AS name,o.begin_time AS beginTime,o.end_time AS end_time,o.state AS state,o.deal_state AS dealState, o.deal_user_id AS dealUserId,o.deal_time AS dealTime,");
        sb.append("o.description as description,o.memo as memo,o.alarm_level as alarmLevel,dt.name as alarmTypeName,dt2.name as alarmLevelName from dmu_alarm_info o ");
        sb.append("Left Join  tb_alarm_type tb on o.alarm_type = tb.type_id ");
        sb.append("left join (select * from t_dict where pre_id = ?) dt on tb.id = dt.value ");
        sb.append("left join (select * from t_dict where pre_id = ?) dt2 on tb.level = dt2.value where 1 = 1 ");
        params.add(TDict.ALARM_TYPE);
        params.add(TDict.ALARM_LEVEL);
        if (StringUtils.isNotBlank(paramMap.get("sourceId"))) {
            params.add(paramMap.get("sourceId"));
            sb.append("and o.source_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("dealState"))) {
            params.add(paramMap.get("dealState"));
            sb.append("and o.deal_state = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("name"))) {
            params.add("%" + paramMap.get("name") + "%");
            sb.append("and o.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(Integer.valueOf(paramMap.get("alarmType")));
            sb.append("and tb.id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmLevel"))) {
            params.add(Integer.valueOf(paramMap.get("alarmLevel")));
            sb.append("and tb.level = ? ");
        }
        if (StringUtils.isBlank(paramMap.get("beginTime"))) {
            paramMap.put("beginTime", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("beginTime"));
        sb.append("and o.begin_time > ? ");
        if (StringUtils.isBlank(paramMap.get("endTime"))) {
            paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("endTime"));
        sb.append("and o.end_time < ? ");
        sb.append(") a order by a.endTime desc");
        List list = this.getFivs_r().findSQLToMap(sb.toString(), params.toArray());
        return list;
    }
}
