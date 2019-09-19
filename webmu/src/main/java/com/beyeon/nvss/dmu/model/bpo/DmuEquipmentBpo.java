package com.beyeon.nvss.dmu.model.bpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.EncoderStateRecord;
import com.beyeon.hibernate.fivsdb.ServerStateRecord;
import com.beyeon.hibernate.fivsdb.ServerStateRecordCpu;
import com.beyeon.hibernate.fivsdb.ServerStateRecordDisk;
import com.beyeon.hibernate.fivsdb.ServerStateRecordNetcard;
import com.beyeon.hibernate.fivsdb.SwitchStateRecord;
import com.beyeon.hibernate.fivsdb.SwitchStateRecordPort;
import com.beyeon.nvss.dmu.model.dao.DmuEquipmentDao;
import com.beyeon.nvss.dmu.model.dto.DmuEquipmentDto;
import com.beyeon.nvss.dmu.model.dto.ServerStateRecordDto;
import com.beyeon.nvss.dmu.model.dto.SwitchStateRecordDto;
@Cache(cacheName = "其他设备")
@Component("dmuEquipmentBpo")
public class DmuEquipmentBpo extends BaseBpo {
private DmuEquipmentDao dmuEquipmentDao;
private ServerStateRecordBpo serverStateRecordBpo;
private SwitchStateRecordBpo switchStateRecordBpo;
	
	public void setDmuEquipmentDao(DmuEquipmentDao dmuEquipmentDao) {
		this.dmuEquipmentDao = dmuEquipmentDao;
	}

	public DmuEquipmentDto get(String mid) {
		DmuEquipmentDto dmuEquipmentDto = new DmuEquipmentDto();
		dmuEquipmentDto.setDmuEquipment(this.dmuEquipmentDao.getFivs(DmuEquipment.class, mid));
		dmuEquipmentDto.setDiskStateRecord(this.dmuEquipmentDao.findDiskStateRecord(mid));
		dmuEquipmentDto.setServerStateRecord(findServerStateRecordById(mid));
		dmuEquipmentDto.setSwitchStateRecord(findSwitchStateRecordById(mid));
		dmuEquipmentDto.setEncoderStateRecord(this.dmuEquipmentDao.findEncoderStateRecord(mid));
		return dmuEquipmentDto;
	}
	
	public void save(DmuEquipmentDto dmuEquipmentDto) {
		this.dmuEquipmentDao.saveFivs(dmuEquipmentDto.getDmuEquipment());
	}
	
	public void update(DmuEquipmentDto dmuEquipmentDto) {
		this.dmuEquipmentDao.updateFivs(dmuEquipmentDto.getDmuEquipment());
	}

	public void delete(String[] ids) {
		this.dmuEquipmentDao.delete(ids);
	}
	
	public void deleteByMaster(String master) {
		this.dmuEquipmentDao.deleteByMaster(master);
	}

	public void find(PageObject pageObject) {
		this.dmuEquipmentDao.find(pageObject);
	}
	
	public DmuEquipment findById(String id) {
		return this.dmuEquipmentDao.findById(id);
	}
	
	public List findAllDmuEquipment() {
		return this.dmuEquipmentDao.findAllDmuEquipment();
	}
	public List<DmuEquipment> findByParam(Map<String, String> paramMap){
		return this.dmuEquipmentDao.findByParam(paramMap);
	}
	
	

	public boolean checkUnique(String attrName,String value,String id) {
		return true;
	}
	
	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<DmuEquipment> list = dmuEquipmentDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				// 存放每一行的数据
				DmuEquipment dmuEquipment = (DmuEquipment) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (dmuEquipment != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(i + 1));
					data.add(dmuEquipment.getId());
					data.add(dmuEquipment.getName());
					data.add(dmuEquipment.getType());
					data.add(dmuEquipment.getVersion());
					data.add(dmuEquipment.getIp());
					data.add(String.valueOf(dmuEquipment.getPort()));
					data.add(dmuEquipment.getMac());
					data.add(dmuEquipment.getCorp());
					data.add(dmuEquipment.getPos());
					data.add(dmuEquipment.getRemark());
					data.add(dmuEquipment.getStatusZh());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}
	
	public ArrayList<ArrayList<ArrayList<String>>> findExcelFieldDataServer(String id,HashMap<String,String> map) {
		ArrayList<ArrayList<ArrayList<String>>> fieldDataList = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> fieldData1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> fieldData2 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> fieldData3 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> fieldData4 = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<ServerStateRecord> list = new ArrayList<>();
		List cpuList = new ArrayList<>();
		List diskList = new ArrayList<>();
		List netcardList = new ArrayList<>();
		if (StringUtils.isNotBlank(id)){
			ServerStateRecord state = dmuEquipmentDao.findServerStateRecord(id);
			if (state != null ){
				 list.add(state);
				 Integer recordid = state.getId();
				 cpuList = dmuEquipmentDao.getCpuList(recordid);
				 diskList = dmuEquipmentDao.getDiskList(recordid);
				 netcardList = dmuEquipmentDao.getNetcardList(recordid);			 
			}
		}else{
			list = dmuEquipmentDao.findServerStateRecordList(map);
			for (ServerStateRecord record : list){
				 Integer recordid = record.getId();
				 List cpu = dmuEquipmentDao.getCpuList(recordid);
				 List disk = dmuEquipmentDao.getDiskList(recordid);
				 List netcard = dmuEquipmentDao.getNetcardList(recordid);	
				 cpuList.addAll(cpu);
				 diskList.addAll(disk);
				 netcardList.addAll(netcard);
			}
		}
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 存放每一行的数据
				ServerStateRecord serverStateRecord = (ServerStateRecord) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (serverStateRecord != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(serverStateRecord.getId()));
					data.add(serverStateRecord.getServerid());
					data.add(serverStateRecord.getSystemInfo());
					data.add(serverStateRecord.getDiskNum());
					data.add(serverStateRecord.getTotalMem());
					data.add(serverStateRecord.getUsedMem());
					data.add(serverStateRecord.getLeftMem());
					data.add(serverStateRecord.getCpuNum());
					data.add(serverStateRecord.getNetworkCard());
					data.add(serverStateRecord.getRecordTime());
					fieldData1.add(data);
				}
			}
		}
		if (diskList !=null && diskList.size()>0){
			for (int i = 0 ; i < diskList.size(); i ++){
				ServerStateRecordDisk disk =  (ServerStateRecordDisk) diskList.get(i);
				if (disk !=null){
					ArrayList<String> data = new ArrayList<String>();	
					data.add(String.valueOf(disk.getRecordid()));		
					data.add(disk.getDriver());					
					data.add(disk.getTotal());
					data.add(disk.getUsed());
					data.add(disk.getLeft());
					fieldData2.add(data);
				}
			}
		}
		if (cpuList != null && cpuList.size()>0 ){
			for (int i = 0 ; i < cpuList.size() ; i ++){
				ServerStateRecordCpu cpu = (ServerStateRecordCpu) cpuList.get(i);
				if (cpu != null){
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(cpu.getRecordid()));	
					data.add(cpu.getCpuPercent());					
					data.add(cpu.getCpuTemp());
					fieldData3.add(data);
				}
			}
		}
		
		if (netcardList != null && netcardList.size()>0){
			for (int i = 0 ; i < netcardList.size(); i ++){
				ServerStateRecordNetcard net =  (ServerStateRecordNetcard) netcardList.get(i);
				if (net != null){
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(net.getRecordid()));	
					data.add(net.getPort());					
					data.add(net.getPortState());
					data.add(net.getBandwidth());
					data.add(net.getDataFlow());
					data.add(net.getIp());
					data.add(net.getMac());
					fieldData4.add(data);
				}
			}
		}
		fieldDataList.add(fieldData1);
		fieldDataList.add(fieldData2);
		fieldDataList.add(fieldData3);
		fieldDataList.add(fieldData4);
		return fieldDataList;
	}
	
	public ArrayList<ArrayList<String>> findExcelFieldDataEncord(String id,HashMap<String,String> map) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<EncoderStateRecord> list = new ArrayList<>();
		if (StringUtils.isNotBlank(id)){
			EncoderStateRecord state = dmuEquipmentDao.findEncoderStateRecord(id);			
			if (state !=null )
				list.add(state);
		}else{
			list = dmuEquipmentDao.findEncoderStateRecordList(map);
		}
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 存放每一行的数据
				EncoderStateRecord record = (EncoderStateRecord) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (record != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(record.getId()));	
					data.add(record.getEncoderid());
					data.add(record.getCodeType());
					data.add(record.getStreamType());
					data.add(record.getMainStreamRateType());
					data.add(record.getMainStreamResolution());
					data.add(record.getMainStreamFrameRate());
					data.add(record.getMainStreamGOP());
					data.add(record.getChildStreamResolution());
					data.add(record.getRecordTime());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}
	
	public ArrayList<ArrayList<String>> findExcelFieldDataDisk(String id,HashMap<String,String> map) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		
		List<DiskStateRecord> list = new ArrayList<>();
		if (StringUtils.isNotBlank(id)){
			DiskStateRecord state = dmuEquipmentDao.findDiskStateRecord(id);
			if (state !=null )
				list.add(state);
		}else{
			list = dmuEquipmentDao.findDiskStateRecordList(map);
		}
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 存放每一行的数据
				DiskStateRecord record = (DiskStateRecord) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (record != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(record.getId()));	
					data.add(record.getDiskid());
					data.add(record.getTotalvolume());
					data.add(record.getUndistributed());
					data.add(record.getPortNum());
					data.add(record.getCpu());
					data.add(record.getFan());
					data.add(record.getBad());
					data.add(record.getState());
					data.add(record.getRecordTime());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}
	
	public ArrayList<ArrayList<ArrayList<String>>> findExcelFieldDataSwitch(String id,HashMap<String,String> map) {
		ArrayList<ArrayList<ArrayList<String>>> fieldDataList = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> fieldData1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> fieldData2 = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<SwitchStateRecord> list = new ArrayList<>();
		List portList = new ArrayList<>();
		
		if (StringUtils.isNotBlank(id)){
			SwitchStateRecord state = dmuEquipmentDao.findSwitchStateRecord(id);			
			if (state != null ){
				list.add(state);
				Integer recordid = state.getId();
				portList = dmuEquipmentDao.getPortList(recordid);		 
			}
		}else{
			 list = dmuEquipmentDao.findSwitchStateRecordList(map);
			 for (SwitchStateRecord r : list){
				 Integer recordid = r.getId();
				 List port = dmuEquipmentDao.getPortList(recordid);		
				 portList.addAll(port);
			 }
		}
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 存放每一行的数据
				SwitchStateRecord serverStateRecord = (SwitchStateRecord) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (serverStateRecord != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(serverStateRecord.getId()));	
					data.add(serverStateRecord.getSwitchid());
					data.add(serverStateRecord.getMemory());
					data.add(serverStateRecord.getCpu());
					data.add(serverStateRecord.getPortNum());
					data.add(serverStateRecord.getRecordTime());
					fieldData1.add(data);
				}
			}
		}
		
		
		if (portList != null && portList.size()>0){
			for (int i = 0 ; i < portList.size(); i ++){
				SwitchStateRecordPort net =  (SwitchStateRecordPort) portList.get(i);
				if (net != null){
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(net.getRecordid()));	
					data.add(net.getPort());					
					data.add(net.getType());
					data.add(net.getRate());
					data.add(net.getState());
					data.add(net.getIp());
					data.add(net.getLoss());
					fieldData2.add(data);
				}
			}
		}
		fieldDataList.add(fieldData1);
		fieldDataList.add(fieldData2);
		return fieldDataList;
	}
	public DiskStateRecord findDiskStateRecord(String id) {
		return this.dmuEquipmentDao.findDiskStateRecord(id);
	}
	
	public EncoderStateRecord findEncoderStateRecord(String id) {
		return this.dmuEquipmentDao.findEncoderStateRecord(id);
	}
	
	public ServerStateRecord findServerStateRecord(String id) {
		return this.dmuEquipmentDao.findServerStateRecord(id);
	}
	
	public SwitchStateRecord findSwitchStateRecord(String id) {
		return this.dmuEquipmentDao.findSwitchStateRecord(id);
	}
	
	public ServerStateRecordDto findServerStateRecordById(String serverid ) {
		ServerStateRecordDto  dto = new ServerStateRecordDto();
		ServerStateRecord record = this.dmuEquipmentDao.findServerStateRecord(serverid);
		if (record !=null){
			Integer id = record.getId();
			List cpu = this.dmuEquipmentDao.getCpuList(id);
			List disk = this.dmuEquipmentDao.getDiskList(id);
			List net = this.dmuEquipmentDao.getNetcardList(id);
			dto.setServerStateRecordCpu(cpu);
			dto.setServerStateRecordDisk(disk);
			dto.setServerStateRecordNetcard(net);
			dto.setServerStateRecord(record);
		}
		
		return dto;
	}
	
	public SwitchStateRecordDto findSwitchStateRecordById(String resId){
		SwitchStateRecordDto dto = new SwitchStateRecordDto();
		SwitchStateRecord record = this.dmuEquipmentDao.findSwitchStateRecord(resId);
		if (record !=null){
			Integer id = record.getId();		
			List port = this.dmuEquipmentDao.getPortList(id);
			dto.setSwitchStateRecordPort(port);
			dto.setSwitchStateRecord(record);
		}
		
		return dto;
	}
	public void findAllRecord(PageObject pageObject){
		this.dmuEquipmentDao.findAllRecord(pageObject);
	}
	
	public List findEquipment (Map<String,String> paramMap ,boolean self){
		if (self){
			return this.dmuEquipmentDao.findEquipmentOwn(paramMap);
		}else{
			return this.dmuEquipmentDao.findEquipmentOther(paramMap);
		}
	}
	public void saveServer(ServerStateRecordDto serverStateRecordDto) {
		this.dmuEquipmentDao.saveFivs(serverStateRecordDto);
	}
	
	public void saveDisk(DiskStateRecord diskStateRecord) {
		this.dmuEquipmentDao.saveFivs(diskStateRecord);
	}
	public void saveSwitch(SwitchStateRecordDto switchStateRecordDto) {
		this.dmuEquipmentDao.saveFivs(switchStateRecordDto);
	}
	public void saveEncoder(EncoderStateRecord encoderStateRecord) {
		this.dmuEquipmentDao.saveFivs(encoderStateRecord);
	}
}
