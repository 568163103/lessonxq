package com.beyeon.nvss.dmu.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("diskStateRecordDao")
public class DiskStateRecordDao extends NvsBaseDao {

	public void delete(String[] sids) {
		String[] paramNames = { "sids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete s from disk_state_record s  where s.id in (:sids)",
				paramNames, new Object[]{sids});
	}

	public void delete(String date) {
		this.getFivs_w().bulkUpdateSQL("delete s from disk_state_record s  where s.recordTime < ?",
				 new Object[]{date});
	}

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from DiskStateRecord s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("diskid"))) {
			params.add(paramMap.get("diskid"));
			sb.append("and s.diskid = ? ");
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

	
	public List findAllRecord() {
		String sql = "select c from DiskStateRecord c";
		return this.getFivs_r().find(sql, null);
	}
	
	public DiskStateRecord getRecordById(String id) {
		return (DiskStateRecord) this.getFivs_r().findUnique("select se from DiskStateRecord se where se.recordid = ?", new String[]{id});
	}
	
	public List findRecord(String diskid) {
		List list = this.getFivs_r().find("select t from DiskStateRecord t where t.diskid = ? order by t.recordTime desc" ,new Object[]{diskid});
		if (null != list && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List findRecord(String diskid,String beginTime,String endTime) {
		List list = this.getFivs_r().find("select t from DiskStateRecord t where t.diskid = ? and t.recordTime > ? and t.recordTime < ?" ,new Object[]{diskid,beginTime,endTime});
		if (null != list && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List<DiskStateRecord> findByNoPageWithExcel(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from DiskStateRecord s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("diskid"))) {
			params.add(paramMap.get("diskid"));
			sb.append("and s.diskid = ? ");
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
	
	public List<DiskStateRecord> findRecordListByParam(Map<String, Object> paramMap) {
		StringBuilder sb = new StringBuilder("select s from DiskStateRecord s where 1=1 ");
		List params = new ArrayList();
		if (paramMap.get("diskid")!=null && !"".equals(paramMap.get("diskid"))) {
			params.add(paramMap.get("diskid"));
			sb.append("and s.diskid = ? ");
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
		
		
		public DiskStateRecord findDiskStateRecord(String diskid) {
			List list = this.getFivs_r().find("select t from DiskStateRecord t where t.diskid = ? order by t.recordTime desc" ,new Object[]{diskid});
			if (null != list && list.size() > 0){
				DiskStateRecord disk = (DiskStateRecord)list.get(0);
				return disk;
			}
			return null;
		}
}
