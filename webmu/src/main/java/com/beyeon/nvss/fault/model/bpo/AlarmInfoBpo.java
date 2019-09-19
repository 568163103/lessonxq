package com.beyeon.nvss.fault.model.bpo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.AlarmInfo;
import com.beyeon.hibernate.fivsdb.AlarmType;
import com.beyeon.nvss.fault.model.dao.AlarmInfoDao;
import com.beyeon.nvss.fault.model.dto.AlarmInfoDto;

@Component("alarmInfoBpo")
public class AlarmInfoBpo {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AlarmInfoDao alarmInfoDao;

    public void setAlarmInfoDao(AlarmInfoDao alarmInfoDao) {
        this.alarmInfoDao = alarmInfoDao;
    }

    public AlarmInfoDto get(String mid) {
        AlarmInfoDto alarmInfoDto = new AlarmInfoDto();
        alarmInfoDto.setAlarmInfo(this.alarmInfoDao.getFivs(AlarmInfo.class, Integer.parseInt(mid)));
        return alarmInfoDto;
    }

    public void save(Object object) {
        if (object instanceof List)
            this.alarmInfoDao.saveAllFivs((List) object);
        else
            this.alarmInfoDao.saveFivs(object);
    }

    public void findAlarmInfo(PageObject pageObject) {
        this.alarmInfoDao.findAlarmInfo(pageObject);
    }

    public void handleAlarm(String status, String uid, String memo, String id, String type) {
        this.alarmInfoDao.handleAlarm(status, uid, memo, id, type);
    }

    public List findAlarmInfoStatistics(Map<String, String> params) {
        return this.alarmInfoDao.findAlarmInfoStatistics(params);
    }

    public ArrayList<ArrayList<String>> findExcelFieldData(
            PageObject pageObject) {
        ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
        // 存放excel的内容的所有数据
        List<Map<String, Object>> list = alarmInfoDao
                .findByNoPageWithExcel(pageObject);

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                // 存放每一行的数据
                Map<String, Object> alarmInfo = (Map<String, Object>) list.get(i);
                // 将每一行的数据，放置到ArrayList<WmuOperationLog>
                if (alarmInfo != null) {
                    ArrayList<String> data = new ArrayList<String>();
                    int dealState = (int)alarmInfo.get("dealState");
                    String dealStateZH = "";
                    if (dealState == 0 ){
                        dealStateZH = "待确认";
                    }else if (dealState == 1){
                        dealStateZH = "处理中";
                    }else if (dealState == 2){
                        dealStateZH = "待归档";
                    }else if (dealState == 3){
                        dealStateZH = "已归档";
                    }
                    data.add(String.valueOf(i + 1));
                    data.add(String.valueOf(alarmInfo.get("sourceId")));
                    data.add(String.valueOf(alarmInfo.get("name")));
                    data.add(String.valueOf(alarmInfo.get("alarmTypeName")));
                    data.add(String.valueOf(alarmInfo.get("beginTime")));
                    data.add(String.valueOf(alarmInfo.get("endTime")));
                    data.add(dealStateZH);
                    data.add(String.valueOf(alarmInfo.get("dealUserId")));
                    data.add(String.valueOf(alarmInfo.get("dealTime")));
                    data.add(String.valueOf(alarmInfo.get("memo")));
                    data.add(String.valueOf(alarmInfo.get("description")));
                    fieldData.add(data);
                }
            }
        }
        return fieldData;
    }

    public ArrayList<ArrayList<String>> findExcelFieldData2(
            Map<String, String> paramMap) {
        ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
        // 存放excel的内容的所有数据
        List<Object[]> list = alarmInfoDao.findAlarmInfoStatistics(paramMap);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                // 存放每一行的数据
                Object[] alarmInfo = list.get(i);
                // 将每一行的数据，放置到ArrayList<WmuOperationLog>
                if (alarmInfo != null) {
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(String.valueOf(i + 1));
                    data.add(AlarmType.getTypeName(alarmInfo[0].toString()));
                    data.add(alarmInfo[1].toString());
                    fieldData.add(data);
                }
            }
        }
        return fieldData;
    }
}
