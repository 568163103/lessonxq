package com.beyeon.nvss.fault.control.action;

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
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.hibernate.fivsdb.TbAlarmType;
import com.beyeon.nvss.fault.model.bpo.AlarmLevelBpo;

@Component("alarmLevelAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AlarmLevelAction extends BaseAction {
	private AlarmLevelBpo alarmLevelBpo;
	private TbAlarmType alarmType;
	
	public TbAlarmType getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(TbAlarmType alarmType) {
		this.alarmType = alarmType;
	}

	public void setAlarmLevelBpo(AlarmLevelBpo alarmLevelBpo) {
		this.alarmLevelBpo = alarmLevelBpo;
	}
	
	public Object getModel() {
		if(null == alarmType){
			String id = this.getReqParam("id");
			if(StringUtils.isNotBlank(id)){
				alarmType = alarmLevelBpo.get(id);
			} else {
				alarmType = new TbAlarmType();
			}
		}
		return alarmType;
	}

	public String findPage(){
		Collection<AutoCompleteDto> types = AlarmType.getTypes();
		Iterator<AutoCompleteDto> it = types.iterator();
		List<AutoCompleteDto> list = new ArrayList<AutoCompleteDto>();
		while (it.hasNext()) {
			list.add(it.next()); 
		}
		String[] ids = new String[list.size()];
		for(int i=0;i<list.size();i++){
			ids[i]=list.get(i).getValue();
		}
		alarmLevelBpo.findAlarmLevel(this.getPageObject(),ids);
		return "findPage";
	}
	
	public String beforeUpdate(){
		this.setReqAttr("alarmLevels", DictBpo.find(TDict.ALARM_LEVEL));
		return "beforeUpdate";
	}

	public String update(){
		alarmLevelBpo.update(alarmType);
		return findPage();
	}

}
