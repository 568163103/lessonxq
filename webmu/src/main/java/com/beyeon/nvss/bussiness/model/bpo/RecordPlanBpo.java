package com.beyeon.nvss.bussiness.model.bpo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.RecordPlan;
import com.beyeon.nvss.bussiness.model.dao.RecordPlanDao;

@Component("recordPlanBpo")
public class RecordPlanBpo extends BaseBpo {
	private RecordPlanDao recordPlanDao;
	
	public void setRecordPlanDao(RecordPlanDao recordPlanDao) {
		this.recordPlanDao = recordPlanDao;
	}

	public RecordPlan get(String name) {
		RecordPlan recordPlan = this.recordPlanDao.getFivs(RecordPlan.class, name);
		return recordPlan;
	}
	
	public void save(RecordPlan recordPlan) {
		this.recordPlanDao.saveFivs(recordPlan);
	}
	
	public void update(RecordPlan recordPlan) {
		this.recordPlanDao.updateFivs(recordPlan);
	}

	public void find(PageObject pageObject) {
		this.recordPlanDao.find(pageObject);
	}

	public void delete(String[] names) {
		this.recordPlanDao.deleteByName(names);
	}

	public boolean checkUnique(String attrName,String value,String id) {
		return this.recordPlanDao.checkRecordPlanUnique(id,value);
	}

	public List findRecordPlanName() {
		return this.recordPlanDao.findRecordPlanName();
	}
}
