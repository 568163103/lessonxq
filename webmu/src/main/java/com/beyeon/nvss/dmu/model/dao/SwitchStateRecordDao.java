package com.beyeon.nvss.dmu.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.SwitchStateRecord;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("switchStateRecordDao")
public class SwitchStateRecordDao extends NvsBaseDao {

	public void delete(String[] sids) {
		String[] paramNames = { "sids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete s from switch_state_record s left join  switch_state_record_port sp on s.id =sp.recordid where s.id in (:sids)",
				paramNames, new Object[]{sids});
	}
	
	public void delete(String date) {
		this.getFivs_w().bulkUpdateSQL("delete s from switch_state_record s left join  switch_state_record_port sp on s.id =sp.recordid where s.recordTime < ?",
				 new Object[]{date});
	}

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from SwitchStateRecord s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("switchid"))) {
			params.add(paramMap.get("switchid"));
			sb.append("and s.switchid = ? ");
		}
		if (StringUtils.isBlank(paramMap.get("beginTime"))) {
			paramMap.put("beginTime", DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("beginTime"));
		sb.append("and s.recordTime > ? ");
		if (StringUtils.isNotBlank(paramMap.get("endTime"))) {
			params.add(paramMap.get("endTime"));
			sb.append("and s.recordTime < ? ");
		}
		sb.append(" order by s.recordTime desc");
		this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
	}
	
	public List getPortList(Integer recordid) {
		return  this.getFivs_r().find("select se from SwitchStateRecordPort se where se.recordid = ?", new Integer[]{recordid});
	}
	
	public List findAllRecord() {
		String sql = "select c from SwitchStateRecord c";
		return this.getFivs_r().find(sql, null);
	}
	
	public SwitchStateRecord getRecordById(String id) {
		return (SwitchStateRecord) this.getFivs_r().findUnique("select se from SwitchStateRecord se where se.recordid = ?", new String[]{id});
	}
	
	public List findRecord(String switchid) {
		List list = this.getFivs_r().find("select t from SwitchStateRecord t where t.switchid = ? order by recordTime desc" ,new Object[]{switchid});
		if (null != list && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List findRecord(String switchid,String beginTime,String endTime) {
		List list = this.getFivs_r().find("select t from SwitchStateRecord t where t.switchid = ? and t.recordTime > ? and t.recordTime < ?" ,new Object[]{switchid,beginTime,endTime});
		if (null != list && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List<SwitchStateRecord> findByNoPageWithExcel(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from SwitchStateRecord s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("switchid"))) {
			params.add(paramMap.get("switchid"));
			sb.append("and s.switchid = ? ");
		}
		if (StringUtils.isBlank(paramMap.get("beginTime"))) {
			paramMap.put("beginTime", DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("beginTime"));
		sb.append("and s.recordTime > ? ");
		if (StringUtils.isNotBlank(paramMap.get("endTime"))) {
			params.add(paramMap.get("endTime"));
			sb.append("and s.recordTime < ? ");
		}
		sb.append(" order by s.recordTime desc");
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}
	
	public List<SwitchStateRecord> findRecordListByParam(Map<String, Object> paramMap) {
		StringBuilder sb = new StringBuilder("select s from SwitchStateRecord s where 1=1 ");
		List params = new ArrayList();
		if (paramMap.get("switchid")!=null && !"".equals(paramMap.get("switchid"))) {
			params.add(paramMap.get("switchid"));
			sb.append("and s.switchid = ? ");
		}
		if (paramMap.get("beginTime")!=null && !"".equals(paramMap.get("beginTime"))) {
			paramMap.put("beginTime", DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("beginTime"));
		sb.append("and s.recordTime > ? ");
		if (paramMap.get("endTime")!=null && !"".equals(paramMap.get("endTime"))) {
			params.add(paramMap.get("endTime"));
			sb.append("and s.recordTime < ? ");
		}
		sb.append(" order by s.recordTime desc");
		List list = this.getFivs_r().findList(sb.toString(), params.toArray());
		return list;
	}
		public SwitchStateRecord findRecordByParam(Map<String, Object> paramMap) {
			StringBuilder sb = new StringBuilder("select s from SwitchStateRecord s where 1=1 ");
			List params = new ArrayList();
			if (paramMap.get("switchid")!=null && !"".equals(paramMap.get("switchid"))) {
				params.add(paramMap.get("switchid"));
				sb.append("and s.switchid = ? ");
			}
			if (paramMap.get("beginTime")!=null && !"".equals(paramMap.get("beginTime"))) {
				paramMap.put("beginTime", DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
			}
			params.add(paramMap.get("beginTime"));
			sb.append("and s.recordTime > ? ");
			if (paramMap.get("endTime")!=null && !"".equals(paramMap.get("endTime"))) {
				params.add(paramMap.get("endTime"));
				sb.append("and s.recordTime < ? ");
			}
			sb.append(" order by s.recordTime desc");
			return (SwitchStateRecord) this.getFivs_r().findUnique(sb.toString(), params.toArray());
			
	}
		
		public SwitchStateRecord findSwitchStateRecord(String switchid) {
			List list = this.getFivs_r().find("select t from SwitchStateRecord t where t.switchid = ? order by recordTime desc" ,new Object[]{switchid});
			if (null != list && list.size() > 0){
				SwitchStateRecord record = (SwitchStateRecord) list.get(0);
				return record;
			}
			return null;
		}
		
	
}
