package com.beyeon.nvss.log.model.bpo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.OperationLog;
import com.beyeon.hibernate.fivsdb.TUserTrace;
import com.beyeon.nvss.log.model.dao.OperationLogDao;

@Component("operationLogBpo")
public class OperationLogBpo {
	private Logger logger =LoggerFactory.getLogger(this.getClass());
	private OperationLogDao operationLogDao;

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public void save(Object object) {
		if (object instanceof List)
			this.operationLogDao.saveAllFivs((List) object);
		else
			this.operationLogDao.saveFivs(object);
	}

	public void findOperationLog(PageObject pageObject) {
		this.operationLogDao.findOperationLog(pageObject);
	}
	
	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<OperationLog> list = operationLogDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				// 存放每一行的数据
				OperationLog operationLog = (OperationLog) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (operationLog != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(i + 1));
					data.add(operationLog.getUserId());
					data.add(operationLog.getUserName());
					data.add(operationLog.getTerminalIp());
					data.add(operationLog.getOperation());
					data.add(operationLog.getDescription());
					data.add(operationLog.getTime());
					data.add(operationLog.getObjectTime());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}

}
