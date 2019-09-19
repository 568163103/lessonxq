package com.socket.sip.process.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.*;
import com.beyeon.nvss.device.model.bpo.EncoderBpo;
import com.beyeon.nvss.device.model.bpo.EquipmentBpo;
import com.beyeon.nvss.device.model.bpo.ServerBpo;
import com.beyeon.nvss.dmu.model.bpo.DmuAlarmLevelBpo;
import com.beyeon.nvss.dmu.model.bpo.DmuEquipmentBpo;
import com.beyeon.nvss.fault.model.bpo.AlarmResBpo;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;	

public class QueryAlarmRes extends SIPSocketUtil{
	public List<HashMap<String,Object>> query (Map<String, Object> requestMap){

		AlarmResBpo alarmResBpo =(AlarmResBpo)SpringInit.getApplicationContext().getBean("alarmResBpo");
		List<TbAlarmRes> dlist = alarmResBpo.findDmuAlarmRes();
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		for (TbAlarmRes equipment : dlist){
				HashMap<String,Object> smap = new HashMap<String,Object>();
				String id = equipment.getResId() + equipment.getAlarmType();
				smap.put("id", id);
				smap.put("name", equipment.getName());
				smap.put("description", equipment.getSid());
				list.add(smap);
		}
		
		return list;
		
	}
	
	
}
