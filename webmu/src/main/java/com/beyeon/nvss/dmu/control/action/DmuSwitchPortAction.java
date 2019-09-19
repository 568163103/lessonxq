package com.beyeon.nvss.dmu.control.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.hibernate.fivsdb.AlarmType;
import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.hibernate.fivsdb.TbAlarmRes;
import com.beyeon.nvss.device.model.dao.EquipmentDao;
import com.beyeon.nvss.device.model.dto.EquipmentDto;
import com.beyeon.nvss.dmu.model.dao.DmuEquipmentDao;
import com.beyeon.nvss.fault.model.dao.AlarmResDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.SwitchPortInfo;
import com.beyeon.nvss.dmu.model.bpo.DmuSwitchPortBpo;
import com.beyeon.nvss.dmu.model.dto.DmuSwitchPortDto;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component("dmuSwitchPortAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DmuSwitchPortAction extends BaseAction {
	private DmuSwitchPortBpo dmuSwitchPortBpo;
	private DmuSwitchPortDto dmuSwitchPortDto;
	private EquipmentDao equipmentDao;
	private DmuEquipmentDao dmuEquipmentDao;
	private AlarmResDao  alarmResDao;
	public void setEquipmentDao(EquipmentDao equipmentDao) {
		this.equipmentDao = equipmentDao;
	}
	public void setDmuEquipmentDao(DmuEquipmentDao dmuEquipmentDao) {
		this.dmuEquipmentDao = dmuEquipmentDao;
	}
	public EquipmentDto get(String mid) {
		EquipmentDto equipmentDto = new EquipmentDto();
		equipmentDto.setEquipment(this.equipmentDao.getFivs(Equipment.class, mid));
		return equipmentDto;
	}

	public AlarmResDao getAlarmResDao() {
		return alarmResDao;
	}

	public void setAlarmResDao(AlarmResDao alarmResDao) {
		this.alarmResDao = alarmResDao;
	}

	public DmuSwitchPortDto getDmuSwitchPortDto() {
		return dmuSwitchPortDto;
	}
	public void setDmuSwitchPortDto(DmuSwitchPortDto dmuSwitchPortDto) {
		this.dmuSwitchPortDto = dmuSwitchPortDto;
	}
	public DmuSwitchPortBpo getDmuSwitchPortBpo() {
		return dmuSwitchPortBpo;
	}
	public void setDmuSwitchPortBpo(DmuSwitchPortBpo dmuSwitchPortBpo) {
		this.dmuSwitchPortBpo = dmuSwitchPortBpo;
	}
	
	public Object getModel() {
		if(null == dmuSwitchPortDto){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				dmuSwitchPortDto = dmuSwitchPortBpo.get(id);
			} else {
				dmuSwitchPortDto = new DmuSwitchPortDto();
			}
		}
		return dmuSwitchPortDto;
	}
		
	public String update(){
		String id = this.getReqParam("id");
		String switchid = this.getReqParam("switchid");
		String port = this.getReqParam("port");
		String ip = this.getReqParam("ip");
		String deviceid = this.getReqParam("deviceid");
		SwitchPortInfo info = new SwitchPortInfo();
		info.setId(Integer.parseInt(id));
		info.setIp(ip);
		info.setPort(Integer.parseInt(port));
		info.setSwitchid(switchid);
		info.setDeviceid(deviceid);
		this.dmuSwitchPortBpo.update(info);
		this.setReqAttr("serverId",switchid);
		return findPage();
	}
	@Transactional(propagation= Propagation.REQUIRED,readOnly=false)
	public String save(){
		String switchid = this.getReqParam("switchid");
		String port = this.getReqParam("port");
		String ip = this.getReqParam("ip");
		String deviceid = this.getReqParam("deviceid");
		SwitchPortInfo info = new SwitchPortInfo();
		info.setIp(ip);
		info.setPort(Integer.parseInt(port));
		info.setSwitchid(switchid);
		info.setDeviceid(deviceid);
		this.dmuSwitchPortBpo.save(info);
		this.setReqAttr("serverId",switchid);
		Map<String,String> params = new HashMap<>();
		params.put("id",switchid);
		Equipment equipment =equipmentDao.findByParam(params).get(0);
		//添加交换机告警
		if (StringUtils.isNotBlank(port)) {
			String tempId = null;
			tempId = switchid;
			StringBuffer alremIdBuffer = new StringBuffer(tempId);
			alremIdBuffer.replace(13, 14, "1");
			String strHex = Integer.toHexString(Integer.parseInt(port));
			if (strHex.length() == 1) {
				alremIdBuffer.replace(15, 16, strHex);
			} else if (strHex.length() == 2) {
				alremIdBuffer.replace(14, 16, strHex);
			}
			TbAlarmRes alarmRes = new TbAlarmRes(alremIdBuffer.toString(), "34", equipment.getName() + "(" + "端口流量告警" + ")",switchid);
			this.equipmentDao.saveFivs(alarmRes);
		}

		return findPage();
	}
	public String delete(){
		String[] ids = this.getReqParams("items");
		String serverid = this.getReqParam("serverId");
		this.setReqAttr("serverId",serverid);
		dmuSwitchPortBpo.delete(ids);	
		return findPage();
	}
	public String findPage(){		
		String serverId = this.getReqParam("items");
		Object serverId1  = this.getReqAttr("serverId");
		if(serverId1!=null){
			serverId = serverId1.toString();
		}
		
		this.setReqAttr("serverId",serverId);
		PageObject pageObject = getPageObject();
		Map<String, String> params = pageObject.getParams();
		params.put("resId", serverId);
		pageObject.setParams(params);
		dmuSwitchPortBpo.find(pageObject);
		
		if (dmuSwitchPortBpo.checkSelf(serverId)){
			return "findSelfPage";
		}
		return "findPage";
	}
	public String beforeSave(){
		String serverId = this.getReqParam("serverId");
		Object serverId1  = this.getReqAttr("serverId");
		if(serverId1!=null){
			serverId = serverId1.toString();
		}
		
		this.setReqAttr("serverId",serverId);
		return "beforeSave";
	}
	public String beforeUpdate(){
		SwitchPortInfo port = dmuSwitchPortDto.getDmuSwitchPort();
		this.setReqAttr("serverId",port.getSwitchid());
		this.setReqAttr("port",port.getPort());
		this.setReqAttr("ip",port.getIp());
		this.setReqAttr("deviceid",port.getDeviceid());		
		this.setReqAttr("id",port.getId());		
		return "beforeUpdate";
	}
	public String findPageSelf(){		
		String serverId = this.getReqParam("items");
		Object serverId1  = this.getReqAttr("serverId");
		if(serverId1!=null){
			serverId = serverId1.toString();
		}
		
		this.setReqAttr("serverId",serverId);
		PageObject pageObject = getPageObject();
		Map<String, String> params = pageObject.getParams();
		params.put("sid", serverId);
		pageObject.setParams(params);
		dmuSwitchPortBpo.find(pageObject);
		return "findSelfPage";
	}


	
}
