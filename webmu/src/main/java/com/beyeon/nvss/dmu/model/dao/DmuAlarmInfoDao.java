package com.beyeon.nvss.dmu.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.DmuAlarmInfo;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("dmuAlarmInfoDao")
public class DmuAlarmInfoDao extends NvsBaseDao {

    public void findAlarmInfo(PageObject pageObject) {
        boolean flag = getAlarmType();
        StringBuffer sb = new StringBuffer();
        List params = new ArrayList();
        params.add(TDict.ALARM_LEVEL);
        sb.append("select t.id,t.source_id as sourceId,t.name as t.name,t.alarm_type as alarmType,d.name as dmuAlarmTypeName ,t.begin_time as beginTime,t.end_time as endTime,t.alarm_level as alarmLevel,t.state as state,t.description,t.target_info as targetInfo,t.status,t.eventid,dt2.name as dmuAlarmLevelName from dmu_alarm_info t ");
        sb.append(" left join dmu_alarm_type d on t.alarm_type = d.id left join (select * from t_dict where pre_id = ?) dt2 on t.alarm_level = dt2.value ");
        if (flag) {
            sb.append(" LEFT JOIN tb_alarmsubcri_type tb on t.alarm_type = tb.alarm_type where (tb.alarm_type is null or (t.begin_time>=tb.begin_time and t.end_time <= tb.end_time)) ");
        } else {
            sb.append(" where 1 = 1 ");
        }

        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("sourceId"))) {
            params.add(paramMap.get("sourceId"));
            sb.append("and t.source_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("name"))) {
            params.add("%" + paramMap.get("name") + "%");
            sb.append("and t.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(paramMap.get("alarmType"));
            sb.append("and t.alarm_type = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmLevel"))) {
            params.add(Integer.parseInt(paramMap.get("alarmLevel")));
            sb.append("and t.alarm_level = ? ");
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
        sb.append("order by t.id desc ");
        this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
    }

    public List findAlarmInfoStatistics(Map<String, String> paramMap) {
        List params = new ArrayList();
        StringBuffer sb = new StringBuffer("select t.alarmType,count(*) from DmuAlarmInfo t where 1=1 ");
        if (StringUtils.isNotBlank(paramMap.get("statisticsTime"))) {
            params.add(paramMap.get("statisticsTime"));
            sb.append(" and t.beginTime >= ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("sourceId"))) {
            params.add(paramMap.get("sourceId"));
            sb.append(" and t.sourceId = ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmLevel"))) {
            params.add(Integer.parseInt(paramMap.get("alarmLevel")));
            sb.append(" and t.level = ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("status"))) {
            params.add(Integer.parseInt(paramMap.get("status")));
            sb.append(" and t.state = ?");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(paramMap.get("alarmType"));
            sb.append(" and t.alarmType = ?");
        }
        if (StringUtils.isBlank(paramMap.get("beginTime"))) {
            paramMap.put("beginTime", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("beginTime"));
        sb.append("and t.beginTime > ? ");
        if (StringUtils.isBlank(paramMap.get("endTime"))) {
            paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("endTime"));
        sb.append("and t.endTime < ? ");
        sb.append("  and t.state != 0 ");
        sb.append(" group by t.alarmType order by t.alarmType");
        ;
        return this.getFivs_r().find(sb.toString(), params.toArray());
    }

    public List findNoneReportAlarm() {
        String sb = "select t from DmuAlarmInfo t where status = '0'   ";
        return this.getFivs_r().find(sb, null);
    }

    public void updateStautus(Integer id, String status) {
        String sql = "update  dmu_alarm_info t  set  t.status = ? where t.id = ? ";
        this.getFivs_w().bulkUpdateSQL(sql, new Object[]{status, id});

    }

    public void updateState(Integer id, Integer state, String status) {
        String sql = "update  dmu_alarm_info t  set  t.state = ? ,status = ? where t.id = ? ";
        this.getFivs_w().bulkUpdateSQL(sql, new Object[]{state, status, id});

    }

    public List findByParams(String source_id, String alarm_type, String begin_time) {
        String sb = "select t from DmuAlarmInfo t where sourceId = ? and  alarmType = ? and beginTime = ?   ";
        return this.getFivs_r().find(sb, new Object[]{source_id, alarm_type, begin_time});
    }

    public List<DmuAlarmInfo> findByNoPageWithExcel(PageObject pageObject) {
        StringBuffer sb = new StringBuffer();
        sb.append("select t from DmuAlarmInfo t where 1 = 1   and t.state != 0 ");
        List params = new ArrayList();

        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("sourceId"))) {
            params.add(paramMap.get("sourceId"));
            sb.append("and t.sourceId = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("name"))) {
            params.add("%" + paramMap.get("name") + "%");
            sb.append("and t.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(paramMap.get("alarmType"));
            sb.append("and t.alarmType = ? ");
        }
        if (StringUtils.isBlank(paramMap.get("beginTime"))) {
            paramMap.put("beginTime", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("beginTime"));
        sb.append("and t.beginTime > ? ");
        if (StringUtils.isBlank(paramMap.get("endTime"))) {
            paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("endTime"));
        sb.append("and t.endTime < ? ");

        sb.append("order by t.id desc ");
        List list = this.getFivs_r().findList(sb.toString(), params.toArray());
        return list;
    }

    public boolean getAlarmType() {
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from dmu_alarm_type d INNER JOIN tb_alarmsubcri_type t on d.id = t.alarm_type ");
        List list = this.getFivs_r().findSQLToMap(sb.toString(), null);
        if (list != null && list.size() > 0) {
            flag = true;
        }
        return flag;
    }

    public void handleAlarm(String status, String id) {
        this.getFivs_w().bulkUpdateSQL("update dmu_alarm_info t set t.state = ? where t.id = ? ",
                new Object[]{Integer.parseInt(status), Integer.parseInt(id)});
    }
}
