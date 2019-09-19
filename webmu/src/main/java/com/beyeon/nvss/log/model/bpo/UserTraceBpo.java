package com.beyeon.nvss.log.model.bpo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.TUserTrace;
import com.beyeon.hibernate.fivsdb.UserSecurity;
import com.beyeon.nvss.log.model.dao.UserTraceDao;

@Component("userTraceBpo")
public class UserTraceBpo {
	private Logger logger =LoggerFactory.getLogger(this.getClass());
	private UserTraceDao userTraceDao;

	public void setUserTraceDao(UserTraceDao userTraceDao) {
		this.userTraceDao = userTraceDao;
	}

	public void save(Object object) {
		if (object instanceof List)
			this.userTraceDao.saveAllFivs((List) object);
		else
			this.userTraceDao.saveFivs(object);
	}

	public void findUserTrace(PageObject pageObject) {
		this.userTraceDao.findUserTrace(pageObject);
	}
	
	
	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<TUserTrace> list = userTraceDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				// 存放每一行的数据
				TUserTrace userTrace = (TUserTrace) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (userTrace != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(i + 1));
					data.add(userTrace.getAmid());
					data.add(userTrace.getUserName());
					data.add(userTrace.getTerminalIp());
					data.add(userTrace.getMenuName());
					data.add(userTrace.getUserTrace());
					data.add(DateUtil.format(userTrace.getOperateDate(), "yyyy-MM-dd HH:mm:ss"));
					data.add(userTrace.getObjectTime());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}
	
	public UserSecurity findUserSecurity (String userId){
		return this.userTraceDao.findUserSecurity(userId);
	}
	
	public void resetUserSecurity(String userName){
		this.userTraceDao.resetUserSecurity(userName);
	}
	
	public void updateUserSecurity(UserSecurity user){
		this.userTraceDao.updateFivs(user);
	}
	
	public void saveUserSecurity(UserSecurity user){
		this.userTraceDao.saveFivs(user);
	}
	
	public String findUserIdByUserName(String userName){
		return this.userTraceDao.findUserIdByUserName(userName);
	}
}
