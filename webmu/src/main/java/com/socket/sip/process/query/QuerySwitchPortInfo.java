package com.socket.sip.process.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.nvss.dmu.model.bpo.DmuSwitchPortBpo;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;	

public class QuerySwitchPortInfo extends SIPSocketUtil{
	public List<HashMap<String,Object>> query (Map<String, Object> requestMap){
			List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
			DmuSwitchPortBpo dmuSwitchPortBpo =(DmuSwitchPortBpo)SpringInit.getApplicationContext().getBean("dmuSwitchPortBpo");
			List<HashMap<String, Object>> elist = dmuSwitchPortBpo.findList();
			if (elist != null && elist.size()>0){
				for (int i =0 ; i< elist.size() ; i ++){
					HashMap<String, Object> equipment = elist.get(i);				
					HashMap<String,Object> smap = new HashMap<String,Object>();
					smap.put("id",equipment.get("switchid")!=null ? equipment.get("switchid") : "");					
					smap.put("ip",equipment.get("ip")!=null ? equipment.get("ip") : "");	
					smap.put("deviceId",equipment.get("deviceId")!=null ? equipment.get("deviceId") : "");		
					smap.put("port",equipment.get("port")!=null ? equipment.get("port") : "0");	
					list.add(smap);
				}
			}
			
		return list;
		
	}
}
