package com.beyeon.nvss.dmu.model.bpo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.EncoderStateRecord;
import com.beyeon.nvss.dmu.model.dao.EncoderStateRecordDao;
import com.beyeon.nvss.dmu.model.dto.EncoderStateRecordDto;

@Component("encoderStateRecordBpo")
public class EncoderStateRecordBpo extends BaseBpo {
	private EncoderStateRecordDao encoderStateRecordDao;
	
	public void setEncoderStateRecordDao(EncoderStateRecordDao encoderStateRecordDao) {
		this.encoderStateRecordDao = encoderStateRecordDao;
	}

	public EncoderStateRecordDto get(String mid) {
		EncoderStateRecordDto encoderStateRecordDto = new EncoderStateRecordDto();
		encoderStateRecordDto.setEncoderStateRecord(this.encoderStateRecordDao.getFivs(EncoderStateRecord.class, mid));
		return encoderStateRecordDto;
	}
	
	public void save(EncoderStateRecordDto encoderStateRecordDto) {
		this.encoderStateRecordDao.saveFivs(encoderStateRecordDto.getEncoderStateRecord());
	}

	public void delete(String[] ids) {
		this.encoderStateRecordDao.delete(ids);
	}
	
	public void delete(String date) {
		this.encoderStateRecordDao.delete(date);
	}

	public void find(PageObject pageObject) {
		this.encoderStateRecordDao.find(pageObject);
	}
	
	public List findRecordByEncoderId(String encoderId) {
		return this.encoderStateRecordDao.findRecord(encoderId);
	}
	public List<EncoderStateRecord> findRecordListByParam(Map<String, Object> paramMap){
		return this.encoderStateRecordDao.findRecordListByParam(paramMap);
	}
	public EncoderStateRecord findRecordByParam(Map<String, Object> paramMap){
		return this.encoderStateRecordDao.findRecordByParam(paramMap);
	}
	public EncoderStateRecord getRecordById(Integer id ) {
		return this.encoderStateRecordDao.getRecordById(id);
	}
	
	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<EncoderStateRecord> list = encoderStateRecordDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 存放每一行的数据
				EncoderStateRecord encoder = (EncoderStateRecord) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (encoder != null) {
					
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
	
	public String findServerIp(Integer serverType) {
		return encoderStateRecordDao.findServerIp(serverType);
	}

	public EncoderStateRecord findEncoderStateRecord(String resId) {
		// TODO Auto-generated method stub
		return encoderStateRecordDao.findEncoderStateRecord(resId);
	}
}
