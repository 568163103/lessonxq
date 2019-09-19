package com.beyeon.nvss.dmu.model.bpo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.DmuAlarmRes;
import com.beyeon.hibernate.fivsdb.DmuAlarmType;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.nvss.dmu.model.dao.DmuAlarmLevelDao;

@Cache(cacheName="网管告警类型")
@Component("dmuAlarmLevelBpo")
public class DmuAlarmLevelBpo {
	private Logger logger =LoggerFactory.getLogger(this.getClass());
	private DmuAlarmLevelDao dmuAlarmLevelDao;

	public DmuAlarmType get(String mid) {
		return this.dmuAlarmLevelDao.getFivs(DmuAlarmType.class, mid);
	}
	
	public void setDmuAlarmLevelDao(DmuAlarmLevelDao dmuAlarmLevelDao) {
		this.dmuAlarmLevelDao = dmuAlarmLevelDao;
	}

	public void findDmuAlarmLevel(PageObject pageObject) {
		this.dmuAlarmLevelDao.findAlarmLevel(pageObject);
	}
	
	public List findDmuAlarmLevel() {
		return this.dmuAlarmLevelDao.findAlarmLevel();
	}
	
	public void update(DmuAlarmType alarmType) {
		this.dmuAlarmLevelDao.updateFivs(alarmType);
	}
	
	@Cache(type = Cache.REFRESH,cacheName = "网管告警类型",refreshExpre = Cache.Bm5)
	public void findDmuAlarmType(){
		List<DmuAlarmType> list = this.dmuAlarmLevelDao.findAlarmLevel();
		for (DmuAlarmType o : list){		
			DmuAlarmType.getTypeMap().put(o.getId(), o.getName());
		}
	}	

}
