package com.socket.sip.process.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.SwitchPortInfo;
import com.beyeon.nvss.dmu.model.bpo.DmuSwitchPortBpo;
import com.beyeon.nvss.dmu.model.bpo.SwitchStateRecordBpo;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;	

public class ReportSwitchPortInfo extends SIPSocketUtil{
	public Map<String,Object> report (Map<String, Object> requestMap){
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String command =(String) requestMap.get("@command");
		responseMap.put("@command", command);
		HashMap<String,Object> parameters = (HashMap<String, Object>)requestMap.get("parameters");
		if (parameters!=null ){
			String dmuId = (String) parameters.get("dmuId");
			String userId = (String) parameters.get("userId");
			String userName = (String) parameters.get("userName");
			String userLevel = (String) parameters.get("userLevel");
			String totalPacketNum = String.valueOf(parameters.get("totalPacketNum"));
			String curPacketNum =  String.valueOf(parameters.get("curPacketNum"));
			HashMap<String,Object> rgroup = (HashMap<String, Object>) parameters.get("group");
			if (rgroup!=null && dmuId!=null && userId!=null && userName !=null && userLevel !=null && totalPacketNum!=null && curPacketNum !=null){
				List<HashMap<String, Object>> rUrl = new ArrayList<HashMap<String, Object>>();
				HashMap<String, Object> rMap = new HashMap<String, Object>();
				try {
					rUrl = (ArrayList<HashMap<String, Object>>) rgroup.get("URL");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					rMap =  (HashMap<String, Object>) rgroup.get("URL");
				}
				DmuSwitchPortBpo dmuSwitchPortBpo =(DmuSwitchPortBpo)SpringInit.getApplicationContext().getBean("dmuSwitchPortBpo");
				
				for (HashMap<String,Object> url : rUrl){
					url.put("master", userId);
					deal(url,dmuSwitchPortBpo);
				}
				
				if (rMap!=null &&rMap.size()>0){
					rMap.put("master", userId);
					deal(rMap,dmuSwitchPortBpo);
				}
				addResult(responseMap,"0","success");	
			}else{
				addResult(responseMap,"37","parameter error"); //参数错误
			}
		}else{
			addResult(responseMap,"37","parameter error"); //参数错误
		}
		return responseMap;
		
	}
	
	private void deal(HashMap<String,Object> url,DmuSwitchPortBpo dmuSwitchPortBpo){
		String id = (String)url.get("id");
		String master = (String)url.get("master");
		String port = (String)url.get("port");					
		String ip = (String)url.get("ip");		
		SwitchPortInfo info = new SwitchPortInfo();
		info.setPort(Integer.parseInt(port));
		info.setSwitchid(id);
		info.setMaster(master);
		info.setIp(ip);
		SwitchPortInfo s = dmuSwitchPortBpo.findBySwitchIdAndPort(id,Integer.parseInt(port));
		if (s !=null){
			info.setId(s.getId());
			dmuSwitchPortBpo.update(info);
		}else{
			dmuSwitchPortBpo.save(info);
		}
		
	}
}
