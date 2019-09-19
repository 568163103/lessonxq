package com.beyeon.nvss.system.model.bpo;

import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.SystemConfig;
import com.beyeon.nvss.system.model.dao.SystemConfigDao;
import com.beyeon.nvss.system.model.dto.SystemConfigDto;
@Cache(cacheName = "系统设置")
@Component("systemConfigBpo")
public class SystemConfigBpo  {
	private SystemConfigDao systemConfigDao;
	
	
	public SystemConfigDao getSystemConfigDao() {
		return systemConfigDao;
	}

	public void setSystemConfigDao(SystemConfigDao systemConfigDao) {
		this.systemConfigDao = systemConfigDao;
	}

	public void update(SystemConfigDto systemConfigDto) {		
		SystemConfig.setConfig(systemConfigDto.getSystemConfig());
		this.systemConfigDao.updateFivs(systemConfigDto.getSystemConfig());
	}
	
	public SystemConfigDto get(String mid) {
		SystemConfigDto systemConfigDto = new SystemConfigDto();
		systemConfigDto.setSystemConfig(this.systemConfigDao.getFivs(SystemConfig.class, Integer.parseInt(mid)));
		return systemConfigDto;
	}

	public void find(PageObject pageObject) {
		this.systemConfigDao.find(pageObject);
	}
	
	
	@Cache(cacheName = "系统设置")
	public void findSystemConfig() {
		SystemConfig config = this.systemConfigDao.find();		
		if (config !=null ){
			SystemConfig.setConfig(config);
		}else{
			SystemConfig conf = new SystemConfig();
			conf.setMaxErrorCount(5);
			conf.setErrorLoginConfig(0);
			conf.setUserLockTime(1200);
			conf.setPwdResetFlag(0);
			conf.setKeepPasswordTime(43200);
			conf.setPwdRemaindTime(10800);
			conf.setExceptionUser("superadmin,admin,");
			this.systemConfigDao.saveFivs(conf);
			SystemConfig.setConfig(conf);
		}		
	}

	
}
