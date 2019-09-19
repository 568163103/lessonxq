package com.beyeon.nvss.dmu.model.bpo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.AlarmType;
import com.beyeon.hibernate.fivsdb.DmuAlarmInfo;
import com.beyeon.hibernate.fivsdb.DmuAlarmType;
import com.beyeon.hibernate.fivsdb.DmuAlarmInfo;
import com.beyeon.nvss.dmu.model.dao.DmuAlarmInfoDao;
import com.beyeon.nvss.dmu.model.dto.DmuAlarmInfoDto;

@Component("dmuAlarmInfoBpo")
public class DmuAlarmInfoBpo {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DmuAlarmInfoDao dmuAlarmInfoDao;

    public DmuAlarmInfoDto get(String mid) {
        DmuAlarmInfoDto dmuAlarmInfoDto = new DmuAlarmInfoDto();
        dmuAlarmInfoDto.setDmuAlarmInfo(this.dmuAlarmInfoDao.getFivs(DmuAlarmInfo.class, Integer.parseInt(mid)));
        return dmuAlarmInfoDto;
    }

    public DmuAlarmInfoDao getDmuAlarmInfoDao() {
        return dmuAlarmInfoDao;
    }

    public void setDmuAlarmInfoDao(DmuAlarmInfoDao dmuAlarmInfoDao) {
        this.dmuAlarmInfoDao = dmuAlarmInfoDao;
    }

    public void save(DmuAlarmInfo info) {
        this.dmuAlarmInfoDao.saveFivs(info);
    }

    public void findAlarmInfo(PageObject pageObject) {
        this.dmuAlarmInfoDao.findAlarmInfo(pageObject);
    }

    public List findAlarmInfoStatistics(Map<String, String> params) {
        return this.dmuAlarmInfoDao.findAlarmInfoStatistics(params);
    }

    public List findByParams(String sourceId, String alarmType, String beginTime) {
        return this.dmuAlarmInfoDao.findByParams(sourceId, alarmType, beginTime);
    }

    public List<DmuAlarmInfo> findNoneReportAlarm() {
        return this.dmuAlarmInfoDao.findNoneReportAlarm();
    }

    public void updateState(Integer id, Integer state, String status) {
        this.dmuAlarmInfoDao.updateState(id, state, status);
    }

    public void updateStautus(Integer id, String status) {
        this.dmuAlarmInfoDao.updateStautus(id, status);
    }

    public ArrayList<ArrayList<String>> findExcelFieldData(
            PageObject pageObject) {
        ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
        // 存放excel的内容的所有数据
        List<DmuAlarmInfo> list = dmuAlarmInfoDao
                .findByNoPageWithExcel(pageObject);

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                // 存放每一行的数据
                DmuAlarmInfo dmuAlarmInfo = (DmuAlarmInfo) list.get(i);
                // 将每一行的数据，放置到ArrayList<WmuOperationLog>
                if (dmuAlarmInfo != null) {
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(String.valueOf(i + 1));
                    data.add(dmuAlarmInfo.getSourceId());
                    data.add(dmuAlarmInfo.getName());
                    data.add(dmuAlarmInfo.getDmuAlarmTypeName());
                    data.add(dmuAlarmInfo.getBeginTime());
                    data.add(dmuAlarmInfo.getEndTime());
                    data.add(dmuAlarmInfo.getStateZh());
                    data.add(dmuAlarmInfo.getDescription());

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
        List<Object[]> list = dmuAlarmInfoDao.findAlarmInfoStatistics(paramMap);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                // 存放每一行的数据
                Object[] alarmInfo = list.get(i);
                // 将每一行的数据，放置到ArrayList<WmuOperationLog>
                if (alarmInfo != null) {
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(String.valueOf(i + 1));
                    data.add(DmuAlarmType.getTypeName(alarmInfo[0].toString()));
                    data.add(alarmInfo[1].toString());
                    fieldData.add(data);
                }
            }
        }
        return fieldData;
    }

    public void handleAlarm(String status, String id) {
        this.dmuAlarmInfoDao.handleAlarm(status, id);
    }
}
