package com.beyeon.nvss.system.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.SystemConfig;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("systemConfigDao")
public class SystemConfigDao extends NvsBaseDao {

	public SystemConfig find(PageObject pageObject) {
		StringBuilder sb = new StringBuilder("select s from SystemConfig s where 1=1 ");
		List list = this.getFivs_r().find(pageObject, sb.toString(),null);
		if (list!=null && list.size()>0){
			SystemConfig config = (SystemConfig) list.get(0);
			return config;
		}
		return null;
	}
	
	public SystemConfig find(){
		StringBuilder sb = new StringBuilder("select s from SystemConfig s where 1=1 ");
		List list = this.getFivs_r().find(sb.toString(),null);
		if (list!=null && list.size()>0){
			SystemConfig config = (SystemConfig) list.get(0);
			return config;
		}
		return null;
	}
}
