package com.beyeon.nvss.position.model.bpo;

import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.PositionCode;
import com.beyeon.nvss.position.model.dao.PositionDao;

@Component("positionBpo")
public class PositionBpo{

	private PositionDao positionDao;
	
	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	public PositionCode get(String mid) {
		return this.positionDao.getFivs(PositionCode.class, mid);
	}
	
	public void save(PositionCode position) {
        this.positionDao.saveFivs(position);
	}
	
	public void update(PositionCode position) {
        this.positionDao.updatePosition(position);
	}

	public void find(PageObject pageObject) {
		this.positionDao.find(pageObject);
	}
	
	public void delete(String[] ids){
		this.positionDao.delete(ids);
	}
		
}
