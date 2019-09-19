package com.beyeon.nvss.dmu.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.EncoderStateRecord;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("encoderStateRecordDao")
public class EncoderStateRecordDao extends NvsBaseDao {

	public void delete(String[] sids) {
		String[] paramNames = { "sids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete s from encoder_state_record s  where s.id in (:sids)",
				paramNames, new Object[]{sids});
	}
	
	public void delete(String date) {
		this.getFivs_w().bulkUpdateSQL("delete s from encoder_state_record s  where s.recordTime < ?",
				new Object[]{date});
	}

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from EncoderStateRecord s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("encoderid"))) {
			params.add(paramMap.get("encoderid"));
			sb.append("and s.encoderid = ? ");
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
		String sql = "select c from EncoderStateRecord c";
		return this.getFivs_r().find(sql, null);
	}
	
	public EncoderStateRecord getRecordById(Integer id) {
		return (EncoderStateRecord) this.getFivs_r().findUnique("select se from EncoderStateRecord se where se.recordid = ?", new Integer[]{id});
	}
	
	public List findRecord(String encoderid) {
		List list = this.getFivs_r().find("select t from EncoderStateRecord t where t.encoderid = ? order by t.recordTime desc" ,new Object[]{encoderid});
		if (null != list && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List findRecord(String encoderid,String beginTime,String endTime) {
		List list = this.getFivs_r().find("select t from EncoderStateRecord t where t.encoderid = ? and t.recordTime > ? and t.recordTime < ?" ,new Object[]{encoderid,beginTime,endTime});
		if (null != list && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public EncoderStateRecord findEncoderStateRecord(String encoderid) {
		List list = this.getFivs_r().find("select t from EncoderStateRecord t where t.encoderid = ? order by t.recordTime desc" ,new Object[]{encoderid});
		if (null != list && list.size() > 0){
			EncoderStateRecord  record = (EncoderStateRecord) list.get(0);
			return record;
		}
		return null;
	}
	
	public List<EncoderStateRecord> findByNoPageWithExcel(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from EncoderStateRecord s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("encoderid"))) {
			params.add(paramMap.get("encoderid"));
			sb.append("and s.encoderid = ? ");
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
	
	public List<EncoderStateRecord> findRecordListByParam(Map<String, Object> paramMap) {
		StringBuilder sb = new StringBuilder("select s from EncoderStateRecord s where 1=1 ");
		List params = new ArrayList();
		if (paramMap.get("encoderid")!=null && !"".equals(paramMap.get("encoderid"))) {
			params.add(paramMap.get("encoderid"));
			sb.append("and s.encoderid = ? ");
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
		public EncoderStateRecord findRecordByParam(Map<String, Object> paramMap) {
			StringBuilder sb = new StringBuilder("select s from EncoderStateRecord s where 1=1 ");
			List params = new ArrayList();
			if (paramMap.get("encoderid")!=null && !"".equals(paramMap.get("encoderid"))) {
				params.add(paramMap.get("encoderid"));
				sb.append("and s.encoderid = ? ");
			}
			if (paramMap.get("beginTime") ==null || "".equals(paramMap.get("beginTime"))) {
				paramMap.put("beginTime", DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
			}
			params.add(paramMap.get("beginTime"));
			sb.append("and s.recordTime > ? ");
			if (paramMap.get("endTime")!=null && !"".equals(paramMap.get("endTime"))) {
				params.add(paramMap.get("endTime"));
				sb.append("and s.recordTime < ? ");
			}
			sb.append(" order by s.recordTime desc");
			return (EncoderStateRecord) this.getFivs_r().findUnique(sb.toString(), params.toArray());
			
	}
}
