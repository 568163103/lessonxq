package com.beyeon.nvss.bussiness.model.bpo;

import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.AlarmPreScheme;
import com.beyeon.nvss.bussiness.model.dao.AlarmPreSchemeDao;

@Component("alarmPreSchemeBpo")
public class AlarmPreSchemeBpo {
	private AlarmPreSchemeDao alarmPreSchemeDao;

	public void setAlarmPreSchemeDao(AlarmPreSchemeDao alarmPreSchemeDao) {
		this.alarmPreSchemeDao = alarmPreSchemeDao;
	}

	public void save(AlarmPreScheme alarmPreScheme) {
		this.alarmPreSchemeDao.saveFivs(alarmPreScheme);
	}

	public void update(AlarmPreScheme alarmPreScheme,AlarmPreScheme oldAlarmPreScheme) {
		this.alarmPreSchemeDao.saveFivs(alarmPreScheme);
		this.alarmPreSchemeDao.delete(new Object[]{oldAlarmPreScheme.getAlarmType(),oldAlarmPreScheme.getSourceId(),oldAlarmPreScheme.getSchemeId()});
	}

	public void delete(String[] ids) {
		for (String id : ids) {
			this.alarmPreSchemeDao.delete(new Object[]{Integer.valueOf(id.split(",")[0]),id.split(",")[1],Integer.valueOf(id.split(",")[2])});
		}
	}

	public void find(PageObject pageObject) {
		this.alarmPreSchemeDao.find(pageObject);
	}

}
