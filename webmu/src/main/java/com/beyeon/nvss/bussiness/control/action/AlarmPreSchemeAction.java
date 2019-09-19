package com.beyeon.nvss.bussiness.control.action;

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
import com.beyeon.hibernate.fivsdb.AlarmPreScheme;
import com.beyeon.hibernate.fivsdb.AlarmType;
import com.beyeon.hibernate.fivsdb.Channel;
import com.beyeon.hibernate.fivsdb.Encoder;
import com.beyeon.hibernate.fivsdb.PreScheme;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.nvss.bussiness.model.BussinessFacade;
import com.beyeon.nvss.device.model.DeviceFacade;

@Component("alarmPreSchemeAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AlarmPreSchemeAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(AlarmPreSchemeAction.class);
	private BussinessFacade bussinessFacade;
	private AlarmPreScheme alarmPreScheme;
	private AlarmPreScheme oldAlarmPreScheme;
	private DeviceFacade deviceFacade;
	
	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public void setBussinessFacade(BussinessFacade bussinessFacade) {
		this.bussinessFacade = bussinessFacade;
	}

	public AlarmPreScheme getOldAlarmPreScheme() {
		return oldAlarmPreScheme;
	}

	public void setOldAlarmPreScheme(AlarmPreScheme oldAlarmPreScheme) {
		this.oldAlarmPreScheme = oldAlarmPreScheme;
	}

	public Object getModel() {
		if(null == alarmPreScheme){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				alarmPreScheme = new AlarmPreScheme(Integer.valueOf(id.split(",")[0]),id.split(",")[1],Integer.valueOf(id.split(",")[2]));
			} else {
				alarmPreScheme = new AlarmPreScheme();
			}
		}
		return alarmPreScheme;
	}

	public String beforeUpdate(){
		this.setReqAttr("alarmTypes", AlarmType.getTypes());
		this.setReqAttr("preSchemes", PreScheme.getObjectMap());
		return "beforeUpdate";
	}

	public String update(){
		bussinessFacade.getAlarmPreSchemeBpo().update(alarmPreScheme, oldAlarmPreScheme);
		return findPage();
	}

	public String beforeSave(){
		this.setReqAttr("alarmTypes", AlarmType.getTypes());
		this.setReqAttr("preSchemes", PreScheme.getObjectMap());
		return "beforeSave";
	}

	public String save(){
		bussinessFacade.getAlarmPreSchemeBpo().save(alarmPreScheme);
		return findPage();
	}

	public String delete(){
		String[] ids = this.getReqParams("items");
		bussinessFacade.getAlarmPreSchemeBpo().delete(ids);
		return findPage();
	}

	public String findPage(){
		this.setReqAttr("alarmTypes", AlarmType.getTypes());
		bussinessFacade.getAlarmPreSchemeBpo().find(getPageObject());
		return "findPage";
	}

	public String findChannel(){
		String alarmType = this.getReqParam("alarmType");
		//编码器断开
		if("11".equals(alarmType)){
			List<Encoder> list = deviceFacade.getEncoderBpo().findAllEncoder();
			Map<String, String> map = new HashMap<String, String>();
			for(Encoder encoder :list){
				map.put(encoder.getId(), encoder.getName());
			}
			responseJSON(map);
			
		}//服务器断开
		else if("9".equals(alarmType)){
			List<Server> list = deviceFacade.getServerBpo().findAllServer();
			Map<String, String> map = new HashMap<String, String>();
			for(Server server :list){
				map.put(server.getId(), server.getName());
			}
			responseJSON(map);
		}else if("7".equals(alarmType)){
		}else if("8".equals(alarmType)){
		}else {
			responseJSON(Channel.getObjectMap());
		}
		
		return null;
	}

}
