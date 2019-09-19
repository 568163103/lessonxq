package com.beyeon.nvss.dmu.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.ServerStateRecord;
import com.beyeon.hibernate.fivsdb.ServerStateRecordCpu;
import com.beyeon.hibernate.fivsdb.ServerStateRecordDisk;
import com.beyeon.hibernate.fivsdb.ServerStateRecordNetcard;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("serverStateRecordDao")
public class ServerStateRecordDao extends NvsBaseDao {

	public void delete(String[] sids) {
		String[] paramNames = { "sids" };
		this.getFivs_w().bulkUpdateSQLByParamName("delete s,t,u,se from server_state_record s left join server_state_record_cpu t on s.id = t.recordid left join server_state_record_disk u on s.id = u.recordid left join server_state_record_netcard se on s.id = se.recordid where s.id in (:sids)",
				paramNames, new Object[]{sids});
	}
	
	public void delete(String date) {
		this.getFivs_w().bulkUpdateSQL("delete s,t,u,se from server_state_record s left join server_state_record_cpu t on s.id = t.recordid left join server_state_record_disk u on s.id = u.recordid left join server_state_record_netcard se on s.id = se.recordid where s.recordTime < ?",
				new Object[]{date});
	}

	public void find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from ServerStateRecord s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("serverid"))) {
			params.add(paramMap.get("serverid"));
			sb.append("and s.serverid = ? ");
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

	
	public List getCpuList(Integer recordid) {
		return  this.getFivs_r().find("select se from ServerStateRecordCpu se where se.recordid = ?", new Integer[]{recordid});
	}
	
	public List getDiskList(Integer recordid) {
		return  this.getFivs_r().find("select se from ServerStateRecordDisk se where se.recordid = ?", new Integer[]{recordid});
	}
	
	public List getNetcardList(Integer recordid) {
		return  this.getFivs_r().find("select se from ServerStateRecordNetcard se where se.recordid = ?", new Integer[]{recordid});
	}

	
	public List findAllRecord() {
		String sql = "select c from ServerStateRecord c";
		return this.getFivs_r().find(sql, null);
	}
	
	public ServerStateRecord getRecordById(String id) {
		return (ServerStateRecord) this.getFivs_r().findUnique("select se from ServerStateRecord se where se.recordid = ?", new String[]{id});
	}
	
	public List findRecord(String serverid) {
		List list = this.getFivs_r().find("select t from ServerStateRecord t where t.serverid = ? order by t.recordTime desc" ,new Object[]{serverid});
		if (null != list && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List findRecord(String serverid,String beginTime,String endTime) {
		List list = this.getFivs_r().find("select t from ServerStateRecord t where t.serverid = ? and t.recordTime > ? and t.recordTime < ?" ,new Object[]{serverid,beginTime,endTime});
		if (null != list && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List<ServerStateRecord> findByNoPageWithExcel(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from ServerStateRecord s where 1=1 ");
		List params = new ArrayList();
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("serverid"))) {
			params.add(paramMap.get("serverid"));
			sb.append("and s.serverid = ? ");
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
	
	public List<ServerStateRecord> findRecordListByParam(Map<String, Object> paramMap) {
		StringBuilder sb = new StringBuilder("select s from ServerStateRecord s where 1=1 ");
		List params = new ArrayList();
		if (paramMap.get("serverid")!=null && !"".equals(paramMap.get("serverid"))) {
			params.add(paramMap.get("serverid"));
			sb.append("and s.serverid = ? ");
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
		public ServerStateRecord findRecordByParam(Map<String, Object> paramMap) {
			StringBuilder sb = new StringBuilder("select s from ServerStateRecord s where 1=1 ");
			List params = new ArrayList();
			if (paramMap.get("serverid")!=null && !"".equals(paramMap.get("serverid"))) {
				params.add(paramMap.get("serverid"));
				sb.append("and s.serverid = ? ");
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
			return (ServerStateRecord) this.getFivs_r().findUnique(sb.toString(), params.toArray());
			
	}
		
	public ServerStateRecord findServerStateRecord(String serverid) {
			List list = this.getFivs_r().find("select t from ServerStateRecord t where t.serverid = ? order by t.recordTime desc" ,new Object[]{serverid});
			if (null != list && list.size() > 0){
				ServerStateRecord result = (ServerStateRecord)list.get(0);
				return result;
			}
			return null;
	}
}
