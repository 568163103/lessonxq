package com.socket.sip.process.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.common.util.DateUtil;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.SwitchStateRecord;
import com.beyeon.hibernate.fivsdb.SwitchStateRecordPort;
import com.beyeon.nvss.dmu.model.bpo.DmuEquipmentBpo;
import com.beyeon.nvss.dmu.model.bpo.SwitchStateRecordBpo;
import com.beyeon.nvss.dmu.model.dto.SwitchStateRecordDto;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;

public class QuerySwitchStateRecord extends SIPSocketUtil{
	public Map<String,Object> query (Map<String, Object> requestMap){
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String command =(String) requestMap.get("@command");
		String flag =(String) requestMap.get("flag");
		responseMap.put("@command", command);
		
		if ("0".equals(flag)){   //Sa向下级查询
			HashMap<String,Object> parameters = (HashMap<String, Object>)requestMap.get("parameters");
			if (parameters!=null ){
				HashMap<String,Object> rgroup = (HashMap<String, Object>) parameters.get("group");
				if (rgroup!=null){
					HashMap<String,Object> rUrl = (HashMap<String, Object>) rgroup.get("URL");
					if (rUrl!=null && rUrl.get("resId")!=null && !"".equals(rUrl.get("resId"))){
						String id = (String) rUrl.get("resId");
						SwitchStateRecordBpo switchStateRecordBpo =(SwitchStateRecordBpo)SpringInit.getApplicationContext().getBean("switchStateRecordBpo");
						DmuEquipmentBpo dmuEquipmentBpo =(DmuEquipmentBpo)SpringInit.getApplicationContext().getBean("dmuEquipmentBpo");
						DmuEquipment equipment = dmuEquipmentBpo.findById(id);
						String type = equipment.getType();
						String master = equipment.getMaster();
						HashMap<String, String> result = SIPSocketUtil.queryState(id, type, master);
						if ("0".equals(result.get("code"))){
							SwitchStateRecord record = switchStateRecordBpo.findSwitchStateRecord(id);
							if (record!=null){
								HashMap<String,Object> recordMap = new HashMap<String,Object>();
								recordMap.put("resId", record.getSwitchid() !=null ? record.getSwitchid() : "");
								recordMap.put("memory", record.getMemory()!=null ? record.getMemory() : "");
								recordMap.put("cpu", record.getCpu()!=null ? record.getCpu() : "");
								recordMap.put("portNum", record.getPortNum()!=null ? record.getPortNum() : "");
								List<HashMap<String,Object>> group = new ArrayList<HashMap<String,Object>>();
								List<HashMap<String,String>> clist = new ArrayList<HashMap<String,String>> ();
								List<SwitchStateRecordPort> port = switchStateRecordBpo.getPort(record.getId());
								
								if (port!=null && port.size()>0){
									for (int i =0 ; i< port.size(); i++){
										HashMap<String,String> cobj = new HashMap<String,String>();
										SwitchStateRecordPort c = port.get(i);
										cobj.put("port", c.getPort());
										cobj.put("type", c.getType());
										cobj.put("rate", c.getRate());
										cobj.put("state", c.getState());
										cobj.put("ip", c.getIp());
										cobj.put("loss", c.getLoss());
										clist.add(cobj);
									}
								}else{
									HashMap<String,String> cobj = new HashMap<String,String>();
									cobj.put("port", "");
									cobj.put("type", "");
									cobj.put("rate", "");
									cobj.put("state", "");
									cobj.put("ip", "");
									cobj.put("loss", "");
									clist.add(cobj);
								}
								
								HashMap<String,Object> url = new HashMap<String,Object> ();
								url.put("url", clist);
								group.add(url);
								
								recordMap.put("group", group);
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
						String memory = (String) rUrl.get("memory");
						String cpu = (String) rUrl.get("cpu");
						String portNum = (String) rUrl.get("portNum");
						HashMap<String,Object> group2 = (HashMap<String, Object>) rUrl.get("group");
						if (res_id !=null && !"".equals(res_id)){
							SwitchStateRecordBpo switchStateRecordBpo =(SwitchStateRecordBpo)SpringInit.getApplicationContext().getBean("switchStateRecordBpo");
							SwitchStateRecordDto switchStateRecordDto = new SwitchStateRecordDto();
							SwitchStateRecord switchStateRecord = new SwitchStateRecord();
							switchStateRecord.setSwitchid(res_id);
							switchStateRecord.setMemory(memory);
							switchStateRecord.setCpu(cpu);
							switchStateRecord.setPortNum(portNum);
							switchStateRecord.setRecordTime(DateUtil.getTime());
							if (group2!=null && group2.size()>0){
								List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
								HashMap<String, String> rMap = new HashMap<String, String>();
								try {
									list = (List<HashMap<String, String>>) group2.get("url");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									rMap =  (HashMap<String, String>)group2.get("url");
								}
								
								List<SwitchStateRecordPort> clist = new ArrayList<SwitchStateRecordPort>();
								
								if (list !=null && list.size()>0){
									clist = deal(list);
								}
								if (rMap !=null && rMap.size()>0){
									clist = deal(rMap);
								}
								switchStateRecordDto.setSwitchStateRecord(switchStateRecord);
								switchStateRecordDto.setSwitchStateRecordPort(clist);
							}
							switchStateRecordBpo.save(switchStateRecordDto);
						}
						
					}
				}
			}
		}
		
		return responseMap;
		
	}
	
	private List<SwitchStateRecordPort> deal(List<HashMap<String, String>> list ){
		List<SwitchStateRecordPort> clist = new ArrayList<SwitchStateRecordPort>();
		for(HashMap<String,String> map : list){
			SwitchStateRecordPort p = new SwitchStateRecordPort();
			String port = map.get("port") !=null ?  map.get("port") : "";
			String type = map.get("type") !=null ?  map.get("type") : "";
			String rate = map.get("rate") !=null ?  map.get("rate") : "";
			String state = map.get("state") !=null ?  map.get("state") : "";
			String ip = map.get("ip") !=null ?  map.get("ip") : "";
			String loss = map.get("loss") !=null ?  map.get("loss") : "";
			p.setPort(port);
			p.setType(type);
			p.setRate(rate);
			p.setState(state);
			p.setIp(ip);
			p.setLoss(loss);
			clist.add(p);
		}		
		return clist;
	}
	
	private List<SwitchStateRecordPort> deal(HashMap<String,String> map ){
		List<SwitchStateRecordPort> list = new ArrayList<SwitchStateRecordPort>();
		String port = map.get("port") !=null ?  map.get("port") : "";
		SwitchStateRecordPort p = new SwitchStateRecordPort();
		if (!"".equals(port)){
			String type = map.get("type")!=null ?  map.get("type") : "";
			String rate = map.get("rate")!=null ?  map.get("rate") : "";
			String state = map.get("state")!=null ?  map.get("state") : "";
			String ip = map.get("ip")!=null ?  map.get("ip") : "";
			String loss = map.get("loss") !=null ?  map.get("loss") : "";
			p.setPort(port);
			p.setType(type);
			p.setRate(rate);
			p.setState(state);
			p.setIp(ip);
			p.setLoss(loss);
			list.add(p);
		}
		
		return list;
	}
}
