package com.beyeon.nvss.fault.control.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.hibernate.fivsdb.AlarmType;
import com.beyeon.hibernate.fivsdb.DmuAlarmType;
import com.beyeon.hibernate.fivsdb.TbAlarmType;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.fault.model.dto.TbAlarmsubcriTypeDto;

@Component("tbAlarmsubcriTypeAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TbAlarmsubcriTypeAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(TbAlarmsubcriTypeAction.class);
	private DeviceFacade deviceFacade;
	private TbAlarmsubcriTypeDto tbAlarmsubcriTypeDto;
	private static String code = null;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public TbAlarmsubcriTypeDto getTbAlarmsubcriType() {
		return tbAlarmsubcriTypeDto;
	}
	public void setTbAlarmsubcriType(TbAlarmsubcriTypeDto tbAlarmsubcriTypeDto) {
		this.tbAlarmsubcriTypeDto = tbAlarmsubcriTypeDto;
	}

	public Object getModel() {
		if(null == tbAlarmsubcriTypeDto){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				
				tbAlarmsubcriTypeDto = deviceFacade.getTbAlarmsubcriTypeBpo().get(id);				
			} else {
				tbAlarmsubcriTypeDto = new TbAlarmsubcriTypeDto();
			}
		}
		return tbAlarmsubcriTypeDto;
	}
	
	public String beforeUpdate(){
		return "beforeUpdate";
	}

	public String update(){
		deviceFacade.getTbAlarmsubcriTypeBpo().update(tbAlarmsubcriTypeDto);
		return findPage();
	}

	public String beforeSave(){		
		Collection<AutoCompleteDto> s = AlarmType.getTypes();
		Map<String, String> typeMap = new HashMap<String, String>();
		
		Map<String, String> dmuTypeMap = DmuAlarmType.getTypeMap();
		for (Map.Entry<String, String> entry : dmuTypeMap.entrySet()) { 
			typeMap.put(entry.getKey(), entry.getValue());
		}
		for (AutoCompleteDto o : AlarmType.getTypes()) {
			typeMap.put(TbAlarmType.getObjectName(o.getValue()), o.getLabel());
		}
		this.setReqAttr("alarmTypes", typeMap);
		return "beforeSave";
	}
	
	public String save(){		
		deviceFacade.getTbAlarmsubcriTypeBpo().save(tbAlarmsubcriTypeDto);		
		return findPage();
	}
	
	public String delete(){
		String[] ids = this.getReqParams("items");
		deviceFacade.getTbAlarmsubcriTypeBpo().delete(ids);
		return findPage();
	}
	public String findPage(){
		Collection<AutoCompleteDto> s = AlarmType.getTypes();
		Map<String, String> typeMap = new HashMap<String, String>();
		
		Map<String, String> dmuTypeMap = DmuAlarmType.getTypeMap();
		for (Map.Entry<String, String> entry : dmuTypeMap.entrySet()) { 
			typeMap.put(entry.getKey(), entry.getValue());
		}
		for (AutoCompleteDto o : AlarmType.getTypes()) {
			typeMap.put(TbAlarmType.getObjectName(o.getValue()), o.getLabel());
		}
		this.setReqAttr("alarmTypes", typeMap);
		deviceFacade.getTbAlarmsubcriTypeBpo().find(getPageObject());
		return "findPage";
	}
	
	
}
