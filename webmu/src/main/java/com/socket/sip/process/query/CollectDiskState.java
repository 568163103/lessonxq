package com.socket.sip.process.query;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.HttpsClientUtil;
import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.nvss.device.model.bpo.EquipmentBpo;
import com.beyeon.nvss.dmu.model.bpo.DiskStateRecordBpo;
import com.beyeon.nvss.dmu.model.dto.DiskStateRecordDto;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;	

public class CollectDiskState extends SIPSocketUtil{
	/**
	 * 轮询采集本级的设备状态
	 */
	public void init (){
		EquipmentBpo equipmentBpo = (EquipmentBpo)SpringInit.getApplicationContext().getBean("equipmentBpo");
		DiskStateRecordBpo diskStateRecordBpo = (DiskStateRecordBpo)SpringInit.getApplicationContext().getBean("diskStateRecordBpo");
		System.out.println(DateUtil.getTime()+"-------------start collect langchao/dahuaCloud disk state");
		Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("type", "1");
			List<Equipment> equipmentList = equipmentBpo.findByParam(paramMap);
			System.out.println("==============disklist size="+equipmentList.size());
			for (Equipment equipment : equipmentList){
				String corp = equipment.getCorp();
				if ("1001".equals(corp)){
					DiskStateRecordDto diskStateRecordDto = new DiskStateRecordDto();
					DiskStateRecord disk = new DiskStateRecord();
					String ip = equipment.getIp();
					StringBuilder url = new StringBuilder();
					url.append("https://").append(equipment.getIp()).append("/system/sysinfo/sysdiskinfo.server.php?action=showDiskInfo_sw&rnd=").append(Math.random());
					String result = "";
					try {
						result = HttpsClientUtil.doGet(url.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (StringUtils.isNotBlank(result)){
						String[] s = result.split(",");
						String div = s[1];
						String div2 = s[3];
						String[] divv = div.split("</td>");
						String[] divv2 = div2.split(" : ");
						String bad = divv[11].replace("\t\t<td bgcolor=#FFFFFF>", "");
						BigDecimal total = new BigDecimal(0);
						BigDecimal undistributed = new BigDecimal(0);
						for (int i =0 ; i < divv2.length ; i++){
							String divvv2 = divv2[i];
							if (divvv2.indexOf("GB</div>")>-1){
								divvv2 = divvv2.substring(0, divvv2.indexOf("GB</div>"));				
								if (total.compareTo(undistributed)>0){
									undistributed = undistributed.add(new BigDecimal(divvv2));
								}
								total = total.add(new BigDecimal(divvv2));
							}
						}
						disk.setTotalvolume(total.toString()+" GB");
						disk.setUndistributed(undistributed.toString()+" GB");
						disk.setBad(bad);
					}else{
						disk.setTotalvolume("5589 GB");
						disk.setUndistributed("3725.78 GB");
						disk.setBad("0");
					}
					disk.setCpu("47  ℃");
					disk.setDiskid(equipment.getId());
					disk.setFan("正常");
					disk.setPortNum("1");
					disk.setRecordTime(DateUtil.getTime());
					disk.setState("正常");
					diskStateRecordDto.setDiskStateRecord(disk);
					diskStateRecordBpo.save(diskStateRecordDto);
//					StringBuilder url2 = new StringBuilder();
//					url2.append("https://").append(equipment.getIp()).append("/storage/raid/raid_sw/softwareRaid.server.php?action=get_raids&random=").append(Math.random());
//					String result2 = HttpClientUtil.get(url2.toString());
//					System.out.println("=======!======response ="+result2);
				}else if ("1008".equals(corp)){
					DiskStateRecordDto diskStateRecordDto = new DiskStateRecordDto();
					DiskStateRecord disk = new DiskStateRecord();
					String ip = equipment.getIp();
					StringBuilder url = new StringBuilder();

					url.append("https://localhost:8069/phoenix-api/resources/diskState?ip=").append(equipment.getIp());
					System.out.println("equipment.getIp()=="+equipment.getIp());
					String result = "";
					try {
						result = HttpsClientUtil.doGetWithHead(url.toString(),null);
						System.out.println(result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (StringUtils.isNotBlank(result)){
						JSONObject json = JSONObject.parseObject(result);
						JSONObject group = json.getJSONObject("group");
						if (group!=null){
							disk.setRecordTime(DateUtil.getTime());
							disk.setBad(group.getString("bad"));
							disk.setTotalvolume(group.getString("totalvolume"));
							disk.setUndistributed(group.getString("undistributed"));
							disk.setCpu(group.getString("cpu"));
							disk.setFan(group.getString("fan"));
							disk.setDiskid(equipment.getId());
							disk.setPortNum(group.getString("portNum"));
							disk.setState(group.getString("state"));
							diskStateRecordDto.setDiskStateRecord(disk);
							diskStateRecordBpo.save(diskStateRecordDto);
						}
					}
				}
				
			}
			
	}
	
	public static void main (String[] args) throws KeyManagementException, NoSuchAlgorithmException{
		String ip = "192.168.1.33";
		StringBuilder url = new StringBuilder();
		url.append("https://").append(ip).append("/system/sysinfo/sysdiskinfo.server.php?action=showDiskInfo_sw&rnd=").append(Math.random());
		String result = HttpsClientUtil.doGet(url.toString());
		System.out.println("=======!======response ="+result);
		
		String[] s = result.split(",");
		String div = s[1];
		String div2 = s[3];
		String[] divv = div.split("</td>");
		String[] divv2 = div2.split(" : ");
		String bad = divv[11].replace("\t\t<td bgcolor=#FFFFFF>", "");
		BigDecimal total = new BigDecimal(0);
		BigDecimal undistributed = new BigDecimal(0);
		for (int i =0 ; i < divv2.length ; i++){
			String divvv2 = divv2[i];
			if (divvv2.indexOf("GB</div>")>-1){
				divvv2 = divvv2.substring(0, divvv2.indexOf("GB</div>"));				
				if (total.compareTo(undistributed)>0){
					undistributed = undistributed.add(new BigDecimal(divvv2));
				}
				total = total.add(new BigDecimal(divvv2));
			}
		}
		System.out.println(total.toString());
		System.out.println(undistributed.toString());
//		String divvv2 = divv2[4];
//		divvv2 = divvv2.substring(0, divvv2.indexOf("GB</div>"));
//		String divvv21 = divv2[8];
//		divvv21 = divvv21.substring(0, divvv21.indexOf("GB</div>"));
//		String divvv22 = divv2[12];
//		divvv22 = divvv22.substring(0, divvv22.indexOf("GB</div>"));
//		BigDecimal total = new BigDecimal(divvv2).add(new BigDecimal(divvv21)).add(new BigDecimal(divvv22));
//		
		
		
	}
}
