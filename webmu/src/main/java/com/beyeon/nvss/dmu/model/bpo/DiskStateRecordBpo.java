package com.beyeon.nvss.dmu.model.bpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.nvss.dmu.model.dao.DiskStateRecordDao;
import com.beyeon.nvss.dmu.model.dto.DiskStateRecordDto;

@Component("diskStateRecordBpo")
public class DiskStateRecordBpo extends BaseBpo {
	private DiskStateRecordDao diskStateRecordDao;
	
	public void setDiskStateRecordDao(DiskStateRecordDao diskStateRecordDao) {
		this.diskStateRecordDao = diskStateRecordDao;
	}

	public DiskStateRecordDto get(String mid) {
		DiskStateRecordDto diskStateRecordDto = new DiskStateRecordDto();
		diskStateRecordDto.setDiskStateRecord(this.diskStateRecordDao.getFivs(DiskStateRecord.class, mid));
		return diskStateRecordDto;
	}
	
	public void save(DiskStateRecordDto diskStateRecordDto) {
		if (diskStateRecordDto!=null && diskStateRecordDto.getDiskStateRecord()!=null){
			String diskid = diskStateRecordDto.getDiskStateRecord().getDiskid();
			DiskStateRecord record = diskStateRecordDao.findDiskStateRecord(diskid);
			if ((record==null || !record.getRecordTime().equals(diskStateRecordDto.getDiskStateRecord().getRecordTime())) 
					&& StringUtils.isNotBlank(diskStateRecordDto.getDiskStateRecord().getCpu())){
				this.diskStateRecordDao.saveFivs(diskStateRecordDto.getDiskStateRecord());
			}
		}
	}

	public void delete(String[] ids) {
		this.diskStateRecordDao.delete(ids);
	}
	
	public void delete(String date) {
		this.diskStateRecordDao.delete(date);
	}

	public void find(PageObject pageObject) {
		this.diskStateRecordDao.find(pageObject);
	}
	
	public List findRecordByDiskId(String diskId) {
		return this.diskStateRecordDao.findRecord(diskId);
	}
	
	public DiskStateRecord findDiskStateRecord(String resId){
		return this.diskStateRecordDao.findDiskStateRecord(resId);
	}
	public List<DiskStateRecord> findRecordListByParam(Map<String, Object> paramMap){
		return this.diskStateRecordDao.findRecordListByParam(paramMap);
	}
	
	public DiskStateRecord getRecordById(String id ) {
		return this.diskStateRecordDao.getRecordById(id);
	}
	
	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<DiskStateRecord> list = diskStateRecordDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 存放每一行的数据
				DiskStateRecord disk = (DiskStateRecord) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (disk != null) {
					
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
	
	public List<HashMap<String,Object>> findState(String resId){
		return diskStateRecordDao.findState(resId);
	}
}
