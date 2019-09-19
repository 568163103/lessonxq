package com.beyeon.nvss.log.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.OperationLog;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.hibernate.fivsdb.TUserTrace;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("operationLogDao")
public class OperationLogDao extends NvsBaseDao {

	public void findOperationLog(PageObject pageObject) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.id,t.user_id as userId,t.user_name as userName,t.terminal_ip as terminalIp,t.time,t.object_id as objectId,t.object_time as objectTime,IFNULL(c.name,p.name) as description,t.operation from operation_log t ");
//		sb.append("select t.id,t.user_id as userId,t.user_name as userName,t.terminal_ip as terminalIp,t.time,t.object_id as objectId,IFNULL(c.name,p.name) as description,t.operation from operation_log t ");
		sb.append("LEFT JOIN channel c on t.description = c.id LEFT JOIN platform_res p on t.description = p.id where 1=1 ");
		List params = new ArrayList();

		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("amid"))) {
			params.add(paramMap.get("amid"));
			sb.append("and t.user_id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("userName"))) {
			params.add(paramMap.get("userName"));
			sb.append("and t.user_name = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("terminalIp"))) {
			params.add(paramMap.get("terminalIp"));
			sb.append("and t.terminal_ip = ? ");
		}
		if (StringUtils.isBlank(paramMap.get("openTimeOne"))) {
			paramMap.put("openTimeOne", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("openTimeOne"));
		sb.append("and t.time>=? ");
		if (StringUtils.isBlank(paramMap.get("openTimeTwo"))) {
			paramMap.put("openTimeTwo", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("openTimeTwo"));
		sb.append("and t.time<? ");
		if (StringUtils.isNotBlank(paramMap.get("userTrace"))) {
			params.add(paramMap.get("userTrace"));
			sb.append("and t.operation = ? ");
		}
		sb.append("order by t.id desc ");
		this.getFivs_r().findSQLToBean(pageObject, sb.toString(), params.toArray(), OperationLog.class);
	}
	
	public List<OperationLog> findByNoPageWithExcel(PageObject pageObject) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.id,t.user_id as userId,t.user_name as userName,t.terminal_ip as terminalIp,t.time,t.object_id as objectId,t.object_time as objectTime,IFNULL(c.name,p.name) as description,d.name as operation  from operation_log t ");
//		sb.append("select t.id,t.user_id as userId,t.user_name as userName,t.terminal_ip as terminalIp,t.time,t.object_id as objectId,IFNULL(c.name,p.name) as description,d.name as operation  from operation_log t ");
		sb.append("LEFT JOIN channel c on t.description = c.id LEFT JOIN platform_res p on t.description = p.id LEFT JOIN (select * from t_dict where pre_id = ? )d on t.operation = d.value where 1=1 ");
		List params = new ArrayList();
		params.add(TDict.OPERATION_TYPE);
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("amid"))) {
			params.add(paramMap.get("amid"));
			sb.append("and t.user_id = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("userName"))) {
			params.add(paramMap.get("userName"));
			sb.append("and t.user_name = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("terminalIp"))) {
			params.add(paramMap.get("terminalIp"));
			sb.append("and t.terminal_ip = ? ");
		}
		if (StringUtils.isBlank(paramMap.get("openTimeOne"))) {
			paramMap.put("openTimeOne", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("openTimeOne"));
		sb.append("and t.time>=? ");
		if (StringUtils.isBlank(paramMap.get("openTimeTwo"))) {
			paramMap.put("openTimeTwo", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
		}
		params.add(paramMap.get("openTimeTwo"));
		sb.append("and t.time<? ");
		if (StringUtils.isNotBlank(paramMap.get("userTrace"))) {
			params.add(paramMap.get("userTrace"));
			sb.append("and t.operation = ? ");
		}
		sb.append("order by t.id desc ");
		List list = this.getFivs_r().findSQLToBean(sb.toString(), params.toArray(), OperationLog.class);
		return list;
	}
}
