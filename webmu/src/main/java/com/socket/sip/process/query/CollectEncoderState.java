package com.socket.sip.process.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.hibernate.fivsdb.Encoder;
import com.beyeon.hibernate.fivsdb.EncoderStateRecord;
import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.ServerStateRecord;
import com.beyeon.hibernate.fivsdb.ServerStateRecordCpu;
import com.beyeon.hibernate.fivsdb.ServerStateRecordDisk;
import com.beyeon.hibernate.fivsdb.ServerStateRecordNetcard;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.hibernate.fivsdb.SwitchStateRecord;
import com.beyeon.hibernate.fivsdb.SwitchStateRecordPort;
import com.beyeon.nvss.device.model.bpo.EncoderBpo;
import com.beyeon.nvss.device.model.bpo.EquipmentBpo;
import com.beyeon.nvss.device.model.bpo.ServerBpo;
import com.beyeon.nvss.dmu.model.bpo.DiskStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.EncoderStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.ServerStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.SwitchStateRecordBpo;
import com.beyeon.nvss.dmu.model.dto.DiskStateRecordDto;
import com.beyeon.nvss.dmu.model.dto.EncoderStateRecordDto;
import com.beyeon.nvss.dmu.model.dto.ServerStateRecordDto;
import com.beyeon.nvss.dmu.model.dto.SwitchStateRecordDto;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;
import com.socket.sip.XmlUtil;	

public class CollectEncoderState extends SIPSocketUtil{
	/**
	 * 轮询采集本级的设备状态
	 */
	public void init (){
			System.out.println(DateUtil.getTime()+"-----------------start to collecte encoder state");
			EncoderBpo encoderBpo = (EncoderBpo)SpringInit.getApplicationContext().getBean("encoderBpo");
			String status = encoderBpo.findServerStatus(ServerType.CMU);
			if ("true".equals(status)){
				List encoderList = encoderBpo.findAllEncoder();
				if (encoderList!=null  && encoderList.size()>0){
					EncoderStateRecordBpo encoderStateRecordBpo =(EncoderStateRecordBpo)SpringInit.getApplicationContext().getBean("encoderStateRecordBpo");
					
					for(int i = 0 ; i < encoderList.size(); i++){
						Encoder encoder =(Encoder) encoderList.get(i);
						String id = encoder.getId();
						
						HashMap<String, Object> requestMap = new HashMap<String,Object>();
						HashMap<String,Object> parameters = new HashMap<String,Object>();
						HashMap<String,Object> rgroup =  new HashMap<String,Object>();
						HashMap<String,Object> rUrl = new HashMap<String,Object>();
						String command = "";
						Integer num = encoder.getChannelCount();
						if (num == 1){
							command = "QueryIPCamState";
						}else{
							command = "QueryCoderState";
						}
						rUrl.put("resId", id);
						rgroup.put("URL",rUrl );
						parameters.put("group", rgroup);
						parameters.put("dmuId", String.valueOf((long)(Math.random() * 1000000000)));
						requestMap.put("@command",command);
						requestMap.put("parameters", parameters);
						
						
						
						StringBuilder cmuUrl = new StringBuilder();
						cmuUrl.append("http://").append(encoderStateRecordBpo.findServerIp(ServerType.CMU)).append(":")
								.append(ResourceUtil.getServerConf("cmu.server.port")).append("/TBMessageReq");
						String xml = "";
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							xml = XmlUtil.formatXml(XmlUtil.map2xml(requestMap, "request"));
						
							String result = HttpClientUtil.post(cmuUrl.toString()+"?said="+id,xml);
//							System.out.println("TBMessageReq================"+result);
							if (StringUtils.isNotBlank(result)){
								map = XmlUtil.xml2mapWithAttr(result, false);
							}
						} catch (DocumentException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if (map!=null &&map.size()>0){
							HashMap<String ,Object> parameter = (HashMap<String, Object>) map.get("parameters");
							if (parameter !=null && parameter.size()>0){
								HashMap<String ,Object> group = (HashMap<String, Object>) parameter.get("group");
								if (group !=null && group.size()>0){
									HashMap<String ,Object> url = (HashMap<String, Object>) group.get("URL");
									if (url !=null && url.size()>0){
										String res_id = (String) url.get("resId");
										String codeType = (String) url.get("coderType");
										String streamType = (String) url.get("streamType");
										String mainStreamRateType = (String) url.get("mainStreamRateType");
										String mainStreamResolution = (String) url.get("mainStreamResolution");
										String mainStreamFrameRate = (String) url.get("mainStreamFrameRate");
										String mainStreamGOP = (String) url.get("mainStreamGOP");
										String childStreamResolution = (String) url.get("childStreamResolution");
										if (res_id !=null && !"".equals(res_id)){
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
						
					}
					
					
				}
			}
			
			

		
	}
}
