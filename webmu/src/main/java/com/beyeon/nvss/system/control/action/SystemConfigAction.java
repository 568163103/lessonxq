package com.beyeon.nvss.system.control.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.SystemConfig;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.system.model.dto.SystemConfigDto;

@Component("systemConfigAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SystemConfigAction  extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(SystemConfigAction.class);
	private DeviceFacade deviceFacade;
	private SystemConfigDto systemConfigDto;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public SystemConfigDto getSystemConfig() {
		return systemConfigDto;
	}
	public void setSystemConfig(SystemConfigDto systemConfigDto) {
		this.systemConfigDto = systemConfigDto;
	}

	public Object getModel() {
		if(null == systemConfigDto){
			systemConfigDto = new SystemConfigDto();
			systemConfigDto.setSystemConfig(SystemConfig.getConfig());
		}
		return systemConfigDto;
	}
	
	public String findPage(){
		this.setReqAttr("config", SystemConfig.getConfig());
		return "findPage";
	}
	public String beforeUpdate(){		
		return "beforeUpdate";
	}

	public String update(){
		deviceFacade.getSystemConfigBpo().update(systemConfigDto);
		return  findPage();
	}
	

}
