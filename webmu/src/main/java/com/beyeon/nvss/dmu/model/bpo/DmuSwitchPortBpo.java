package com.beyeon.nvss.dmu.model.bpo;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.SwitchPortInfo;
import com.beyeon.nvss.dmu.model.dao.DmuSwitchPortDao;
import com.beyeon.nvss.dmu.model.dto.DmuSwitchPortDto;
import com.beyeon.nvss.external.model.dto.ResShieldPlanDto;

@Component("dmuSwitchPortBpo")
public class DmuSwitchPortBpo {
	private Logger logger =LoggerFactory.getLogger(this.getClass());
	private DmuSwitchPortDao dmuSwitchPortDao;
	
	public DmuSwitchPortDao getDmuSwitchPortDao() {
		return dmuSwitchPortDao;
	}
	public void setDmuSwitchPortDao(DmuSwitchPortDao dmuSwitchPortDao) {
		this.dmuSwitchPortDao = dmuSwitchPortDao;
	}
	public DmuSwitchPortDto get(String id) {
		DmuSwitchPortDto dmuSwitchPortDto = new DmuSwitchPortDto();
		dmuSwitchPortDto.setDmuSwitchPort(this.dmuSwitchPortDao.getFivs(SwitchPortInfo.class, Integer.parseInt(id)));
		return dmuSwitchPortDto;
	}
	public void save (SwitchPortInfo info){
		this.dmuSwitchPortDao.saveFivs(info);
	}
	public void update (SwitchPortInfo info){
		this.dmuSwitchPortDao.updateFivs(info);
	}
	public void find(PageObject pageObject) {
		this.dmuSwitchPortDao.find(pageObject);
	}
	public void delete(String[] ids) {
		this.dmuSwitchPortDao.delete(ids);
	}
	
	public boolean checkSelf(String serverId){
		return this.dmuSwitchPortDao.checkSelf(serverId);
	}
	public List<HashMap<String,Object>> findList(){
		return this.dmuSwitchPortDao.findList();
	}
	
	public List<HashMap<String,Object>> findListByStateRecord(){
		return this.dmuSwitchPortDao.findListByStateRecord();
	}
	
	public SwitchPortInfo findBySwitchIdAndPort(String switchid,Integer port){
		return this.dmuSwitchPortDao.findBySwitchIdAndPort(switchid, port);
	}
	
	public String findIpByPort(String switchid,Integer port){
		return this.dmuSwitchPortDao.findIpByPort(switchid, port);
	}

}
