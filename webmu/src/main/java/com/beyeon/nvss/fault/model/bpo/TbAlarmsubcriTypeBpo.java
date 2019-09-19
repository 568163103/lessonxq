package com.beyeon.nvss.fault.model.bpo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.DmuAlarmType;
import com.beyeon.hibernate.fivsdb.TbAlarmsubcriType;
import com.beyeon.nvss.fault.model.dao.TbAlarmsubcriTypeDao;
import com.beyeon.nvss.fault.model.dto.TbAlarmsubcriTypeDto;
@Cache(cacheName = "告警策略")
@Component("tbAlarmsubcriTypeBpo")
public class TbAlarmsubcriTypeBpo  {
private TbAlarmsubcriTypeDao tbAlarmsubcriTypeDao;
	
	public void setTbAlarmsubcriTypeDao(TbAlarmsubcriTypeDao tbAlarmsubcriTypeDao) {
		this.tbAlarmsubcriTypeDao = tbAlarmsubcriTypeDao;
	}

	public TbAlarmsubcriTypeDto get(String mid) {
		TbAlarmsubcriTypeDto tbAlarmsubcriTypeDto = new TbAlarmsubcriTypeDto();
		tbAlarmsubcriTypeDto.setTbAlarmsubcriType(this.tbAlarmsubcriTypeDao.getFivs(TbAlarmsubcriType.class, mid));
		return tbAlarmsubcriTypeDto;
	}
	
	public void save(TbAlarmsubcriTypeDto tbAlarmsubcriTypeDto) {
		TbAlarmsubcriType tbAlarmsubcriType = tbAlarmsubcriTypeDto.getTbAlarmsubcriType();
		if (StringUtils.isBlank(tbAlarmsubcriType.getEndTime())){
			tbAlarmsubcriType.setEndTime("23:59:59");
		}
		if (StringUtils.isBlank(tbAlarmsubcriType.getBeginTime())){
			tbAlarmsubcriType.setBeginTime("00:00:00");
		}
		this.tbAlarmsubcriTypeDao.saveFivs(tbAlarmsubcriType);
	}
	
	public void update(TbAlarmsubcriTypeDto tbAlarmsubcriTypeDto) {
		this.tbAlarmsubcriTypeDao.updateFivs(tbAlarmsubcriTypeDto.getTbAlarmsubcriType());
	}

	public void delete(String[] ids) {
		this.tbAlarmsubcriTypeDao.delete(ids);
	}

	public void find(PageObject pageObject) {
		this.tbAlarmsubcriTypeDao.find(pageObject);
	}
	
	public TbAlarmsubcriType findById(String id) {
		return this.tbAlarmsubcriTypeDao.findById(id);
	}
	
	public List findAllTbAlarmsubcriType() {
		return this.tbAlarmsubcriTypeDao.findAllTbAlarmsubcriType();
	}
	
	@Cache(type = Cache.REFRESH,cacheName = "全部告警类型",refreshExpre = Cache.Bm5)
	public void findDmuAlarmType(){
		List<Map> list = this.tbAlarmsubcriTypeDao.getAlarmType();
		for (Map o : list){		
			TbAlarmsubcriType.getObjectMap().put((String)o.get("alarmType"), (String)o.get("name"));
		}
	}	
	
}
