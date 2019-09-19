package com.beyeon.nvss.dmu.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.EncoderStateRecord;
import com.beyeon.hibernate.fivsdb.ServerStateRecord;
import com.beyeon.hibernate.fivsdb.SwitchStateRecord;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("dmuEquipmentDao")
public class DmuEquipmentDao extends NvsBaseDao {

	public void delete(String[] sids) {
		String[] paramNames = { "sids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete  s from dmu_equipment s  where s.id in (:sids)",
				paramNames, new Object[]{sids});
	}
	
	public void deleteByMaster(String sid) {
		this.getFivs_w().bulkUpdate("delete from DmuEquipment t where t.master =?", new Object[]{sid});
		
	}

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from DmuEquipment s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("id"))) {
			params.add(paramMap.get("id"));
			sb.append("and s.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and s.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(paramMap.get("type"));
			sb.append("and s.type = ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("status"))) {
			params.add(Integer.valueOf(paramMap.get("status")));
			sb.append("and s.status = ? ");
		}
		sb.append(" order by s.status desc");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}

	public List findDmuEquipmentType() {
		String hql = new String("select s from DmuEquipmentType s ");
		return this.getFivs_r().find(hql,null);
	}

	public boolean checkDmuEquipmentUnique(String id, String dmuEquipmentname) {
		StringBuilder sql = new StringBuilder("select t from DmuEquipment t where t.name = ? ");
		List params = new ArrayList();
		params.add(dmuEquipmentname);
		if (StringUtils.isNotBlank(id)){
			sql.append("and t.id != ? ");
			params.add(id);
		}
		List list = this.getFivs_r().find(sql.toString(), params.toArray());
		return list.size() == 0;
	}
	
	public List<DmuEquipment> findByNoPageWithExcel(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from DmuEquipment s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("id"))) {
			params.add(paramMap.get("id"));
			sb.append("and s.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and s.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(paramMap.get("type"));
			sb.append("and s.type = ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("status"))) {
			params.add(Integer.valueOf(paramMap.get("status")));
			sb.append("and s.status = ? ");
		}
		sb.append(" order by s.status desc");
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}
	
	public List<DmuEquipment> findByParam(Map<String, String> paramMap) {
		StringBuilder sb = new StringBuilder("select s from DmuEquipment s where 1=1 ");
		List params = new ArrayList();
		if (StringUtils.isNotBlank(paramMap.get("id"))) {
			params.add(paramMap.get("id"));
			sb.append("and s.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and s.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("ip"))) {
			params.add(paramMap.get("ip"));
			sb.append("and s.ip = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(paramMap.get("type"));
			sb.append("and s.type = ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("status"))) {
			params.add(Integer.valueOf(paramMap.get("status")));
			sb.append("and s.status = ? ");
		}
		sb.append(" order by s.status desc");
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}
	
	public DmuEquipment findById(String id) {
		String hql = new String("select e from DmuEquipment e where e.id=?");
		List resList = this.getFivs_r().find(hql, new Object[]{id});
		if (resList.size() > 0)
			return (DmuEquipment)resList.get(0);
		return null;
	}
	
//	public  EncoderStateRecord findCoderStateByCommand(String id) {
//		
//		String hql = new String("select s from EncoderStateRecord s where s.id=? order by s.recordTime desc ");
//		List resList = this.getFivs_r().find(hql, new Object[]{id});
//		if (resList.size() > 0){
//			HashMap<String,Object> result = new HashMap<String,Object>();
//			return (EncoderStateRecord) resList.get(0);			
//		}
//		return null;
//	}
	
	public List findAllDmuEquipment() {
		String sql = "select c from DmuEquipment c";
		return this.getFivs_r().find(sql, null);
	}
	
	
	public DiskStateRecord findDiskStateRecord(String diskid) {
		List list = this.getFivs_r().find("select t from DiskStateRecord t where t.diskid = ?  order by t.recordTime desc" ,new Object[]{diskid});
		if (null != list && list.size() > 0){
			DiskStateRecord result =(DiskStateRecord) list.get(0);
			result.setRecordTime(DateUtil.getTime());
			return result;
		}
		return null;
	}
	
	public EncoderStateRecord findEncoderStateRecord(String encoderid) {
		List list = this.getFivs_r().find("select t from EncoderStateRecord t where t.encoderid = ? order by t.recordTime desc" ,new Object[]{encoderid});
		if (null != list && list.size() > 0){
			EncoderStateRecord result = (EncoderStateRecord) list.get(0);
			result.setRecordTime(DateUtil.getTime());
			return result;
		}
		return null;
	}
	
	public ServerStateRecord findServerStateRecord(String serverid) {
		List list = this.getFivs_r().find("select t from ServerStateRecord t where t.serverid = ? order by t.recordTime desc" ,new Object[]{serverid});
		if (null != list && list.size() > 0){
			ServerStateRecord result = (ServerStateRecord)list.get(0);
			result.setRecordTime(DateUtil.getTime());
			return result;
		}
		return null;
	}
	
	public SwitchStateRecord findSwitchStateRecord(String switchid) {
		List list = this.getFivs_r().find("select t from SwitchStateRecord t where t.switchid = ? order by t.recordTime desc" ,new Object[]{switchid});
		if (null != list && list.size() > 0){
			SwitchStateRecord result = (SwitchStateRecord)list.get(0);
			result.setRecordTime(DateUtil.getTime());
			return result;
		}
		return null;
	}
	
	public List getCpuList(Integer recordid) {
		return  this.getFivs_r().find("select se from ServerStateRecordCpu se where se.recordid = ?", new Integer[]{recordid});
	}
	
	public List getDiskList(Integer recordid) {
		return  this.getFivs_r().find("select se from ServerStateRecordDisk se where se.recordid = ?", new Integer[]{recordid});
	}
	
	public List getNetcardList(Integer recordid) {
		return  this.getFivs_r().find("select se from ServerStateRecordNetcard se where se.recordid = ?", new Integer[]{recordid});
	}
	
	public List getPortList(Integer recordid) {
		return  this.getFivs_r().find("select se from SwitchStateRecordPort se where se.recordid = ?", new Integer[]{recordid});
	}
	
	public void findAllRecord(PageObject pageObject){

		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		StringBuffer sb = new StringBuffer("select a.*,b.name,b.type from (select diskid as resId,totalvolume as a1 ,undistributed as a2,portNum as a3,cpu  as a4,fan  as a5,bad as a6,state  as a7,recordTime from disk_state_record a  ");
		if (StringUtils.isBlank(paramMap.get("beginTime"))) {
			paramMap.put("beginTime", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
		}		
		params.add(paramMap.get("beginTime"));
		sb.append("where a.recordTime > ? ");
		if (StringUtils.isBlank(paramMap.get("endTime"))) {
			paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("endTime"));
		sb.append("and a.recordTime <= ? ");
				
		sb.append("union select serverid as resId,systemInfo as a1,diskNum as a2,totalMem as a3,usedMem  as a4,leftMem as a5,cpuNum  as a6,networkCard  as a7,recordTime from server_state_record a ");
		if (StringUtils.isBlank(paramMap.get("beginTime"))) {
			paramMap.put("beginTime", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
		}		
		params.add(paramMap.get("beginTime"));
		sb.append("where a.recordTime > ? ");
		if (StringUtils.isBlank(paramMap.get("endTime"))) {
			paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("endTime"));
		sb.append("and a.recordTime <= ? ");
		sb.append("union select encoderid as resId,codeType as a1,streamType as a2,mainStreamRateType as a3,mainStreamResolution  as a4,mainStreamFrameRate as a5,mainStreamGOP as a6,childStreamResolution  as a7,recordTime from encoder_state_record a ");
		if (StringUtils.isBlank(paramMap.get("beginTime"))) {
			paramMap.put("beginTime", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
		}		
		params.add(paramMap.get("beginTime"));
		sb.append("where a.recordTime > ? ");
		if (StringUtils.isBlank(paramMap.get("endTime"))) {
			paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("endTime"));
		sb.append("and a.recordTime <= ? ");
		sb.append("union select switchid as resId,memory as a1,cpu as a2,portNum as a3 ,''   as a4 ,''  as a5,''  as a6,''  as a7,recordTime from switch_state_record a ");
		if (StringUtils.isBlank(paramMap.get("beginTime"))) {
			paramMap.put("beginTime", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
		}		
		params.add(paramMap.get("beginTime"));
		sb.append("where a.recordTime > ? ");
		if (StringUtils.isBlank(paramMap.get("endTime"))) {
			paramMap.put("endTime", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("endTime"));
		sb.append("and a.recordTime <= ? ");
		sb.append(") a INNER JOIN dmu_equipment b on a.resId = b.id where 1=1 ");
	
		if (StringUtils.isNotBlank(paramMap.get("resId"))) {
			params.add(paramMap.get("resId"));
			sb.append("and b.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%"+paramMap.get("name")+"%");
			sb.append("and b.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(paramMap.get("type"));
			sb.append("and b.type = ? ");
		}
		
		
		
		sb.append("order by a.recordTime desc ,a.resId desc");
		
		this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
	}
	
	public List findEquipmentOwn(Map<String,String> paramMap){
		StringBuffer sb = new StringBuffer ("select b from DmuEquipment b where 1=1 ");
		List params = new ArrayList();
		if (StringUtils.isNotBlank(paramMap.get("resId"))) {
			params.add(paramMap.get("resId"));
			sb.append("and b.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%"+paramMap.get("name")+"%");
			sb.append("and b.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(paramMap.get("type"));
			sb.append("and b.type = ? ");
		}
		sb.append("and master is null ");
		return this.getFivs_r().find(sb.toString(), params.toArray());		
	}
	
	public List findEquipmentOther(Map<String,String> paramMap){
		StringBuffer sb = new StringBuffer ("select b from DmuEquipment b where 1=1 ");
		List params = new ArrayList();
		if (StringUtils.isNotBlank(paramMap.get("resId"))) {
			params.add(paramMap.get("resId"));
			sb.append("and b.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%"+paramMap.get("name")+"%");
			sb.append("and b.name like ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(paramMap.get("type"));
			sb.append("and b.type = ? ");
		}
		sb.append("and master is not null ");
		return this.getFivs_r().find(sb.toString(), params.toArray());		
	}
	
	public List<ServerStateRecord> findServerStateRecordList(HashMap<String,String> paramMap) {
		StringBuffer sb = new StringBuffer("select t.id,t.serverid,t.systemInfo,t.diskNum,t.totalMem,t.usedMem,t.leftMem,t.cpuNum,t.networkCard,t.recordTime from server_state_record t left join dmu_equipment d on t.serverid = d.id where 1 = 1 ");
		List params = new ArrayList();
		if (StringUtils.isNotBlank(paramMap.get("resId"))) {
			params.add(paramMap.get("resId"));
			sb.append("and d.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%"+paramMap.get("name")+"%");
			sb.append("and d.name like ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(paramMap.get("type"));
			sb.append("and d.type = ? ");
		}	
		if (StringUtils.isNotBlank(paramMap.get("beginTime"))) {
			params.add(paramMap.get("beginTime"));
			sb.append("and t.recordTime >= ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("endTime"))) {
			params.add(paramMap.get("endTime"));
			sb.append("and t.recordTime <= ? ");
		}
		sb.append("order by t.id asc ");		
		List<HashMap<String,Object>> list = this.getFivs_r().findSQLToMap(sb.toString(), params.toArray());		
		List<ServerStateRecord> rlist = new ArrayList<ServerStateRecord>();
		for (HashMap<String,Object> record : list){
			ServerStateRecord r = new ServerStateRecord();
			r.setId(Integer.parseInt(String.valueOf(record.get("id"))));
			r.setServerid(String.valueOf(record.get("serverid")));
			r.setSystemInfo(String.valueOf(record.get("systemInfo")));
			r.setDiskNum(String.valueOf(record.get("diskNum")));
			r.setTotalMem(String.valueOf(record.get("totalMem")));
			r.setUsedMem(String.valueOf(record.get("usedMem")));
			r.setLeftMem(String.valueOf(record.get("leftMem")));
			r.setCpuNum(String.valueOf(record.get("cpuNum")));
			r.setNetworkCard(String.valueOf(record.get("networkCard")));
			r.setRecordTime(String.valueOf(record.get("recordTime")));
			rlist.add(r);
		}
		return rlist;
	}
	
	public List<EncoderStateRecord> findEncoderStateRecordList(HashMap<String,String> paramMap) {
		StringBuffer sb = new StringBuffer("select t.id,t.encoderid,t.codeType,t.streamType,t.mainStreamRateType,t.mainStreamResolution,t.mainStreamFrameRate,t.mainStreamGOP,t.childStreamResolution,t.recordTime from encoder_state_record t left join dmu_equipment d on t.encoderid = d.id where 1 = 1 ");
		List params = new ArrayList();
		if (StringUtils.isNotBlank(paramMap.get("resId"))) {
			params.add(paramMap.get("resId"));
			sb.append("and d.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%"+paramMap.get("name")+"%");
			sb.append("and d.name like ? ");
		}	
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(paramMap.get("type"));
			sb.append("and d.type = ? ");
		}	
		if (StringUtils.isNotBlank(paramMap.get("beginTime"))) {
			params.add(paramMap.get("beginTime"));
			sb.append("and t.recordTime >= ? ");
		}
		
		if (StringUtils.isNotBlank(paramMap.get("endTime"))) {
			params.add(paramMap.get("endTime"));
			sb.append("and t.recordTime <= ? ");
		}
		sb.append("order by t.id asc ");		
		List<HashMap<String,Object>> list = this.getFivs_r().findSQLToMap(sb.toString(), params.toArray());		
		List<EncoderStateRecord> rlist = new ArrayList<EncoderStateRecord>();
		for (HashMap<String,Object> record : list){
			EncoderStateRecord r = new EncoderStateRecord();
			r.setId(Integer.parseInt(String.valueOf(record.get("id"))));
			r.setEncoderid(String.valueOf(record.get("encoderid")));
			r.setCodeType(String.valueOf(record.get("codeType")));
			r.setStreamType(String.valueOf(record.get("streamType")));
			r.setMainStreamRateType(String.valueOf(record.get("mainStreamRateType")));
			r.setMainStreamResolution(String.valueOf(record.get("mainStreamResolution")));
			r.setMainStreamFrameRate(String.valueOf(record.get("mainStreamFrameRate")));
			r.setMainStreamGOP(String.valueOf(record.get("mainStreamGOP")));
			r.setChildStreamResolution(String.valueOf(record.get("childStreamResolution")));
			r.setRecordTime(String.valueOf(record.get("recordTime")));
			rlist.add(r);
		}
		return rlist;
	}
	
	
	public List<DiskStateRecord> findDiskStateRecordList(HashMap<String,String> paramMap) {
		StringBuffer sb = new StringBuffer("select t.id,t.diskid,t.totalvolume,t.undistributed,t.portNum,t.cpu,t.fan,t.bad,t.state,t.recordTime from disk_state_record t left join dmu_equipment d on t.diskid = d.id where 1 = 1 ");
		List params = new ArrayList();
		if (StringUtils.isNotBlank(paramMap.get("resId"))) {
			params.add(paramMap.get("resId"));
			sb.append("and d.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%"+paramMap.get("name")+"%");
			sb.append("and d.name like ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(paramMap.get("type"));
			sb.append("and d.type = ? ");
		}	
		if (StringUtils.isNotBlank(paramMap.get("beginTime"))) {
			params.add(paramMap.get("beginTime"));
			sb.append("and t.recordTime >= ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("endTime"))) {
			params.add(paramMap.get("endTime"));
			sb.append("and t.recordTime <= ? ");
		}
		sb.append("order by t.id asc ");		
		List<HashMap<String,Object>> list = this.getFivs_r().findSQLToMap(sb.toString(), params.toArray());		
		List<DiskStateRecord> rlist = new ArrayList<DiskStateRecord>();
		for (HashMap<String,Object> record : list){
			DiskStateRecord r = new DiskStateRecord();
//			t.id,t.diskid,t.totalvolume,t.undistributed,t.portNum,t.cpu,t.fan,t.bad,t.state,t.recordTime
			r.setId(Integer.parseInt(String.valueOf(record.get("id"))));
			r.setDiskid(String.valueOf(record.get("diskid")));
			r.setTotalvolume(String.valueOf(record.get("totalvolume")));
			r.setUndistributed(String.valueOf(record.get("undistributed")));
			r.setPortNum(String.valueOf(record.get("portNum")));
			r.setCpu(String.valueOf(record.get("cpu")));
			r.setFan(String.valueOf(record.get("fan")));
			r.setBad(String.valueOf(record.get("bad")));
			r.setState(String.valueOf(record.get("state")));
			r.setRecordTime(String.valueOf(record.get("recordTime")));
			rlist.add(r);
		}
		return rlist;
	}
	
	
	public List<SwitchStateRecord> findSwitchStateRecordList(HashMap<String,String> paramMap) {
		StringBuffer sb = new StringBuffer("select t.id,t.switchid,t.memory,t.cpu,t.portNum,t.recordTime from switch_state_record t left join dmu_equipment d on t.switchid = d.id where 1 = 1 ");
		List params = new ArrayList();
		if (StringUtils.isNotBlank(paramMap.get("resId"))) {
			params.add(paramMap.get("resId"));
			sb.append("and d.id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%"+paramMap.get("name")+"%");
			sb.append("and d.name like ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("type"))) {
			params.add(paramMap.get("type"));
			sb.append("and d.type = ? ");
		}	
		if (StringUtils.isNotBlank(paramMap.get("beginTime"))) {
			params.add(paramMap.get("beginTime"));
			sb.append("and t.recordTime >= ? ");
		}		
		if (StringUtils.isNotBlank(paramMap.get("endTime"))) {
			params.add(paramMap.get("endTime"));
			sb.append("and t.recordTime <= ? ");
		}
		sb.append("order by t.id asc ");		
		List<HashMap<String,Object>> list = this.getFivs_r().findSQLToMap(sb.toString(), params.toArray());		
		List<SwitchStateRecord> rlist = new ArrayList<SwitchStateRecord>();
		for (HashMap<String,Object> record : list){
			SwitchStateRecord r = new SwitchStateRecord();
			r.setId(Integer.parseInt(String.valueOf(record.get("id"))));
			r.setSwitchid(String.valueOf(record.get("switchid")));
			r.setMemory(String.valueOf(record.get("memory")));
			r.setCpu(String.valueOf(record.get("cpu")));
			r.setPortNum(String.valueOf(record.get("portNum")));
			r.setRecordTime(String.valueOf(record.get("recordTime")));
			rlist.add(r);
		}
		return rlist;
	}
}
