package com.beyeon.nvss.dmu.model.bpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.ServerStateRecord;
import com.beyeon.hibernate.fivsdb.ServerStateRecordCpu;
import com.beyeon.hibernate.fivsdb.ServerStateRecordDisk;
import com.beyeon.hibernate.fivsdb.ServerStateRecordNetcard;
import com.beyeon.nvss.dmu.model.dao.ServerStateRecordDao;
import com.beyeon.nvss.dmu.model.dto.ServerStateRecordDto;

@Component("serverStateRecordBpo")
public class ServerStateRecordBpo extends BaseBpo {
	private ServerStateRecordDao serverStateRecordDao;
	
	public void setServerStateRecordDao(ServerStateRecordDao serverStateRecordDao) {
		this.serverStateRecordDao = serverStateRecordDao;
	}

	public ServerStateRecordDto get(Integer mid) {
		ServerStateRecordDto serverStateRecordDto = new ServerStateRecordDto();
		serverStateRecordDto.setServerStateRecord(this.serverStateRecordDao.getFivs(ServerStateRecord.class, mid));
		serverStateRecordDto.setServerStateRecordCpu(this.serverStateRecordDao.getCpuList(mid));
		serverStateRecordDto.setServerStateRecordDisk(this.serverStateRecordDao.getDiskList(mid));
		serverStateRecordDto.setServerStateRecordNetcard(this.serverStateRecordDao.getNetcardList(mid));
		return serverStateRecordDto;
	}
	
	public void save(ServerStateRecordDto serverStateRecordDto) {
		if (serverStateRecordDto!=null && serverStateRecordDto.getServerStateRecord()!=null){
			String resId = serverStateRecordDto.getServerStateRecord().getServerid();
			ServerStateRecord record = serverStateRecordDao.findServerStateRecord(resId);
			if (record ==null || !record.getRecordTime().equals(serverStateRecordDto.getServerStateRecord().getRecordTime())){
				this.serverStateRecordDao.saveFivs(serverStateRecordDto.getServerStateRecord());
				Integer recordid = serverStateRecordDto.getServerStateRecord().getId();
				List<ServerStateRecordCpu> cpu = serverStateRecordDto.getServerStateRecordCpu();
				List<ServerStateRecordDisk> disk = serverStateRecordDto.getServerStateRecordDisk();
				List<ServerStateRecordNetcard> netcard = serverStateRecordDto.getServerStateRecordNetcard();
				List<ServerStateRecordCpu> cpu2 = new ArrayList<ServerStateRecordCpu>();
				List<ServerStateRecordDisk> disk2 = new ArrayList<ServerStateRecordDisk>();
				List<ServerStateRecordNetcard> netcard2 = new ArrayList<ServerStateRecordNetcard>();
				for (ServerStateRecordCpu c : cpu ){
					c.setRecordid(recordid);
					cpu2.add(c);
				}
				this.serverStateRecordDao.saveAllFivs(cpu2);
				for (ServerStateRecordDisk d : disk){
					d.setRecordid(recordid);
					disk2.add(d);
				}
				this.serverStateRecordDao.saveAllFivs(disk2);
				for (ServerStateRecordNetcard n : netcard){
					n.setRecordid(recordid);
					netcard2.add(n);
				}
				this.serverStateRecordDao.saveAllFivs(netcard2);
			}
		}
	}

	public void delete(String[] ids) {
		this.serverStateRecordDao.delete(ids);
	}

	public void find(PageObject pageObject) {
		this.serverStateRecordDao.find(pageObject);
	}
	
	public List findRecordByServerId(String serverId) {
		return this.serverStateRecordDao.findRecord(serverId);
	}
	public List<ServerStateRecord> findRecordListByParam(Map<String, Object> paramMap){
		return this.serverStateRecordDao.findRecordListByParam(paramMap);
	}
	public ServerStateRecord findRecordByParam(Map<String, Object> paramMap){
		return this.serverStateRecordDao.findRecordByParam(paramMap);
	}
	public ServerStateRecord getRecordById(String id ) {
		return this.serverStateRecordDao.getRecordById(id);
	}
	
	public ServerStateRecord findServerStateRecord(String id ) {
		return this.serverStateRecordDao.findServerStateRecord(id);
	}
	
	public ServerStateRecordDto findServerStateRecordById(String serverid ) {
		ServerStateRecordDto  dto = new ServerStateRecordDto();
		ServerStateRecord record = this.serverStateRecordDao.findServerStateRecord(serverid);
		Integer id = record.getId();
		List cpu = this.serverStateRecordDao.getCpuList(id);
		List disk = this.serverStateRecordDao.getDiskList(id);
		List net = this.serverStateRecordDao.getNetcardList(id);
		dto.setServerStateRecord(record);
		dto.setServerStateRecordCpu(cpu);
		dto.setServerStateRecordDisk(disk);
		dto.setServerStateRecordNetcard(net);
		return dto;
	}
	
	public List getCpu(Integer id ) {
		return this.serverStateRecordDao.getCpuList(id);
	}
	
	public List getDisk(Integer id ) {
		return this.serverStateRecordDao.getDiskList(id);
	}
	
	public List getNetcard(Integer id ) {
		return this.serverStateRecordDao.getNetcardList(id);
	}
	
	
	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<ServerStateRecord> list = serverStateRecordDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 存放每一行的数据
				ServerStateRecord server = (ServerStateRecord) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (server != null) {
					
				}
			}
		}
		return fieldData;
	}
	
	public void delete(String date ) {		
		this.serverStateRecordDao.delete(date);
	}
	
	@Override
	public boolean checkUnique(String attrName, String value, String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<HashMap<String,Object>> findState(String resId){
		return serverStateRecordDao.findState(resId);
	}
	
	
	public void save2(ServerStateRecordDto serverStateRecordDto) {
		
			ServerStateRecord record = serverStateRecordDto.getServerStateRecord();
			record.setRecordTime(DateUtil.getTime());
			
				this.serverStateRecordDao.saveFivs(record);
				Integer recordid = serverStateRecordDto.getServerStateRecord().getId();
				List<ServerStateRecordCpu> cpu = serverStateRecordDto.getServerStateRecordCpu();
				List<ServerStateRecordDisk> disk = serverStateRecordDto.getServerStateRecordDisk();
				List<ServerStateRecordNetcard> netcard = serverStateRecordDto.getServerStateRecordNetcard();
				List<ServerStateRecordCpu> cpu2 = new ArrayList<ServerStateRecordCpu>();
				List<ServerStateRecordDisk> disk2 = new ArrayList<ServerStateRecordDisk>();
				List<ServerStateRecordNetcard> netcard2 = new ArrayList<ServerStateRecordNetcard>();
				for (ServerStateRecordCpu c : cpu ){
					c.setRecordid(recordid);
					cpu2.add(c);
				}
				this.serverStateRecordDao.saveAllFivs(cpu2);
				for (ServerStateRecordDisk d : disk){
					d.setRecordid(recordid);
					disk2.add(d);
				}
				this.serverStateRecordDao.saveAllFivs(disk2);
				for (ServerStateRecordNetcard n : netcard){
					n.setRecordid(recordid);
					netcard2.add(n);
				}
				this.serverStateRecordDao.saveAllFivs(netcard2);
			}
		
	
}
