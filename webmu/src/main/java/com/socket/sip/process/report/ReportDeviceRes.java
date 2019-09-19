package com.socket.sip.process.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.nvss.dmu.model.bpo.DmuEquipmentBpo;
import com.beyeon.nvss.dmu.model.dto.DmuEquipmentDto;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;	

public class ReportDeviceRes extends SIPSocketUtil{
	public Map<String,Object> report (Map<String, Object> requestMap){
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String command =(String) requestMap.get("@command");
		responseMap.put("@command", command);
//		String master = (String) requestMap.get("master");
//		String fromIp ="";
//		if (master!=null ){
//			try {
//				fromIp = master.split(":")[0].replaceAll("\u002f", "");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//			}
//		}
		Map<String, Object> paramMap  = new HashMap<String, Object>();
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
				DmuEquipmentBpo dmuEquipmentBpo =(DmuEquipmentBpo)SpringInit.getApplicationContext().getBean("dmuEquipmentBpo");
				if ("1".equals(curPacketNum)){
					dmuEquipmentBpo.deleteByMaster(userId);
				}
				List<HashMap<String, Object>> rUrl = new ArrayList<HashMap<String, Object>>();
				HashMap<String, Object> rMap = new HashMap<String, Object>();
				try {
					rUrl = (ArrayList<HashMap<String, Object>>) rgroup.get("URL");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					rMap = ( HashMap<String, Object> ) rgroup.get("URL");
				}
				for (HashMap<String,Object> url : rUrl){
					deal(userId,url,dmuEquipmentBpo);
				}
				if (rMap !=null && rMap.size()>0){
					deal(userId,rMap,dmuEquipmentBpo);
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
	
	private void deal(String fromIp,HashMap<String,Object> url,DmuEquipmentBpo dmuEquipmentBpo){
			String id = (String)url.get("id");
			String name = (String)url.get("name");
			String corp = (String)url.get("corp");
			String type = (String)url.get("type");
			String version = (String)url.get("version");
			String pos = (String)url.get("pos");
			String ip = (String)url.get("ip");
			String mac = (String)url.get("mac");
			Integer port = Integer.parseInt(String.valueOf(url.get("port")));
			String remark = (String)url.get("remark");

			DmuEquipment dmu = new DmuEquipment();
			dmu.setId(id);
			dmu.setName(name);
			dmu.setCorp(corp);
			dmu.setType(type);
			dmu.setVersion(version);
			dmu.setPos(pos);
			dmu.setIp(ip);
			dmu.setMac(mac);
			dmu.setPort(port);
			dmu.setRemark(remark);
			dmu.setMaster(fromIp);
			dmu.setStatus(1);
			DmuEquipmentDto dto = new DmuEquipmentDto();
			dto.setDmuEquipment(dmu);

			DmuEquipment equ = dmuEquipmentBpo.findById(id);
			if (equ==null ){
				dmuEquipmentBpo.save(dto);
			}else{
				dmuEquipmentBpo.update(dto);
			}
	}
}
