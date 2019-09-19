package com.socket.sip.process.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.common.util.DateUtil;
import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.nvss.dmu.model.bpo.DiskStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.DmuEquipmentBpo;
import com.beyeon.nvss.dmu.model.dto.DiskStateRecordDto;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;	

public class QueryDiskStateRecord extends SIPSocketUtil{
	public Map<String,Object> query (Map<String, Object> requestMap){
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String command =(String) requestMap.get("@command");
		String flag =(String) requestMap.get("flag");
		responseMap.put("@command", command);
		
		if ("0".equals(flag)){   //Sa向下级级联查询
			HashMap<String,Object> parameters = (HashMap<String, Object>)requestMap.get("parameters");
			if (parameters!=null ){
				HashMap<String,Object> rgroup = (HashMap<String, Object>) parameters.get("group");
				if (rgroup!=null){
					HashMap<String,Object> rUrl = (HashMap<String, Object>) rgroup.get("URL");
					if (rUrl!=null && rUrl.get("resId")!=null && !"".equals(rUrl.get("resId"))){
						String id = (String) rUrl.get("resId");
						DiskStateRecordBpo diskStateRecordBpo =(DiskStateRecordBpo)SpringInit.getApplicationContext().getBean("diskStateRecordBpo");
						DmuEquipmentBpo dmuEquipmentBpo =(DmuEquipmentBpo)SpringInit.getApplicationContext().getBean("dmuEquipmentBpo");
						DmuEquipment equipment = dmuEquipmentBpo.findById(id);
						String type = equipment.getType();
						String master = equipment.getMaster();
						HashMap<String, String> result = SIPSocketUtil.queryState(id, type, master);
						if ("0".equals(result.get("code"))){
							DiskStateRecord record = diskStateRecordBpo.findDiskStateRecord(id);
							if (record !=null ){
								HashMap<String,Object> recordMap = new HashMap<String,Object>();
								recordMap.put("resId", record.getDiskid() !=null ? record.getDiskid() : "");
								recordMap.put("totalvolume", record.getTotalvolume() !=null ? record.getTotalvolume() : "");
								recordMap.put("undistributed", record.getUndistributed()!=null ? record.getUndistributed() : "");
								recordMap.put("portNum", record.getPortNum()!=null ? record.getPortNum() : "");
								recordMap.put("cpu", record.getCpu()!=null ? record.getCpu() : "");
								recordMap.put("fan", record.getFan()!=null ? record.getFan() : "");
								recordMap.put("bad", record.getBad()!=null ? record.getBad() : "");
								recordMap.put("state", record.getState()!=null ? record.getState() : "");
								
								HashMap<String,Object> url2 = new HashMap<String,Object> ();
								url2.put("URL", recordMap);
								
								HashMap<String,Object> group2 = new HashMap<String,Object> ();
								group2.put("group", url2);
								
								addResult(responseMap,"0","success");				
								responseMap.put("parameters", group2);
							}else{
								addResult(responseMap,"47","no result");   //无数据
							}	
						
						}else{
							addResult(responseMap,result.get("code"),"no result");   //无数据
						}	
						
					}else{
						addResult(responseMap,"37","parameter error"); //参数错误
					}
				}else{
					addResult(responseMap,"37","parameter error"); //参数错误
				}
				
			}else{
				addResult(responseMap,"37","parameter error"); //参数错误
			}
		}else if ("1".equals(flag)){   //上级向SA查询收到回复
			HashMap<String ,Object> parameters = (HashMap<String, Object>) requestMap.get("parameters");
			if (parameters !=null && parameters.size()>0){
				HashMap<String ,Object> group = (HashMap<String, Object>) parameters.get("group");
				if (group !=null && group.size()>0){
					HashMap<String ,Object> rUrl = (HashMap<String, Object>) group.get("URL");
					if (rUrl !=null && rUrl.size()>0){
						String res_id = (String) rUrl.get("resId");
						String totalvolume = (String) rUrl.get("totalvolume");
						String undistributed = (String) rUrl.get("undistributed");
						String portNum = (String) rUrl.get("portNum");
						String cpu = (String) rUrl.get("cpu");
						String fan = (String) rUrl.get("fan");
						String bad = (String) rUrl.get("bad");
						String state = (String) rUrl.get("state");
						if (res_id !=null && !"".equals(res_id)){
							DiskStateRecordBpo diskStateRecordBpo =(DiskStateRecordBpo)SpringInit.getApplicationContext().getBean("diskStateRecordBpo");
							DiskStateRecordDto diskStateRecordDto =new DiskStateRecordDto();
							DiskStateRecord diskStateRecord = new DiskStateRecord();
							diskStateRecord.setDiskid(res_id);
							diskStateRecord.setTotalvolume(totalvolume);
							diskStateRecord.setUndistributed(undistributed);
							diskStateRecord.setPortNum(portNum);
							diskStateRecord.setCpu(cpu);
							diskStateRecord.setFan(fan);
							diskStateRecord.setBad(bad);
							diskStateRecord.setState(state);
							diskStateRecord.setRecordTime(DateUtil.getTime());
							diskStateRecordDto.setDiskStateRecord(diskStateRecord);
							diskStateRecordBpo.save(diskStateRecordDto);
						}
						
					}
				}
			}
		}
		
		return responseMap;
		
	}
}

