package com.socket.sip.process.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.nvss.device.model.bpo.EquipmentBpo;
import com.beyeon.nvss.dmu.model.bpo.DmuEquipmentBpo;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;	

public class QueryDevicesRes extends SIPSocketUtil{
	public List<HashMap<String,Object>> query (Map<String, Object> requestMap){
			List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
			DmuEquipmentBpo dmuEquipmentBpo =(DmuEquipmentBpo)SpringInit.getApplicationContext().getBean("dmuEquipmentBpo");
			List<DmuEquipment> dlist = dmuEquipmentBpo.findAllDmuEquipment();
			for (DmuEquipment equipment : dlist){
				HashMap<String,Object> smap = new HashMap<String,Object>();
				smap.put("id",equipment.getId()!= null ? equipment.getId()  : "");
				smap.put("name",equipment.getName()!= null ? equipment.getName()  : "");
				smap.put("corp",equipment.getCorp()!= null ? equipment.getCorp()  : "");
				smap.put("type",equipment.getType()!= null ? equipment.getType()  : "");
				smap.put("version",equipment.getVersion()!= null ? equipment.getVersion()  : "");
				smap.put("pos",equipment.getPos()!= null ? equipment.getPos()  : "");
				smap.put("ip",equipment.getIp()!= null ? equipment.getIp()  : "");
				smap.put("mac",equipment.getMac()!= null ? equipment.getMac()  : "");
				smap.put("port",equipment.getPort()!=null ? equipment.getPort() :  0);
				smap.put("remark",equipment.getRemark()!= null ? equipment.getRemark()  : "");
				list.add(smap);
			}
			
		
		
		return list;
		
	}
	
}
