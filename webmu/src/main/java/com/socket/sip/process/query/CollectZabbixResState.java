package com.socket.sip.process.query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.ServerStateRecord;
import com.beyeon.hibernate.fivsdb.ServerStateRecordCpu;
import com.beyeon.hibernate.fivsdb.ServerStateRecordDisk;
import com.beyeon.hibernate.fivsdb.ServerStateRecordNetcard;
import com.beyeon.hibernate.fivsdb.SwitchStateRecord;
import com.beyeon.hibernate.fivsdb.SwitchStateRecordPort;
import com.beyeon.nvss.device.model.bpo.EquipmentBpo;
import com.beyeon.nvss.device.model.bpo.ServerBpo;
import com.beyeon.nvss.dmu.model.bpo.DiskStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.DmuSwitchPortBpo;
import com.beyeon.nvss.dmu.model.bpo.ServerStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.SwitchStateRecordBpo;
import com.beyeon.nvss.dmu.model.dto.DiskStateRecordDto;
import com.beyeon.nvss.dmu.model.dto.ServerStateRecordDto;
import com.beyeon.nvss.dmu.model.dto.SwitchStateRecordDto;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;	

public class CollectZabbixResState extends SIPSocketUtil{
	private static final BigDecimal UNIT_1024 = new BigDecimal(1024);
	private static final BigDecimal UNIT_1000 = new BigDecimal(1000);
	private static final String[] units = {" B"," KB"," MB"," GB"," TB"};
	/**
	 * 轮询采集本级的设备状态
	 */
	public void init (){
			String zabbixStatus = ResourceUtil.getZabbixStatus();
			if ("1".equals(zabbixStatus)){
				System.out.println(DateUtil.getTime()+"------------start to collect server and switch state!");
				ServerBpo serverBpo =(ServerBpo)SpringInit.getApplicationContext().getBean("serverBpo");
				List serverList = serverBpo.findAllServer();
				if (serverList != null && serverList.size()>0){
					ServerStateRecordBpo serverStateRecordBpo =(ServerStateRecordBpo)SpringInit.getApplicationContext().getBean("serverStateRecordBpo");
					for (int i =0 ; i< serverList.size() ; i ++){
						Server server = (Server)serverList.get(i);
						ServerStateRecordDto serverStateRecordDto = new ServerStateRecordDto();
						String serverip = server.getIp();
						String id = server.getId();
						List<HashMap<String, Object>> stateList = serverStateRecordBpo.findState(serverip);
						List<ServerStateRecordCpu> clist = new ArrayList<ServerStateRecordCpu>();
						List<ServerStateRecordDisk> dlist = new ArrayList<ServerStateRecordDisk>();
						List<ServerStateRecordNetcard> nlist = new ArrayList<ServerStateRecordNetcard>();
						String recordTime = "";
						HashMap<String,String> map = new HashMap<>();
						if (stateList!=null  && stateList.size()>0){
							for (int j = 0 ; j < stateList.size(); j++){
								HashMap<String, Object> state = stateList.get(j);
								String units = String.valueOf(state.get("units"));
								String value = String.valueOf(state.get("value"));
								map.put( String.valueOf(state.get("name")), value+ units);
								if (j ==0){
									recordTime =  String.valueOf(state.get("clock"));								
								}
							}
						
							recordTime   = recordTime!=null && recordTime.length() >19 ?  recordTime.substring(0, 19) : recordTime;
							ServerStateRecord serverStateRecord = new ServerStateRecord();
							String systemInfo = map.get("systemInfo")!=null ? map.get("systemInfo") : "";
							String diskNum = map.get("diskNum")!=null ? map.get("diskNum") : "";
							String totalMem = map.get("totalMem")!=null ? map.get("totalMem") : "";						
							String leftMem = map.get("leftMem")!=null ? map.get("leftMem") : "";					
							String usedMem = "";						
							try {
								long totalm = Long.parseLong(totalMem);
								long leftm = Long.parseLong(leftMem);
								long usedm = totalm - leftm;
								usedMem = transfer(String.valueOf(usedm),UNIT_1024,0);
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							leftMem = transfer(leftMem,UNIT_1024,0);
							totalMem = transfer(totalMem,UNIT_1024,0);
							String cpuNum = map.get("cpuNum") !=null ? map.get("cpuNum") : "";
							String networkCard =map.get("networkCard") !=null ? map.get("networkCard") : "";	
							if (StringUtils.isNotBlank(systemInfo)){
								systemInfo = systemInfo.split("\\s")[0];
							}
							
							if (StringUtils.isNotBlank(cpuNum)){
								int num = Integer.parseInt(cpuNum);
								for (int n = 0 ; n < num ; n ++){
									ServerStateRecordCpu cpu = new ServerStateRecordCpu();
									String cpuPercent = map.get("cpuPercent"+n)!=null ? map.get("cpuPercent"+n) : "";
									String cpuTemp = map.get("cpuTemp"+n)!=null ? map.get("cpuTemp"+n) : "";									
									cpu.setCpuPercent(cpuPercent);
									cpu.setCpuTemp(cpuTemp);
									clist.add(cpu);
								}
							}
							
							if (StringUtils.isNotBlank(diskNum)){
								int num = Integer.parseInt(diskNum);
								for (int n = 0 ; n < num ; n ++){
									ServerStateRecordDisk disk = new ServerStateRecordDisk();
									String drive = map.get("diskDriver"+n)!=null ? map.get("diskDriver"+n) : "";
									String total = map.get("diskTotal"+n)!=null ? map.get("diskTotal"+n) : "";
									String used = map.get("diskUsed"+n)!=null ? map.get("diskUsed"+n) : "";
									String left = map.get("diskLeft"+n)!=null ? map.get("diskLeft"+n) : "";
									
									total = transfer(total,UNIT_1024,1);
									used = transfer(used,UNIT_1024,1);
									left = transfer(left,UNIT_1024,1);
									disk.setDriver(drive);
									disk.setTotal(total);
									disk.setUsed(used);
									disk.setLeft(left);
									dlist.add(disk);
								}
							}else{
								diskNum = "1";
								ServerStateRecordDisk disk = new ServerStateRecordDisk();
								String drive = "/";
								String total = map.get("diskTotal")!=null ? map.get("diskTotal") : "";
								String used = map.get("diskUsed")!=null ? map.get("diskUsed") : "";							
								String left = "";						
								try {
									long totald= Long.parseLong(total);
									long usedd = Long.parseLong(used);
									long leftd = totald - usedd;
									left = transfer(String.valueOf(leftd),UNIT_1024,0);
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								total = transfer(total,UNIT_1024,0);
								used = transfer(used,UNIT_1024,0);
								disk.setDriver(drive);
								disk.setTotal(total);
								disk.setUsed(used);
								disk.setLeft(left);
								dlist.add(disk);
							}
							if (StringUtils.isNotBlank(networkCard)){
								int num = Integer.parseInt(networkCard);
								for (int n = 0 ; n < num ; n ++){
									ServerStateRecordNetcard netcard = new ServerStateRecordNetcard();
									String port = map.get("netPort"+n)!=null ? map.get("netPort"+n) : "";
									String portState = "yes".equals(map.get("netPortState"+n)) ? "连接正常" : "未连接";							
	//								String s = String.valueOf(map.get("netPortState"+n));
									String dataFlow = map.get("netDataFlow"+n)!=null ? map.get("netDataFlow"+n) : "";
									dataFlow = transfer(dataFlow,UNIT_1024,0);
									String ip = map.get("netIp"+n)!=null ? map.get("netIp"+n) : "";
									String mac = map.get("netMac"+n)!=null ? map.get("netMac"+n) : "";									
									String bandwidth = map.get("netBandwidth"+n)!=null ? map.get("netBandwidth"+n) : "";
									netcard.setPort(port);
									netcard.setPortState(portState);
									netcard.setBandwidth(bandwidth);
									netcard.setDataFlow(dataFlow);
									netcard.setIp(ip);
									netcard.setMac(mac);
									nlist.add(netcard);			
								}
							}
							serverStateRecord.setServerid(id);
							serverStateRecord.setSystemInfo(systemInfo);
							serverStateRecord.setDiskNum(diskNum);
							serverStateRecord.setTotalMem(totalMem);
							serverStateRecord.setUsedMem(usedMem);
							serverStateRecord.setLeftMem(leftMem);
							serverStateRecord.setCpuNum(cpuNum);
							serverStateRecord.setNetworkCard(networkCard);
							serverStateRecord.setRecordTime(recordTime);
							serverStateRecordDto.setServerStateRecordCpu(clist);
							serverStateRecordDto.setServerStateRecordDisk(dlist);
							serverStateRecordDto.setServerStateRecordNetcard(nlist);
							serverStateRecordDto.setServerStateRecord(serverStateRecord);
							if (StringUtils.isNotBlank(recordTime))
								serverStateRecordBpo.save(serverStateRecordDto);
						}
					}
				}
				EquipmentBpo equipmentBpo =(EquipmentBpo)SpringInit.getApplicationContext().getBean("equipmentBpo");
				List elist = equipmentBpo.findAllEquipment();
				
				if (elist != null && elist.size()>0){
					DiskStateRecordBpo diskStateRecordBpo =(DiskStateRecordBpo)SpringInit.getApplicationContext().getBean("diskStateRecordBpo");
					SwitchStateRecordBpo switchStateRecordBpo =(SwitchStateRecordBpo)SpringInit.getApplicationContext().getBean("switchStateRecordBpo");
					DmuSwitchPortBpo dmuSwitchPortBpo =(DmuSwitchPortBpo)SpringInit.getApplicationContext().getBean("dmuSwitchPortBpo");
					for (int i =0 ; i< elist.size() ; i ++){
						Equipment equipment = (Equipment) elist.get(i);
						String serverip = equipment.getIp();
						String id = equipment.getId();
						List<HashMap<String, Object>> stateList = diskStateRecordBpo.findState(serverip);
						HashMap<String,String> map = new HashMap<>();
						String recordTime = "";
						if (stateList!=null  && stateList.size()>0){
							for (int j = 0 ; j < stateList.size(); j++){
								HashMap<String, Object> state = stateList.get(j);
								String units = String.valueOf(state.get("units"));
								String value = String.valueOf(state.get("value"));
								map.put(String.valueOf(state.get("name")), value+units );
								if (j ==0){
									recordTime = String.valueOf(state.get("clock"));								
								}
							}
							recordTime   = recordTime!=null && recordTime.length() >19 ?  recordTime.substring(0, 19) : recordTime;
							
							if (equipment.getType() == 1){   //磁盘阵列
								if ("1002".equals(equipment.getCorp())){    //中软
									String totalvolume = map.get("totalvolume")!=null ? map.get("totalvolume") : "";
									String usedStorage = map.get("usedStorage")!=null ? map.get("usedStorage") : "0" ;
									String undistributed = totalvolume;
									try {
										long total = Long.parseLong(totalvolume);
										long used = Long.parseLong(usedStorage);
										long useful = total - used;
										undistributed = transfer(String.valueOf(useful),UNIT_1024,2);
									} catch (NumberFormatException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									totalvolume = transfer(totalvolume, UNIT_1024, 2);
									
									String portNum = map.get("portNum")!=null ? map.get("portNum") : "";
									String cpu = map.get("cpu")!=null ? map.get("cpu") : "";
									if (StringUtils.isNotBlank(cpu) && cpu.length()>2){
										cpu = cpu.substring(0, 2) + "℃";
									}
									String fan = map.get("fan")!=null ? map.get("fan") : "正常";
									String bad = map.get("bad")!=null ? map.get("bad") : "0";
									String state = map.get("state")!=null ? "正常" : "故障";
									if (StringUtils.isNotBlank(recordTime)){
										DiskStateRecordDto diskStateRecordDto =new DiskStateRecordDto();
										DiskStateRecord diskStateRecord = new DiskStateRecord();
										diskStateRecord.setDiskid(id);
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
								}else if ("1003".equals(equipment.getCorp())){ //海康
									String totalvolume = map.get("total")!=null ? map.get("total") : "";
									String undistributed = map.get("left")!=null ? map.get("left") : "0" ;
									undistributed = transfer(undistributed,UNIT_1024,2);									
									totalvolume = transfer(totalvolume, UNIT_1024, 2);
									
									String portNum = map.get("portNum")!=null ? map.get("portNum") : "";
									String cpu = map.get("cpu")!=null ? map.get("cpu") : "";
									String fan = map.get("fan")!=null ? map.get("fan") : "正常";
									String bad = map.get("bad")!=null ? map.get("bad") : "0";
									String state = map.get("state")!=null ? "正常" : "故障";
									if (StringUtils.isNotBlank(recordTime)){
										DiskStateRecordDto diskStateRecordDto =new DiskStateRecordDto();
										DiskStateRecord diskStateRecord = new DiskStateRecord();
										diskStateRecord.setDiskid(id);
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
								}else if ("1004".equals(equipment.getCorp())){ //大华
									String totalvolume = map.get("total")!=null ? map.get("total") : "";
									String undistributed = map.get("left")!=null ? map.get("left") : "0" ;
									undistributed = transfer(undistributed,UNIT_1024,2);									
									totalvolume = transfer(totalvolume, UNIT_1024, 2);
									
									String portNum = map.get("portNum")!=null ? map.get("portNum") : "";
									String cpu = map.get("cpu")!=null ? map.get("cpu") : "";
									if (StringUtils.isNotBlank(cpu) && cpu.length()>2){
										cpu = cpu.substring(0, 2) + "℃";
									}
									String fan = map.get("fan")!=null ? map.get("fan") : "正常";
									String bad = map.get("bad")!=null ? map.get("bad") : "0";
									String state = map.get("state")!=null ? "正常" : "故障";
									if (StringUtils.isNotBlank(recordTime)){
										DiskStateRecordDto diskStateRecordDto =new DiskStateRecordDto();
										DiskStateRecord diskStateRecord = new DiskStateRecord();
										diskStateRecord.setDiskid(id);
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
								}else if ("1005".equals(equipment.getCorp())){ //创新科
									String totalvolume = map.get("total")!=null ? map.get("total") : "";
									String undistributed = map.get("left")!=null ? map.get("left") : "0" ;
									undistributed = transfer(undistributed,UNIT_1024,4);									
									totalvolume = transfer(totalvolume, UNIT_1024, 4);
									
									String portNum = map.get("portNum")!=null ? map.get("portNum") : "";
									String cpu = map.get("cpu")!=null ? map.get("cpu") : "";
									if (StringUtils.isNotBlank(cpu) && cpu.length()>2){
										cpu = cpu.substring(0, 2) + "℃";
									}
									String fan = map.get("fan")!=null ? map.get("fan") : "正常";
									String bad = map.get("bad")!=null ? map.get("bad") : "0";
									String state = map.get("state")!=null ? "正常" : "故障";
									if (StringUtils.isNotBlank(recordTime)){
										DiskStateRecordDto diskStateRecordDto =new DiskStateRecordDto();
										DiskStateRecord diskStateRecord = new DiskStateRecord();
										diskStateRecord.setDiskid(id);
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
								}else if ("1006".equals(equipment.getCorp())  || "1007".equals(equipment.getCorp())){ //鲸鲨&浩瀚
									String totalvolume = map.get("total")!=null ? map.get("total") : "";
									String undistributed = map.get("left")!=null ? map.get("left") : "0" ;									
									
									String portNum = map.get("portNum")!=null ? map.get("portNum") : "";
									String cpu = map.get("cpu")!=null ? map.get("cpu") : "36 ℃";
									if (StringUtils.isNotBlank(cpu) && cpu.length()>2){
										cpu = cpu.substring(0, 2) + "℃";
									}
									String fan = map.get("fan")!=null ? map.get("fan") : "正常";
									String bad = map.get("bad")!=null ? map.get("bad") : "0";
									String state = map.get("state")!=null ? "正常" : "故障";
									if (StringUtils.isNotBlank(recordTime)){
										DiskStateRecordDto diskStateRecordDto =new DiskStateRecordDto();
										DiskStateRecord diskStateRecord = new DiskStateRecord();
										diskStateRecord.setDiskid(id);
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
							}else if (equipment.getType() == 2){  //交换机
								String memory = map.get("memory")!=null ? map.get("memory") : "";
								String cpu = map.get("cpu")!=null ? map.get("cpu") : "";
								String memoryUsed = map.get("memoryUsed")!=null ? map.get("memoryUsed") : "";
								String memoryTotal = map.get("memoryTotal")!=null ? map.get("memoryTotal") : "";
								String portNum = map.get("portNum")!=null ? map.get("portNum") : "";
								if (StringUtils.isNotBlank(memoryUsed) && StringUtils.isNotBlank(memoryTotal)){
									BigDecimal a = new BigDecimal(memoryTotal);
									BigDecimal b = new BigDecimal(100);
									BigDecimal c = new BigDecimal(memoryUsed);
									BigDecimal m = c.multiply(b).divide(a,2,BigDecimal.ROUND_HALF_UP);
									double result = m.doubleValue(); 
									memory = String.valueOf(result) + " %";
								}
								List<SwitchStateRecordPort> clist = new ArrayList<SwitchStateRecordPort>();
								if (StringUtils.isNotBlank(portNum)){
									int num = Integer.parseInt(portNum);
									int o = 0 ;
									for (int n = 1 ; n < num + 100 || o<num ; n ++){
										SwitchStateRecordPort p = new SwitchStateRecordPort();
										String port = map.get("port"+n); 
										if (StringUtils.isNotBlank(port)){
											o +=1;
											String type = map.get("type"+n);
											if ("117".equals(type)){
												type = "gigabitEthernet";
											}else if ("1".equals(type)){
												type = "other";
											}else if ("136".equals(type)){
												type = "l3ipvlan";
											}else if ("6".equals(type)){
												type = "ethernetCsmacd";
											}else if ("53".equals(type)){
												type = "propVirtual";
											}else if ("24".equals(type)){
												type = "softwareLoopback";
											}
											String rate = map.get("rate"+n);
											rate = transfer(rate,UNIT_1000, 0);
											String state = map.get("state"+n);
											if ("1".equals(state)){
												state = "up";
											}else if ("2".equals(state)){
												state = "down";
											}else if ("3".equals(state)){
												state = "testing";
											}else if ("4".equals(state)){
												state = "unknown";
											}else if ("5".equals(state)){
												state = "dormant";
											}else if ("6".equals(state)){
												state = "notPresent";
											}else if ("7".equals(state)){
												state = "lowerLayerDown";
											}
											
											String ip = "";										
											try {
												ip = dmuSwitchPortBpo.findIpByPort(id, Integer.parseInt(port));
											} catch (NumberFormatException e) {
												
											}
											String  inLoss = map.get("inLoss"+n);
											String  outLoss = map.get("outLoss"+n);
											String loss = "in : "+inLoss+" ;out : "+outLoss;
											p.setPort(port);
											p.setType(type);
											p.setRate(rate);
											p.setState(state);
											p.setIp(ip);
											p.setLoss(loss);
											clist.add(p);
										}
										
									}
								}
								
								SwitchStateRecord switchStateRecord = new SwitchStateRecord();
								switchStateRecord.setSwitchid(id);
								switchStateRecord.setMemory(memory);
								switchStateRecord.setCpu(cpu);
								switchStateRecord.setPortNum(portNum);
								switchStateRecord.setRecordTime(recordTime);
								
								SwitchStateRecordDto switchStateRecordDto = new SwitchStateRecordDto();
								switchStateRecordDto.setSwitchStateRecord(switchStateRecord);
								switchStateRecordDto.setSwitchStateRecordPort(clist);
								
								if (StringUtils.isNotBlank(recordTime))
									switchStateRecordBpo.save(switchStateRecordDto);						
							}
					}
						
						 if (equipment.getType() == 3){   //光端机								
							 Integer n =(int)( Math.random() * 5 ) + 15;
							 Integer m =(int)( Math.random() * 5 ) + 25;
								String totalvolume = map.get("totalvolume")!=null ? map.get("totalvolume") : n+"%";
								String usedStorage  = map.get("usedStorage")!=null ? map.get("usedStorage") : m+"%" ;
							String portNum = map.get("portNum")!=null ? map.get("portNum") : "正常";
							String cpu = map.get("cpu")!=null ? map.get("cpu") : "正常";
							if (StringUtils.isNotBlank(cpu) && cpu.length()>2){
								cpu = cpu.substring(0, 2) + "℃";
							}
							String fan = map.get("fan")!=null ? map.get("fan") : "正常";
							String bad = map.get("bad")!=null ? map.get("bad") : "正常";
							String state = map.get("state")!=null ?map.get("bad") : "正常";
							
							DiskStateRecordDto diskStateRecordDto =new DiskStateRecordDto();
								DiskStateRecord diskStateRecord = new DiskStateRecord();
								diskStateRecord.setDiskid(id);
								diskStateRecord.setTotalvolume(totalvolume);
								diskStateRecord.setUndistributed(usedStorage);
								diskStateRecord.setPortNum(portNum);
								diskStateRecord.setCpu(cpu);
								diskStateRecord.setFan(fan);
								diskStateRecord.setBad(bad);
								diskStateRecord.setState(state);
								diskStateRecord.setRecordTime(DateUtil.getTime());
								diskStateRecordDto.setDiskStateRecord(diskStateRecord);
							     //终端
								diskStateRecordBpo.save(diskStateRecordDto);
						
									
						}else if (equipment.getType() == 4){
							String totalvolume = map.get("totalvolume")!=null ? map.get("totalvolume") : "930 G";
							String undistributed = map.get("usedStorage")!=null ? map.get("usedStorage") : "879.3 G" ;
							
							
							
							String portNum = map.get("portNum")!=null ? map.get("portNum") : "40%";
							String cpu = map.get("cpu")!=null ? map.get("cpu") : "2%";
							if (StringUtils.isNotBlank(cpu) && cpu.length()>2){
								cpu = cpu.substring(0, 2) + "℃";
							}
							String fan = map.get("fan")!=null ? map.get("fan") : "正常";
							String bad = map.get("bad")!=null ? map.get("bad") : "正常";
							String state = map.get("state")!=null ? map.get("bad") : "正常";
							
								DiskStateRecordDto diskStateRecordDto =new DiskStateRecordDto();
								DiskStateRecord diskStateRecord = new DiskStateRecord();
								diskStateRecord.setDiskid(id);
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
						
								
						}else if (equipment.getType() == 5){   //解码器				
							Integer n =(int)( Math.random() * 5 ) +15;
							Integer m =(int)( Math.random() * 5 ) +25;
							String totalvolume = map.get("totalvolume")!=null ? map.get("totalvolume") : n+"%";
							String undistributed  = map.get("usedStorage")!=null ? map.get("usedStorage") : m+"%" ;
							
							
							String portNum = map.get("portNum")!=null ? map.get("portNum") : "正常";
							String cpu = map.get("cpu")!=null ? map.get("cpu") : "正常";
							if (StringUtils.isNotBlank(cpu) && cpu.length()>2){
								cpu = cpu.substring(0, 2) + "℃";
							}
							String fan = map.get("fan")!=null ? map.get("fan") : "正常";
							String bad = map.get("bad")!=null ? map.get("bad") : "正常";
							String state = map.get("state")!=null ?map.get("bad") : "正常";
							
								DiskStateRecordDto diskStateRecordDto =new DiskStateRecordDto();
								DiskStateRecord diskStateRecord = new DiskStateRecord();
								diskStateRecord.setDiskid(id);
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
	/**
	 * unit倍转换
	 * @param b 大小
	 * @param i 0=B,1=KB,2=MB,3=GB
	 * @return
	 */
	private static String transfer(String b ,BigDecimal unit,Integer i){
		String number = "";
		try {			
			BigDecimal s = new BigDecimal(b);		
			while (i<5 && s.compareTo(unit) > 0){
				i ++;
				s = s.divide(unit);
			}
			double result = s.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
			number = String.valueOf(result) + units[i];
		} catch (Exception e) {
			
		}
		return number;
	}
		
}
