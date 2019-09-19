package com.beyeon.nvss.dmu.control.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.AlarmType;
import com.beyeon.hibernate.fivsdb.DmuAlarmType;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.nvss.dmu.model.bpo.DmuAlarmLevelBpo;

@Component("dmuAlarmLevelAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DmuAlarmLevelAction extends BaseAction {
	private DmuAlarmLevelBpo dmuAlarmLevelBpo;
	private DmuAlarmType alarmType;
	
	public DmuAlarmType getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(DmuAlarmType alarmType) {
		this.alarmType = alarmType;
	}

	public void setDmuAlarmLevelBpo(DmuAlarmLevelBpo dmuAlarmLevelBpo) {
		this.dmuAlarmLevelBpo = dmuAlarmLevelBpo;
	}
	
	public Object getModel() {
		if(null == alarmType){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				alarmType = dmuAlarmLevelBpo.get(id);
			} else {
				alarmType = new DmuAlarmType();
			}
		}
		return alarmType;
	}

	public String findPage(){
		
		dmuAlarmLevelBpo.findDmuAlarmLevel(this.getPageObject());
		return "findPage";
	}
	
	public String beforeUpdate(){
		this.setReqAttr("dmuAlarmLevels", DictBpo.find(TDict.ALARM_LEVEL));
		return "beforeUpdate";
	}

	public String update(){
		dmuAlarmLevelBpo.update(alarmType);
		return findPage();
	}

}
