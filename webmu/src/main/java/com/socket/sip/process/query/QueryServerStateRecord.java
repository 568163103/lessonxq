package com.socket.sip.process.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.util.DateUtil;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.ServerStateRecord;
import com.beyeon.hibernate.fivsdb.ServerStateRecordCpu;
import com.beyeon.hibernate.fivsdb.ServerStateRecordDisk;
import com.beyeon.hibernate.fivsdb.ServerStateRecordNetcard;
import com.beyeon.nvss.dmu.model.bpo.DiskStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.DmuEquipmentBpo;
import com.beyeon.nvss.dmu.model.bpo.ServerStateRecordBpo;
import com.beyeon.nvss.dmu.model.dto.ServerStateRecordDto;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;

public class QueryServerStateRecord extends SIPSocketUtil{
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
						DmuEquipmentBpo dmuEquipmentBpo =(DmuEquipmentBpo)SpringInit.getApplicationContext().getBean("dmuEquipmentBpo");
						DmuEquipment equipment = dmuEquipmentBpo.findById(id);
						String type = equipment.getType();
						String master = equipment.getMaster();
						HashMap<String, String> result = SIPSocketUtil.queryState(id, type, master);
						if ("0".equals(result.get("code"))){
							HashMap<String,Object> group2 = new HashMap<String,Object> ();
							ServerStateRecordBpo serverStateRecordBpo =(ServerStateRecordBpo)SpringInit.getApplicationContext().getBean("serverStateRecordBpo");
							ServerStateRecord record = serverStateRecordBpo.findServerStateRecord((String) rUrl.get("resId"));			
							if (record!=null ){
								HashMap<String,Object> recordMap = new HashMap<String,Object>();
								recordMap.put("resId", record.getServerid() !=null ? record.getServerid() : "");
								recordMap.put("systemInfo",  record.getSystemInfo()!=null ? record.getSystemInfo() : "");
								recordMap.put("diskNum",  record.getDiskNum()!=null ? record.getDiskNum() : "");
								recordMap.put("totalMem",  record.getTotalMem()!=null ? record.getTotalMem() : "");
								recordMap.put("usedMem",  record.getUsedMem()!=null ? record.getUsedMem() : "");
								recordMap.put("leftMem",  record.getLeftMem()!=null ? record.getLeftMem() : "");
								recordMap.put("cpuNum",  record.getCpuNum()!=null ? record.getCpuNum() : "");
								recordMap.put("networkCard",  record.getNetworkCard()!=null ? record.getNetworkCard() : "");
								List<HashMap<String,Object>> group = new ArrayList<HashMap<String,Object>>();
								List<HashMap<String,String>> clist = new ArrayList<HashMap<String,String>> ();
								List<HashMap<String,String>> dlist = new ArrayList<HashMap<String,String>> ();
								List<HashMap<String,String>> nlist = new ArrayList<HashMap<String,String>> ();			
								List<ServerStateRecordCpu> cpu = serverStateRecordBpo.getCpu((record.getId()));
								
								for (ServerStateRecordCpu c : cpu){
									HashMap<String,String> cobj = new HashMap<String,String>();
									cobj.put("cpuPercent", c.getCpuPercent() !=null ? c.getCpuPercent() : "");
									cobj.put("cpuTemp", c.getCpuTemp() !=null ? c.getCpuTemp(): "");
									clist.add(cobj);
								}
								if (clist.size()==0){
									HashMap<String,String> cobj = new HashMap<String,String>();
									cobj.put("cpuPercent", "");
									cobj.put("cpuTemp", "");
									clist.add(cobj);
								}		
								
								HashMap<String,Object> url = new HashMap<String,Object> ();
								url.put("url", clist);
								recordMap.put("group_cpu", url);
								
								List<ServerStateRecordDisk> disk = serverStateRecordBpo.getDisk(record.getId());
								
								for (ServerStateRecordDisk d : disk){
									HashMap<String,String> dobj = new HashMap<String,String>();
									dobj.put("driver", d.getDriver()!=null ? d.getDriver() : "");
									dobj.put("total", d.getTotal()!=null ? d.getTotal() : "");
									dobj.put("used", d.getUsed()!=null ? d.getUsed() : "");
									dobj.put("left", d.getLeft()!=null ? d.getLeft() : "");
									dlist.add(dobj);
								}

								if (dlist.size()==0){
									HashMap<String,String> dobj = new HashMap<String,String>();
									dobj.put("driver", "");
									dobj.put("total", "");
									dobj.put("used", "");
									dobj.put("left", "");
									dlist.add(dobj);
								}
								HashMap<String,Object> url1 = new HashMap<String,Object> ();
								url1.put("url", dlist);
								recordMap.put("group_disk", url1);
								
								List<ServerStateRecordNetcard> netcard = serverStateRecordBpo.getNetcard(record.getId());
								
								for (ServerStateRecordNetcard n : netcard){
									HashMap<String,String> nobj = new HashMap<String,String>();
									nobj.put("port", n.getPort() != null ? n.getPort() : "");
									nobj.put("portState", n.getPortState()!= null ? n.getPortState() : "");
									nobj.put("bandwidth", n.getBandwidth()!= null ? n.getBandwidth() : "");
									nobj.put("dataFlow", n.getDataFlow()!= null ? n.getDataFlow() : "");
									nobj.put("ip", n.getIp()!= null ? n.getIp() : "");
									nobj.put("mac", n.getMac()!= null ? n.getMac() : "");
									nlist.add(nobj);
								}
								if(nlist.size()==0) {
									HashMap<String,String> nobj = new HashMap<String,String>();
									nobj.put("port", "");
									nobj.put("portState", "");
									nobj.put("bandwidth", "");
									nobj.put("dataFlow", "");
									nobj.put("ip","");
									nobj.put("mac", "");
									nlist.add(nobj);
								}
								HashMap<String,Object> url11 = new HashMap<String,Object> ();
								url11.put("url", nlist);
								recordMap.put("group_netcard", url11);
								HashMap<String,Object> url2 = new HashMap<String,Object> ();
								url2.put("URL", recordMap);
								
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
						String systemInfo = (String) rUrl.get("systemInfo");
						String diskNum = (String) rUrl.get("diskNum");
						String totalMem = (String) rUrl.get("totalMem");
						String usedMem = (String) rUrl.get("usedMem");
						String leftMem = (String) rUrl.get("leftMem");
						String cpuNum = (String) rUrl.get("cpuNum");
						String networkCard = (String) rUrl.get("networkCard");						
						if (res_id !=null && !"".equals(res_id)){
							ServerStateRecordBpo serverStateRecordBpo =(ServerStateRecordBpo)SpringInit.getApplicationContext().getBean("serverStateRecordBpo");
							ServerStateRecordDto serverStateRecordDto = new ServerStateRecordDto();
							ServerStateRecord serverStateRecord = new ServerStateRecord();
							serverStateRecord.setServerid(res_id);
							serverStateRecord.setSystemInfo(systemInfo);
							serverStateRecord.setDiskNum(diskNum);
							serverStateRecord.setTotalMem(totalMem);
							serverStateRecord.setUsedMem(usedMem);
							serverStateRecord.setLeftMem(leftMem);
							serverStateRecord.setCpuNum(cpuNum);
							serverStateRecord.setNetworkCard(networkCard);
							serverStateRecord.setRecordTime(DateUtil.getTime());
							HashMap<String, Object> url2 = new HashMap<String, Object>();
							List<HashMap<String, Object>> group2 = new ArrayList<HashMap<String, Object>>();
							try {
								group2 = (List<HashMap<String, Object>>) rUrl.get("group");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								url2 = (HashMap<String, Object>) rUrl.get("group");
							}
							List<ServerStateRecordCpu> clist = new ArrayList<ServerStateRecordCpu>();
							List<ServerStateRecordDisk> dlist = new ArrayList<ServerStateRecordDisk>();
							List<ServerStateRecordNetcard> nlist = new ArrayList<ServerStateRecordNetcard>();

							
							for (HashMap<String,Object> url : group2){
								HashMap<String, String> url3 = new HashMap<String, String>();
								List<HashMap<String, String>> urlList = new ArrayList<HashMap<String, String>>();
								try {
									urlList = (List<HashMap<String, String>>) url.get("url");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									url3 = (HashMap<String, String>) url.get("url");
								}
								for (HashMap<String,String> url4 : urlList){
									if (url4.containsKey("driver") && StringUtils.isNotBlank(url4.get("driver"))){
										ServerStateRecordDisk disk = new ServerStateRecordDisk();
											String drive = url4.get("driver");
											String total = url4.get("total");
											String used = url4.get("used");
											String left = url4.get("left");
											disk.setDriver(drive);
											disk.setTotal(total);
											disk.setUsed(used);
											disk.setLeft(left);
											dlist.add(disk);
									}else if (url4.containsKey("cpuPercent")  && StringUtils.isNotBlank(url4.get("cpuPercent"))){
										ServerStateRecordCpu cpu = new ServerStateRecordCpu();
											String cpuPercent = url4.get("cpuPercent");
											String cpuTemp = url4.get("cpuTemp");										
											cpu.setCpuPercent(cpuPercent);
											cpu.setCpuTemp(cpuTemp);
											clist.add(cpu);
									}else if (url4.containsKey("port")  && StringUtils.isNotBlank(url4.get("port"))){
										ServerStateRecordNetcard netcard = new ServerStateRecordNetcard();
											String port = url4.get("port");
											String portState = url4.get("portState");
											String bandwidth = url4.get("bandwidth");
											String dataFlow = url4.get("dataFlow");
											String ip = url4.get("ip");
											String mac = url4.get("mac");										
											netcard.setPort(port);
											netcard.setPortState(portState);
											netcard.setBandwidth(bandwidth);
											netcard.setDataFlow(dataFlow);
											netcard.setIp(ip);
											netcard.setMac(mac);
											nlist.add(netcard);										
									}
								}
								
								if (url3.size()>0){
									if (url3.containsKey("driver") && StringUtils.isNotBlank(url3.get("driver"))){
										ServerStateRecordDisk disk = new ServerStateRecordDisk();
											String drive = url3.get("driver");
											String total = url3.get("total");
											String used = url3.get("used");
											String left = url3.get("left");
											disk.setDriver(drive);
											disk.setTotal(total);
											disk.setUsed(used);
											disk.setLeft(left);
											dlist.add(disk);
									}else if (url3.containsKey("cpuPercent")  && StringUtils.isNotBlank(url3.get("cpuPercent"))){
										ServerStateRecordCpu cpu = new ServerStateRecordCpu();
											String cpuPercent = url3.get("cpuPercent");
											String cpuTemp = url3.get("cpuTemp");										
											cpu.setCpuPercent(cpuPercent);
											cpu.setCpuTemp(cpuTemp);
											clist.add(cpu);
									}else if (url3.containsKey("port")  && StringUtils.isNotBlank(url3.get("port"))){
										ServerStateRecordNetcard netcard = new ServerStateRecordNetcard();
											String port = url3.get("port");
											String portState = url3.get("portState");
											String bandwidth = url3.get("bandwidth");
											String dataFlow = url3.get("dataFlow");
											String ip = url3.get("ip");
											String mac = url3.get("mac");										
											netcard.setPort(port);
											netcard.setPortState(portState);
											netcard.setBandwidth(bandwidth);
											netcard.setDataFlow(dataFlow);
											netcard.setIp(ip);
											netcard.setMac(mac);
											nlist.add(netcard);										
									}
								}
								
								
							
								
							}		
							
							if (url2.size()>0){
								HashMap<String, String> url3 = new HashMap<String, String>();
								List<HashMap<String, String>> urlList = new ArrayList<HashMap<String, String>>();
								try {
									urlList = (List<HashMap<String, String>>) url2.get("url");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									url3 = (HashMap<String, String>) url2.get("url");
								}
								
								for (HashMap<String,String> url4 : urlList){
									if (url4.containsKey("driver") && StringUtils.isNotBlank(url4.get("driver"))){
										ServerStateRecordDisk disk = new ServerStateRecordDisk();
											String drive = url4.get("driver");
											String total = url4.get("total");
											String used = url4.get("used");
											String left = url4.get("left");
											disk.setDriver(drive);
											disk.setTotal(total);
											disk.setUsed(used);
											disk.setLeft(left);
											dlist.add(disk);
									}else if (url4.containsKey("cpuPercent")  && StringUtils.isNotBlank(url4.get("cpuPercent"))){
										ServerStateRecordCpu cpu = new ServerStateRecordCpu();
											String cpuPercent = url4.get("cpuPercent");
											String cpuTemp = url4.get("cpuTemp");										
											cpu.setCpuPercent(cpuPercent);
											cpu.setCpuTemp(cpuTemp);
											clist.add(cpu);
									}else if (url4.containsKey("port")  && StringUtils.isNotBlank(url4.get("port"))){
										ServerStateRecordNetcard netcard = new ServerStateRecordNetcard();
											String port = url4.get("port");
											String portState = url4.get("portState");
											String bandwidth = url4.get("bandwidth");
											String dataFlow = url4.get("dataFlow");
											String ip = url4.get("ip");
											String mac = url4.get("mac");										
											netcard.setPort(port);
											netcard.setPortState(portState);
											netcard.setBandwidth(bandwidth);
											netcard.setDataFlow(dataFlow);
											netcard.setIp(ip);
											netcard.setMac(mac);
											nlist.add(netcard);										
									}
								}
								
								if (url3.size()>0){
									if (url3.containsKey("driver") && StringUtils.isNotBlank(url3.get("driver"))){
										ServerStateRecordDisk disk = new ServerStateRecordDisk();
											String drive = url3.get("driver");
											String total = url3.get("total");
											String used = url3.get("used");
											String left = url3.get("left");
											disk.setDriver(drive);
											disk.setTotal(total);
											disk.setUsed(used);
											disk.setLeft(left);
											dlist.add(disk);
									}else if (url3.containsKey("cpuPercent")  && StringUtils.isNotBlank(url3.get("cpuPercent"))){
										ServerStateRecordCpu cpu = new ServerStateRecordCpu();
											String cpuPercent = url3.get("cpuPercent");
											String cpuTemp = url3.get("cpuTemp");										
											cpu.setCpuPercent(cpuPercent);
											cpu.setCpuTemp(cpuTemp);
											clist.add(cpu);
									}else if (url3.containsKey("port")  && StringUtils.isNotBlank(url3.get("port"))){
										ServerStateRecordNetcard netcard = new ServerStateRecordNetcard();
											String port = url3.get("port");
											String portState = url3.get("portState");
											String bandwidth = url3.get("bandwidth");
											String dataFlow = url3.get("dataFlow");
											String ip = url3.get("ip");
											String mac = url3.get("mac");										
											netcard.setPort(port);
											netcard.setPortState(portState);
											netcard.setBandwidth(bandwidth);
											netcard.setDataFlow(dataFlow);
											netcard.setIp(ip);
											netcard.setMac(mac);
											nlist.add(netcard);										
									}
								}
							}
							serverStateRecordDto.setServerStateRecordCpu(clist);
							serverStateRecordDto.setServerStateRecordDisk(dlist);
							serverStateRecordDto.setServerStateRecordNetcard(nlist);
							serverStateRecordDto.setServerStateRecord(serverStateRecord);
							serverStateRecordBpo.save(serverStateRecordDto);
						}
					}
				}
			}
		}
		
		return responseMap;
		
	}
	
	
}