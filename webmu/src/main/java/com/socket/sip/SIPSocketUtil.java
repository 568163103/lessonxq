package com.socket.sip;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.TDict;
import com.socket.netty.NettyChannelMap;
import com.socket.sip.bean.SIPRequestBean;
import com.socket.sip.bean.SIPRequestHead;
import com.socket.sip.bean.SIPResponseBean;
import com.socket.sip.bean.SIPResponseHead;
import com.socket.sip.bean.SIPSessionBean;

import io.netty.channel.socket.SocketChannel;

public class SIPSocketUtil {
	public static List<Map<String, Object>> responseMapList = Collections.synchronizedList(new ArrayList<Map<String,Object>>());
	public static  HashMap<String,String>requestMapList = new HashMap<String,String>(1000);
    
    	
	
    /**
     * 往responseMap中添加处理信息
     * code = 0 成功  code = 1 失败
     * @param responseMap
     * @param code  0成功    1失败
     * @param text 描述
     * @return
     */
	public static Map<String,Object> addResult(Map<String,Object> responseMap,String code,String text){
    	HashMap<String,String> map = new HashMap<String,String>();
    	map.put("@code", code);
    	map.put("#text", text);
    	responseMap.put("result", map);
    	return responseMap;
    	
    }
	
    /**
     * 将收到的response缓存
     * @param cseq   
     * @param xmlMap
     */
	public static void addResponse(String cseq,Map<String, Object> xmlMap){
    	xmlMap.put("cseq",cseq);
    	responseMapList.add(xmlMap);
    }
    /**String localAddress = sc.getLocalAddress().toString().replaceAll("\u002f", "");
	  String remoteAddress = sc.getRemoteAddress().toString().replaceAll("\u002f", "");	
     * 不能识别的请求的默认回应
     * @param sc
     * @return
     */
	public static SIPResponseBean createSIPResponse (String localAddress,String remoteAddress){    	
		Map<String,Object> responseMap = new HashMap<String,Object>();
		responseMap.put("@command", "error");
		addResult(responseMap,"30","invalid SIP error");				
		String via = localAddress+"\n";
		String to = remoteAddress+"\n";
		String from = localAddress+"\n";
		String call_id = ResourceUtil.getAppId()+"@"+localAddress+"\n";
		String cseq = (int) (Math.random() * 10000) + "INVITE\n";
		String content_type = "RVSS/xml\n";
		String content_length ="0";
		SIPResponseHead responseHead = new SIPResponseHead();
		responseHead.setVia(via);
		responseHead.setTo(to);
		responseHead.setFrom(from);
		responseHead.setCall_id(call_id);
		responseHead.setCseq(cseq);
		responseHead.setContent_type(content_type);
		responseHead.setContent_length(content_length);
		SIPResponseBean response = new SIPResponseBean(responseHead, responseMap);
		return response;	
    }
   /**
    * 根据request请求头和准备好的responseMap格式化sip请求
    * @param requestHead   request请求头
    * @param responseMap	response返回参数
    * @return
    */
	public static SIPResponseBean createSIPResponse(SIPRequestHead requestHead,Map<String,Object> responseMap){
    	String via = requestHead.getTo();
    	String to = requestHead.getFrom();
    	String from = requestHead.getTo();
    	String call_id = requestHead.getCall_id();
    	String cseq = requestHead.getCseq();
    	String content_type = requestHead.getContent_type();
    	String content_length = "0";    	
    	SIPResponseHead responseHead = new SIPResponseHead();
    	responseHead.setVia(via);
    	responseHead.setTo(to);
    	responseHead.setFrom(from);
    	responseHead.setCall_id(call_id);
    	responseHead.setCseq(cseq);
    	responseHead.setContent_type(content_type);
    	responseHead.setContent_length(content_length);
    	SIPResponseBean response = new SIPResponseBean(responseHead, responseMap);
    	return response;
    }
    /**String localAddress = sc.getLocalAddress().toString().replaceAll("\u002f", "");	
	   String remoteAddress = sc.getRemoteSocketAddress().toString().replaceAll("\u002f", "");
     * 初始化request请求 
     * @param sc  通道
     * @param requestMap  请求参数
     * @return
     */
	
	public static SIPRequestBean createSIPRequest (String localAddress,String remoteAddress,Map<String,Object> requestMap){
    	String sip = " sip:@"+remoteAddress;
		String via = remoteAddress+"\r\n";				
		String to = " <sip@"+remoteAddress+">\r\n";
		String from = " <sip@"+localAddress+":"+ResourceUtil.getSocketPort()+">\r\n";
		String max_forwards = " 70\r\n";
		String call_id = " "+ResourceUtil.getAppId()+"@"+localAddress+"\r\n";
		String cseq = " "+(int) (Math.random() * 100000000) + " INVITE\r\n";
		String content_type = " RVSS/xml\r\n";
		String content_length ="0";
		SIPRequestHead head = new SIPRequestHead();
		head.setSip(sip);
		head.setVia(via);
		head.setTo(to);
		head.setFrom(from);
		head.setMax_forwards(max_forwards);
		head.setCall_id(call_id);
		head.setCseq(cseq);
		head.setContent_type(content_type);
		head.setContent_length(content_length);
		SIPRequestBean request = new SIPRequestBean(head, requestMap);
		return request;
    }
	
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	
	public static HashMap<String,String> queryState(String id,String type,String master){
		if (StringUtils.isBlank(master)){
			HashMap<String,String> result  = new HashMap<String,String>();
			result.put("code", "0");
			return result;
		}
		HashMap<String,String> result  = new HashMap<String,String>();	
		SocketChannel socket = NettyChannelMap.get(master);
		if (socket!=null){
			 String localAddress = socket.localAddress().toString().replaceAll("\u002f", "");	
			 String remoteAddress = socket.remoteAddress().toString().replaceAll("\u002f", "");
			 Map<String,Object> requestMap = new HashMap<String,Object>();
			 HashMap<String,Object> parameters = new HashMap<String,Object>();
			 HashMap<String,Object> group = new HashMap<String,Object>();
			 HashMap<String,Object> Url = new HashMap<String,Object>();
			 Url.put("resId", id);
			 group.put("URL", Url);
			 parameters.put("group", group);
			 parameters.put("dmuId", String.valueOf((long)(Math.random() * 1000000000)));
			 requestMap.put("parameters", parameters);
			 String command = "";
			 if ("服务器".equals(type)){
				 command= "QueryServerState";
			 }else if ("交换机".equals(type)){
				 command = "QuerySwitchState";
			 }else if ("磁盘阵列".equals(type)){
				 command = "QueryDiskState";
			 }else if ("编码器".equals(type)){
				 command = "QueryCoderState";
			 }else if ("IP摄像机".equals(type)){
				 command = "QueryIPCamState";
			 }
			 if (!"".equals(command)){
				 requestMap.put("@command", command);
				 SIPRequestBean sip = SIPSocketUtil.createSIPRequest(localAddress,remoteAddress, requestMap);
				 String cseq = SIPSocketUtil.replaceBlank(sip.getRequestHead().getCseq());
				 String cskey = cseq+ command;
				 SIPSocketUtil.requestMapList.put(cskey,"");
				 long i = System.currentTimeMillis();
				
				 socket.writeAndFlush(sip.toString());	
				 System.out.println(sip.toString());
				 String str = "0";
				 while (SIPSocketUtil.requestMapList.size()>0){
					String value = SIPSocketUtil.requestMapList.get(cskey);
					if (value!=null){
						if (!"".equals(value)){
							 str = value;
							 SIPSocketUtil.requestMapList.remove(cskey);
							 break;
												 
					 }
					}else{							 
						 break;
					 }
					 long j = System.currentTimeMillis();
					 if (j - i > 60 * 1000){
						 SIPSocketUtil.requestMapList.remove(cskey);
						 str = "48";
						 break;
					 }
					
						 
				 }
				 
				 result.put("code",str);  //超时
//			 }else if ("voop".equalsIgnoreCase(type)){
//				 command = "ReportAlarmInfo";
//				 Map<String,Object> requestMap1 = new HashMap<String,Object>();
//				 requestMap1.put("@command", command);
//				 HashMap<String,Object> parameters1 = new HashMap<String,Object>();
//				 HashMap<String,Object> group1 = new HashMap<String,Object>();
//				 HashMap<String,Object> Url1 = new HashMap<String,Object>();
//				 Url1.put("id", "330001204200000203002");
//				 Url1.put("type", type);
//				 Url1.put("startTime", "2018-04-26 18:00:00");
//				 Url1.put("endTime", "");
//				 Url1.put("targetInfo", "voop");
//				 Url1.put("level", 3);
//				 Url1.put("state", 0);
//				 Url1.put("description", "yiban");
//				 Url1.put("state", 0);
//				 group1.put("URL", Url1);
//				 parameters1.put("group", group1);
//				 parameters1.put("dmuId", Math.random() * 1000000000);
//				 requestMap1.put("parameters", parameters1);
//				 SIPRequestBean sip = SIPSocketUtil.createSIPRequest(localAddress,remoteAddress, requestMap1);
//				 String cseq = SIPSocketUtil.replaceBlank(sip.getRequestHead().getCseq());
//				 String cskey = cseq+ command;
//				 SIPSocketUtil.requestMapList.put(cskey,"");
//				 long i = System.currentTimeMillis();
//				
//
//				 socket.writeAndFlush(sip.toString());		
//				 System.out.println(sip.toString());
//				 String str = "0";
//				 while (SIPSocketUtil.requestMapList.size()>0){
//					String value = SIPSocketUtil.requestMapList.get(cskey);
//					if (value!=null){
//						if (!"".equals(value)){
//							 str = value;
//							 SIPSocketUtil.requestMapList.remove(cskey);
//							 break;
//												 
//					 }
//					}else{							 
//						 break;
//					 }
//					 long j = System.currentTimeMillis();
//					 if (j - i > 60 * 1000){
//						 SIPSocketUtil.requestMapList.remove(cskey);
//						 str = "1";
//						 break;
//					 }
//					
//						 
//				 }
//				 
//				 result.put("code",str);  //超时
			 }else{
				 result.put("code", "-1");  //该类型不可查询实时状态
			 }
				
		 }else{
			 result.put("code", "49"); //对方不在链接状态，无法获取实时状态
		 }
		 
		return result;
		
	}
	
//	public static String checkNull(String str){
//		String s = str != null ? str : "";
//		return s;
//	}
}
