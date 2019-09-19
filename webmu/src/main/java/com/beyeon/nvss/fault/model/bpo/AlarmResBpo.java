package com.beyeon.nvss.fault.model.bpo;

import java.util.ArrayList;
import java.util.List;

import com.beyeon.hibernate.fivsdb.Equipment;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.TbAlarmRes;
import com.beyeon.nvss.fault.model.dao.AlarmResDao;
import com.beyeon.nvss.fault.model.dto.AlarmResDto;

@Cache(cacheName = "告警资源")
@Component("alarmResBpo")
public class AlarmResBpo {
    private AlarmResDao alarmResDao;

    public void setAlarmResDao(AlarmResDao alarmResDao) {
        this.alarmResDao = alarmResDao;
    }

    public AlarmResDto get(Integer mid) {
        AlarmResDto alarmResDto = new AlarmResDto();
        alarmResDto.setAlarmRes(this.alarmResDao.getFivs(TbAlarmRes.class, mid));
        return alarmResDto;
    }

    public void save(AlarmResDto alarmResDto) {
        this.alarmResDao.saveFivs(alarmResDto.getAlarmRes());
    }

    public void update(AlarmResDto alarmResDto) {
        this.alarmResDao.updateFivs(alarmResDto.getAlarmRes());
    }

    public void delete(String[] ids) {
        this.alarmResDao.delete(ids);
    }

    public void deleteByMaster(String sid) {
        this.alarmResDao.deleteByMaster(sid);
    }

    public void find(PageObject pageObject) {
        this.alarmResDao.find(pageObject);
    }

    public TbAlarmRes findById(String id) {
        return this.alarmResDao.findById(id);
    }

    public TbAlarmRes findByResIdAndType(String resId, String type) {
        return this.alarmResDao.findByResIdAndType(resId, type);
    }

    public List findAllAlarmRes() {
        return this.alarmResDao.findAllAlarmRes();
    }

    public void findRes(PageObject pageObject) {
        this.alarmResDao.findRes(pageObject);
    }

    public List findDmuAlarmRes() {
        return this.alarmResDao.findDmuAlarmRes();
    }


    public ArrayList<ArrayList<String>> findExcelFieldData(
            PageObject pageObject) {
        ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
        // 存放excel的内容的所有数据
        List<TbAlarmRes> list = alarmResDao
                .findByNoPageWithExcel(pageObject);

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                // 存放每一行的数据
                TbAlarmRes tbAlarmRes = list.get(i);
                // 将每一行的数据，放置到ArrayList<WmuOperationLog>
                if (tbAlarmRes != null) {
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(String.valueOf(i + 1));
                    data.add(tbAlarmRes.getResId());
                    data.add(tbAlarmRes.getAlarmTypeName());
                    data.add(tbAlarmRes.getName());
                    data.add(tbAlarmRes.getSid());
                    fieldData.add(data);
                }
            }
        }
        return fieldData;
    }
}
