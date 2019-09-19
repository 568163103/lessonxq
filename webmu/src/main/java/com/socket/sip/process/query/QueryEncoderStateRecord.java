package com.socket.sip.process.query;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.util.JaxbUtils;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.EncoderStateRecord;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.nvss.dmu.model.bpo.DmuEquipmentBpo;
import com.beyeon.nvss.dmu.model.bpo.EncoderStateRecordBpo;
import com.beyeon.nvss.dmu.model.dto.EncoderStateRecordDto;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;
import com.socket.sip.XmlUtil;

public class QueryEncoderStateRecord extends SIPSocketUtil{
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
						
						HashMap<String,Object> recordMap = new HashMap<String,Object>();
						EncoderStateRecordBpo encoderStateRecordBpo =(EncoderStateRecordBpo)SpringInit.getApplicationContext().getBean("encoderStateRecordBpo");
						
						if ("0".equals(result.get("code"))){
							EncoderStateRecord record = encoderStateRecordBpo.findEncoderStateRecord(id);
							if (record!=null ){
								recordMap.put("resId", record.getEncoderid() !=null ? record.getEncoderid() : "");
								recordMap.put("coderType", record.getCodeType()!=null ? record.getCodeType() : "");
								recordMap.put("streamType", record.getStreamType()!=null ? record.getStreamType() : "");
								recordMap.put("mainStreamRateType", record.getMainStreamRateType()!=null ? record.getMainStreamRateType() : "");
								recordMap.put("mainStreamResolution", record.getMainStreamResolution()!=null ? record.getMainStreamResolution() : "");
								recordMap.put("mainStreamFrameRate", record.getMainStreamFrameRate()!=null ? record.getMainStreamFrameRate() : "");
								recordMap.put("mainStreamGOP", record.getMainStreamGOP()!=null ? record.getMainStreamGOP() : "");
								recordMap.put("childStreamResolution", record.getChildStreamResolution()!=null ? record.getChildStreamResolution() : "");
								
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
							String codeType = (String) rUrl.get("coderType");
							String streamType = (String) rUrl.get("streamType");
							String mainStreamRateType = (String) rUrl.get("mainStreamRateType");
							String mainStreamResolution = (String) rUrl.get("mainStreamResolution");
							String mainStreamFrameRate = (String) rUrl.get("mainStreamFrameRate");
							String mainStreamGOP = (String) rUrl.get("mainStreamGOP");
							String childStreamResolution = (String) rUrl.get("childStreamResolution");
							if (res_id !=null && !"".equals(res_id)){
								EncoderStateRecordBpo encoderStateRecordBpo =(EncoderStateRecordBpo)SpringInit.getApplicationContext().getBean("encoderStateRecordBpo");
								EncoderStateRecordDto encoderStateRecordDto =new EncoderStateRecordDto();
								EncoderStateRecord encoderStateRecord =new EncoderStateRecord();
								encoderStateRecord.setEncoderid(res_id);
								encoderStateRecord.setCodeType(codeType);
								encoderStateRecord.setStreamType(streamType);
								encoderStateRecord.setMainStreamRateType(mainStreamRateType);
								encoderStateRecord.setMainStreamFrameRate(mainStreamFrameRate);
								encoderStateRecord.setMainStreamGOP(mainStreamGOP);
								encoderStateRecord.setMainStreamResolution(mainStreamResolution);
								encoderStateRecord.setChildStreamResolution(childStreamResolution);
								encoderStateRecord.setRecordTime(DateUtil.getTime());
								if ("QueryIPCamState".equals(command)){
									encoderStateRecord.setType("0");
								}else{
									encoderStateRecord.setType("1");
								}
								encoderStateRecordDto.setEncoderStateRecord(encoderStateRecord);
								encoderStateRecordBpo.save(encoderStateRecordDto);
							}
							
						}
					}
				}
			
			
		}
		
		return responseMap;
		
	}
	
	public static void main (String[] args){
		Map<String,Object> request = new HashMap<String,Object>();
		request.put("@command", "QueryIPCamState");
		HashMap<String,Object> parameters =new HashMap<String,Object>();
		HashMap<String,Object> group =new HashMap<String,Object>();
		HashMap<String,Object> url =new HashMap<String,Object>();
		url.put("resId", "2200001201000201");
		group.put("URL", url);
		parameters.put("group", group);
		parameters.put("dmuId", "25020012");		
		request.put("parameters", parameters);
		
		StringBuilder cmuUrl = new StringBuilder();
		cmuUrl.append("http://192.168.0.121:9123/TBMessageReq");
		String xml = "";
		try {
			xml = XmlUtil.formatXml(XmlUtil.map2xml(request, "request"));
			xml = xml.replace("?>", "standalone=\"yes\" ?>");
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = HttpClientUtil.post(cmuUrl.toString()+"?said="+url.get("resId"),xml);
		System.out.println("TBMessageReq================"+result);
		
	}
}
