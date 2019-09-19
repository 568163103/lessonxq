package com.socket.sip.process.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.DmuAlarmRes;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.TbAlarmRes;
import com.beyeon.hibernate.fivsdb.TbAlarmType;
import com.beyeon.nvss.dmu.model.bpo.DmuAlarmLevelBpo;
import com.beyeon.nvss.dmu.model.bpo.DmuEquipmentBpo;
import com.beyeon.nvss.dmu.model.dto.DmuEquipmentDto;
import com.beyeon.nvss.fault.model.bpo.AlarmLevelBpo;
import com.beyeon.nvss.fault.model.bpo.AlarmResBpo;
import com.beyeon.nvss.fault.model.dto.AlarmResDto;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;
import org.apache.commons.lang3.StringUtils;

public class ReportAlarmRes extends SIPSocketUtil{
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
				AlarmResBpo alarmResBpo =(AlarmResBpo)SpringInit.getApplicationContext().getBean("alarmResBpo");
				if ("1".equals(curPacketNum)){
					alarmResBpo.deleteByMaster(userId);
				}
				List<HashMap<String, Object>> rUrl = new ArrayList<HashMap<String, Object>>();
				HashMap<String, Object> rMap = new HashMap<String, Object>();
				try {
					rUrl = (ArrayList<HashMap<String, Object>>) rgroup.get("URL");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					rMap =  (HashMap<String, Object>) rgroup.get("URL");
				}
				for (HashMap<String,Object> url : rUrl){
					deal(userId,url,alarmResBpo);
				}

				if (rMap!=null &&rMap.size()>0){
					deal(userId,rMap,alarmResBpo);
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
	
	private void deal(String fromId,HashMap<String,Object> url,AlarmResBpo alarmResBpo){
		String id = (String)url.get("id");
		String name = (String)url.get("name");					
//		String description = (String)url.get("description");
		String resId = id.substring(0, 16);
		String alarmType = id.substring(16, 21);
		String typeId = "";
		Map<String,String>  alarmTypeMap = TbAlarmType.getObjectMap();
		for (String key : alarmTypeMap.keySet()){
			if (alarmType.equals(alarmTypeMap.get(key))){
				typeId = key;
				break;
			}
		}
		if (StringUtils.isNotBlank(typeId)){
			TbAlarmRes tbAlarmRes = new TbAlarmRes();
			tbAlarmRes.setAlarmType(typeId);
			tbAlarmRes.setResId(resId);
			tbAlarmRes.setName(name);
			tbAlarmRes.setSid(fromId);

			TbAlarmRes alarmRes = alarmResBpo.findByResIdAndType(resId,typeId);
			AlarmResDto alarmResDto = new AlarmResDto();
			alarmResDto.setAlarmRes(tbAlarmRes);
			if (alarmRes == null){
				alarmResBpo.save(alarmResDto);
			}else{
				tbAlarmRes.setId(alarmRes.getId());
				alarmResBpo.update(alarmResDto);
			}
		}

		
	}
}
