package com.beyeon.nvss.dmu.model.bpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.SwitchStateRecord;
import com.beyeon.hibernate.fivsdb.SwitchStateRecordPort;
import com.beyeon.nvss.dmu.model.dao.SwitchStateRecordDao;
import com.beyeon.nvss.dmu.model.dto.SwitchStateRecordDto;

@Component("switchStateRecordBpo")
public class SwitchStateRecordBpo extends BaseBpo {
	private SwitchStateRecordDao switchStateRecordDao;
	
	public void setSwitchStateRecordDao(SwitchStateRecordDao switchStateRecordDao) {
		this.switchStateRecordDao = switchStateRecordDao;
	}

	public SwitchStateRecordDto get(String mid) {
		SwitchStateRecordDto switchStateRecordDto = new SwitchStateRecordDto();
		switchStateRecordDto.setSwitchStateRecord(this.switchStateRecordDao.getFivs(SwitchStateRecord.class, mid));
		return switchStateRecordDto;
	}
	
	public void save(SwitchStateRecordDto switchStateRecordDto) {
		if (switchStateRecordDto!=null && switchStateRecordDto.getSwitchStateRecord()!=null){
			String switchid = switchStateRecordDto.getSwitchStateRecord().getSwitchid();
			SwitchStateRecord record = switchStateRecordDao.findSwitchStateRecord(switchid);
			if (record==null || !record.getRecordTime().equals(switchStateRecordDto.getSwitchStateRecord().getRecordTime())){
				this.switchStateRecordDao.saveFivs(switchStateRecordDto.getSwitchStateRecord());
				Integer recordid = switchStateRecordDto.getSwitchStateRecord().getId();
				List<SwitchStateRecordPort> portList = switchStateRecordDto.getSwitchStateRecordPort();
				for (SwitchStateRecordPort port :portList){
					port.setRecordid(recordid);		
					this.switchStateRecordDao.saveFivs(port);
				}
			}
		}
		
	}

	public void delete(String[] ids) {
		this.switchStateRecordDao.delete(ids);
	}
	
	public void delete(String date) {
		this.switchStateRecordDao.delete(date);
	}

	public void find(PageObject pageObject) {
		this.switchStateRecordDao.find(pageObject);
	}
	
	public List findRecordBySwitchId(String switchId) {
		return this.switchStateRecordDao.findRecord(switchId);
	}
	
	public SwitchStateRecord findSwitchStateRecord(String resId){
		return this.switchStateRecordDao.findSwitchStateRecord(resId);
	}
	
	public SwitchStateRecordDto findSwitchStateRecordById(String resId){
		SwitchStateRecordDto dto = new SwitchStateRecordDto();
		SwitchStateRecord record = this.switchStateRecordDao.findSwitchStateRecord(resId);
		Integer id = record.getId();
		List port = this.switchStateRecordDao.getPortList(id);
		dto.setSwitchStateRecord(record);
		dto.setSwitchStateRecordPort(port);
		return dto;
	}
	public List<SwitchStateRecord> findRecordListByParam(Map<String, Object> paramMap){
		return this.switchStateRecordDao.findRecordListByParam(paramMap);
	}
	public SwitchStateRecord findRecordByParam(Map<String, Object> paramMap){
		return this.switchStateRecordDao.findRecordByParam(paramMap);
	}
	public SwitchStateRecord getRecordById(String id ) {
		return this.switchStateRecordDao.getRecordById(id);
	}
	public List getPort(Integer id ) {
		return this.switchStateRecordDao.getPortList(id);
	}
	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<SwitchStateRecord> list = switchStateRecordDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 存放每一行的数据
				SwitchStateRecord switc = (SwitchStateRecord) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (switc != null) {
					
				}
			}
		}
		return fieldData;
	}

	@Override
	public boolean checkUnique(String attrName, String value, String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<HashMap<String,String>> findState(String resId){
		return switchStateRecordDao.findState(resId);
	}
	
	public void save2(SwitchStateRecordDto switchStateRecordDto) {
		SwitchStateRecord record = switchStateRecordDto.getSwitchStateRecord();
		record.setRecordTime(DateUtil.getTime());
		this.switchStateRecordDao.saveFivs(switchStateRecordDto.getSwitchStateRecord());
		Integer recordid = switchStateRecordDto.getSwitchStateRecord().getId();
		List<SwitchStateRecordPort> portList = new ArrayList<SwitchStateRecordPort>() ;				
		for (SwitchStateRecordPort port :switchStateRecordDto.getSwitchStateRecordPort()){
			port.setRecordid(recordid);		
			portList.add(port);
		}
		this.switchStateRecordDao.saveAllFivs(portList);
			
		
	}
}
