package com.beyeon.nvss.dmu.model.bpo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.DmuAlarmRes;
import com.beyeon.nvss.dmu.model.dao.DmuAlarmResDao;

@Component("dmuAlarmResBpo")
public class DmuAlarmResBpo {
	private Logger logger =LoggerFactory.getLogger(this.getClass());
	private DmuAlarmResDao dmuAlarmResDao;
	
	public DmuAlarmResDao getDmuAlarmResDao() {
		return dmuAlarmResDao;
	}
	public void setDmuAlarmResDao(DmuAlarmResDao dmuAlarmResDao) {
		this.dmuAlarmResDao = dmuAlarmResDao;
	}

	public void save (DmuAlarmRes info){
		this.dmuAlarmResDao.saveFivs(info);
	}
	
	public void findAlarmInfo(PageObject pageObject) {
		this.dmuAlarmResDao.find(pageObject);
	}
	
	public void delete(String[] ids) {
		this.dmuAlarmResDao.delete(ids);
	}
	
	public DmuAlarmRes findByIdAndType(String id,String type){
		return this.dmuAlarmResDao.findByIdAndType(id,type);
	}
	
	public void update(DmuAlarmRes res){
		this.dmuAlarmResDao.updateFivs(res);
	}
}
