package com.beyeon.nvss.fault.model.bpo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.Channel;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.hibernate.fivsdb.TbAlarmType;
import com.beyeon.nvss.fault.model.dao.AlarmLevelDao;

@Cache(cacheName="告警类型")
@Component("alarmLevelBpo")
public class AlarmLevelBpo {
	private Logger logger =LoggerFactory.getLogger(this.getClass());
	private AlarmLevelDao alarmLevelDao;

	public TbAlarmType get(String mid) {
		return this.alarmLevelDao.getFivs(TbAlarmType.class, mid);
	}
	
	public void setAlarmLevelDao(AlarmLevelDao alarmLevelDao) {
		this.alarmLevelDao = alarmLevelDao;
	}

	public void findAlarmLevel(PageObject pageObject,String[] ids) {
		this.alarmLevelDao.findAlarmLevel(pageObject,ids);
	}
	
	public void update(TbAlarmType alarmType) {
		this.alarmLevelDao.updateFivs(alarmType);
	}
	
	@Cache(type = Cache.REFRESH,cacheName = "告警类型",refreshExpre = Cache.Bm5)
	public void findAlarmType(){
		List<TbAlarmType> list = alarmLevelDao.getAllFivs(TbAlarmType.class);
		Map<String,String> currObjectlMap = new HashMap<String, String>();
		for (TbAlarmType type : list) {
			currObjectlMap.put(type.getId(),type.getTypeId());
		}
		TbAlarmType.setObjectMap(currObjectlMap);
		
		Map<String,String> currObjectlMap1 = new HashMap<String, String>();
		for (TbAlarmType type : list) {
			currObjectlMap1.put(type.getId(),String.valueOf(type.getLevel()));
		}
		TbAlarmType.setLevelMap(currObjectlMap1);
		
		Map<String,String> currObjectlMap2 = new HashMap<String, String>();
		Collection<AutoCompleteDto> list2 = DictBpo.find(TDict.ALARM_LEVEL);
		Iterator<AutoCompleteDto> it =list2.iterator();
		while (it.hasNext()) {
			AutoCompleteDto s = it.next();
			currObjectlMap2.put(s.getValue(),s.getLabel());
		}
		
		Map<String,String> currObjectlMap3 = new HashMap<String, String>();
		for (TbAlarmType type : list) {
			currObjectlMap3.put(type.getId(),currObjectlMap2.get(String.valueOf(type.getLevel())));
		}
		TbAlarmType.setLevelNameMap(currObjectlMap3);
	}
	
}
