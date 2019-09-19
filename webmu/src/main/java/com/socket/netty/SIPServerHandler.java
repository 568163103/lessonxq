package com.socket.netty;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.general.security.model.bpo.UserBpo;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.User;
import com.beyeon.nvss.device.model.bpo.ServerBpo;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;
import com.socket.sip.bean.SIPBean;
import com.socket.sip.bean.SIPRequestBean;
import com.socket.sip.bean.SIPRequestHead;
import com.socket.sip.bean.SIPResponseBean;
import com.socket.sip.bean.SIPResponseHead;
import com.socket.sip.bean.SIPSessionBean;
import com.socket.sip.bean.SIPType;
import com.socket.sip.process.MD5;
import com.socket.sip.process.query.QueryAlarmRes;
import com.socket.sip.process.query.QueryDevicesRes;
import com.socket.sip.process.query.QueryDiskStateRecord;
import com.socket.sip.process.query.QueryEncoderStateRecord;
import com.socket.sip.process.query.QueryServerStateRecord;
import com.socket.sip.process.query.QuerySwitchPortInfo;
import com.socket.sip.process.query.QuerySwitchStateRecord;
import com.socket.sip.process.report.ReportAlarmInfo;
import com.socket.sip.process.report.ReportAlarmRes;
import com.socket.sip.process.report.ReportDeviceRes;
import com.socket.sip.process.report.ReportSwitchPortInfo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

public class SIPServerHandler extends SimpleChannelInboundHandler<SIPBean> {  
	 private int lossConnectCount = 0;	
	 @Override
	    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
	        System.out.println("已经一个心跳周期未收到客户端的消息了！");
	        if (evt instanceof IdleStateEvent){
	            IdleStateEvent event = (IdleStateEvent)evt;
	            if (event.state()== IdleState.READER_IDLE){
	                lossConnectCount++;
	                if (lossConnectCount>2){
	                    System.out.println("关闭这个不活跃通道！");
	                    String clientid = NettyChannelMap.remove((SocketChannel)ctx.channel()); 
	                    ctx.channel().close();
	                    notify(clientid, "0");
	                }
	            }
	        }else {
	            super.userEventTriggered(ctx,evt);
	        }
	    }
    @Override  
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {  
        //channel失效，从Map中移除  
    	 String clientid = NettyChannelMap.remove((SocketChannel)ctx.channel()); 
    	 notify(clientid, "0");
    }  
    @Override  
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, SIPBean baseMsg) throws Exception {  
    	lossConnectCount = 0;
    	
        if(SIPType.REQUEST.equals(baseMsg.getType())){  
        	SIPRequestBean sip = (SIPRequestBean) baseMsg;
//        	System.out.println("===========reach======"+sip.toString());
        	SIPRequestHead requestHead = sip.getRequestHead();
			Map<String, Object> requestMap = sip.getMap();	
//			requestMap.put("master", channelHandlerContext.channel().remoteAddress().toString());
			Map<String, Object> validateMap = validateLogin(requestMap,(SocketChannel) channelHandlerContext.channel());
			String command = (String) requestMap.get("@command");
			String code = (String) validateMap.get("@code");
			String text = (String) validateMap.get("#text");					
			Map<String,Object> responseMap = new HashMap<String,Object>();
			if (code!=null  && "0".equals(code)){
				requestMap.put("keepAlivePeriod", validateMap.get("keepAlivePeriod"));
				requestMap.put("dmuCurrentTime", validateMap.get("dmuCurrentTime"));
				responseMap = processRequest(requestMap);       //业务处理，获取需返回的responseMap
			}else{
				responseMap.put("@command",command);
				SIPSocketUtil.addResult(responseMap, code, text);
			}
			if ("0".equals(code) && ("QueryDeviceRes".equals(command) || "QueryAlarmRes".equals(command) || "QuerySwitchPortInfo".equals(command))){
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				try {
					list = (List<HashMap<String, Object>>) responseMap.get("parameters");
				} catch (Exception e) {
					HashMap<String, Object> response = new HashMap<String, Object>();
					response.put("@command", command);
					SIPSocketUtil.addResult(response,"47","no result");   //无数据
				}
				if (list!=null && list.size()>0){
					List<HashMap<String, Object>> responseList = packet(list,command);
					for (int k = 0; k< responseList.size(); k++){
						SIPResponseBean response =SIPSocketUtil.createSIPResponse(requestHead, responseList.get(k));   //格式化sip request
						channelHandlerContext.channel().writeAndFlush(response);   //打印输出
//						System.out.println(response);
					}
				}
				
			}else{
				SIPResponseBean response =SIPSocketUtil.createSIPResponse(requestHead, responseMap);   //格式化sip request
				channelHandlerContext.channel().writeAndFlush(response);   //打印输出
//				System.out.println(response);
			}
			
			if (!"0".equals(code)){
				channelHandlerContext.channel().close();
				NettyChannelMap.remove((SocketChannel)channelHandlerContext.channel());  
			}
        } else if(SIPType.RESPONSE.equals(baseMsg.getType())){
        	SIPResponseBean sip = (SIPResponseBean) baseMsg;

//        	System.out.println("===========reach======"+sip.toString());
        	SIPResponseHead responseHead = sip.getResponseHead();
			Map<String, Object> responseMap = sip.getMap();
			String cseq =  SIPSocketUtil.replaceBlank(responseHead.getCseq());
			String command = (String) responseMap.get("@command");
			//验证是否是自己发的请求	    				
			if (SIPSocketUtil.requestMapList.size()>0){
				for(Entry<String,String> entry : SIPSocketUtil.requestMapList.entrySet() ){
					String key = entry.getKey();
					String values = entry.getValue();
					if (key.equals(cseq+command) && values.equals("")){
						HashMap<String,String> map = (HashMap<String, String>) responseMap.get("result");
						if (map!=null && "0".equals(map.get("@code"))){							
							processResponse(responseMap);
							SIPSocketUtil.requestMapList.remove(key);
						}else{
							SIPSocketUtil.requestMapList.put(key, map.get("@code"));
						}
						
					}
				}
			}
			
        }else{
        	String localAddress = channelHandlerContext.channel().localAddress().toString().replaceAll("\u002f", "");	
        	String remoteAddress = channelHandlerContext.channel().remoteAddress().toString().replaceAll("\u002f", "");	
        	SIPResponseBean response =SIPSocketUtil.createSIPResponse(localAddress,remoteAddress);   //无法识别的请求 默认回应
        	channelHandlerContext.channel().writeAndFlush(response);   //打印输出
			System.out.println(response);
        }
        ReferenceCountUtil.release(baseMsg);
    }
    
    /**
	 * userId断开已有连接
	 * @param dmuUserId
	 */
	private static void oldLoginOut(String dmuUserId){
		try{
			SocketChannel sc = NettyChannelMap.get(dmuUserId);
			if (sc!=null){				
				sc.close();
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 *  验证登录
	 * @param ip
	 * @param requestMap
	 * @return
	 */
	private Map<String,Object> validateLogin(Map<String, Object> requestMap,SocketChannel ctx){
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String command =(String) requestMap.get("@command");		
		HashMap<String,Object> map = (HashMap<String, Object>) requestMap.get("parameters");
		if ("DmuRegister".equals(command) ){
			String dmuUserId = (String) map.get("dmuUserId");
			String dmuPassword = (String) map.get("dmuPassword");
			String dmuIp = (String) map.get("dmuIp");
			String dmuPort = (String) map.get("dmuPort");
			String version = (String) map.get("version");
			String dmuId = (String) map.get("dmuId");
			if (dmuUserId!=null && dmuPassword!=null && dmuIp!=null && dmuPort!=null && version!=null
					&& dmuId!=null){
				SocketChannel sc = NettyChannelMap.get(dmuUserId);
				if (sc!=null && sc == ctx){
					responseMap.put("@code", "0");
					responseMap.put("keepAlivePeriod",ResourceUtil.getSocketKeepAlive());
					responseMap.put("dmuCurrentTime",DateUtil.getTime());  //登录时间
				}else {	
					ServerBpo serverBpo =(ServerBpo)SpringInit.getApplicationContext().getBean("serverBpo");
					Server server = serverBpo.getServerById(dmuUserId);
					if (server!=null && dmuPassword.equals(MD5.md5(server.getPassword()))){
						if (sc!=null){
							oldLoginOut(dmuUserId);
						}
						NettyChannelMap.add(dmuUserId, ctx);
						notify(dmuUserId, "1");
						responseMap.put("@code", "0");
						responseMap.put("keepAlivePeriod", ResourceUtil.getSocketKeepAlive());
						responseMap.put("dmuCurrentTime",DateUtil.getTime());  //登录时间
					}else{
						responseMap.put("@code", "2");
						responseMap.put("#text", "password error");  //密码错误
					}
				}
			}else{
				responseMap.put("@code", "37");
				responseMap.put("#text", "parameter error"); //参数错误
			}
			
		}else if ("DmuLogin".equals(command) ){
			String dmuUserId = (String) map.get("userName");
			String dmuPassword = (String) map.get("password");
			String dmuIp = (String) map.get("ip");
			String dmuPort = (String) map.get("port");
			if (dmuUserId!=null && dmuPassword!=null && dmuIp!=null && dmuPort!=null ){
				SocketChannel sc = NettyChannelMap.get(dmuUserId);
				if (sc!=null && sc == ctx){
					responseMap.put("@code", "0");
					responseMap.put("keepAlivePeriod",ResourceUtil.getSocketKeepAlive());
					responseMap.put("dmuCurrentTime",DateUtil.getTime());  //登录时间
				}else {
					UserBpo serverBpo =(UserBpo)SpringInit.getApplicationContext().getBean("userBpo");
					User server = serverBpo.getUserByUsername(dmuUserId);
					serverBpo.getUserByUsername(dmuUserId);
					String passwo = server.getPasswd();
					passwo = MD5.md5(passwo);
					if (server!=null && dmuPassword.equals(MD5.md5(server.getPasswd()))){	
						if (sc!=null){
							oldLoginOut(dmuUserId);
						}
						NettyChannelMap.add(dmuUserId, ctx);
						NettyChannelMap.setUserid(dmuUserId);
						responseMap.put("@code", "0");
						responseMap.put("keepAlivePeriod", ResourceUtil.getSocketKeepAlive());
						responseMap.put("dmuCurrentTime",DateUtil.getTime());  //登录时间
					}else{
						responseMap.put("@code", "2");
						responseMap.put("#text", "password error");  //密码错误
					}
				}
			}else{
				responseMap.put("@code", "37");
				responseMap.put("#text", "parameter error"); //参数错误
			}
			
		}else if ("DmuKeepAlive".equals(command)  || "SaKeepAlive".equals(command)){
			String keepAlivePeriod  =  (String) map.get("keepAlivePeriod");
			if(keepAlivePeriod != null && NettyChannelMap.hasValue(ctx)){
				responseMap.put("@code", "0");
				responseMap.put("keepAlivePeriod", ResourceUtil.getSocketKeepAlive());
			}else if (keepAlivePeriod==null){
				responseMap.put("@code", "37");
				responseMap.put("#text", "parameter error"); //参数错误
			}else{
				responseMap.put("@code", "8");
				responseMap.put("#text", "loginOut error");   //已登出
			}
		}else{
			if (NettyChannelMap.hasValue(ctx)){
				responseMap.put("@code", "0");
			}else{
				responseMap.put("@code", "8");
				responseMap.put("#text", "loginOut error");   //已登出
			}			
		}
		return responseMap;
	}
	/**
	 * 业务处理
	 * @param requestMap  request请求参数
	 * @return responseMap
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Object> processRequest (Map<String, Object> requestMap){
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String command =(String) requestMap.get("@command");
		responseMap.put("@command", command);
		
		HashMap <String,String> parameters = new HashMap<String,String>();
		if ("DmuRegister".equals(command) || "DmuLogin".equals(command)){
			SIPSocketUtil.addResult(responseMap,"0","success");				
			parameters.put("keepAlivePeriod", String.valueOf(requestMap.get("keepAlivePeriod")));   //心跳时间
			parameters.put("dmuCurrentTime",String.valueOf(requestMap.get("dmuCurrentTime")));  //登录时间
			responseMap.put("parameters", parameters);
		}else if ("DmuKeepAlive".equals(command) || "SaKeepAlive".equals(command)){
			SIPSocketUtil.addResult(responseMap,"0","success");
			HashMap<String, String> params =(HashMap<String,String>) requestMap.get("parameters");
			String keepAlivePeriod= (String) params.get("keepAlivePeriod");
			parameters.put("keepAlivePeriod", keepAlivePeriod);
			responseMap.put("parameters", parameters);
		}else if ("QueryDeviceRes".equals(command)){
			QueryDevicesRes s = new QueryDevicesRes();
			List<HashMap<String, Object>> list = s.query(requestMap);
			responseMap.put("parameters", list);
		}else if ("QueryAlarmRes".equals(command)){
			QueryAlarmRes s = new QueryAlarmRes();
			List<HashMap<String, Object>> list = s.query(requestMap);
			responseMap.put("parameters", list);
		}else if ("QueryCoderState".equals(command) || "QueryIPCamState".equals(command)){
			requestMap.put("flag", "0");  //标记是被查询
			QueryEncoderStateRecord s = new QueryEncoderStateRecord();
			responseMap = s.query(requestMap);
		}else if ("QueryServerState".equals(command)){
			requestMap.put("flag", "0");  //标记是被查询
			QueryServerStateRecord s = new QueryServerStateRecord();
			responseMap = s.query(requestMap);
		}else if ("QuerySwitchState".equals(command)){
			requestMap.put("flag", "0");  //标记是被查询
			QuerySwitchStateRecord s = new QuerySwitchStateRecord();
			responseMap = s.query(requestMap);
		}else if ("QueryDiskState".equals(command)){
			requestMap.put("flag", "0");  //标记是被查询
			QueryDiskStateRecord s = new QueryDiskStateRecord();
			responseMap = s.query(requestMap);
		}else if ("QuerySwitchPortInfo".equals(command)){
			QuerySwitchPortInfo s = new QuerySwitchPortInfo();
			List<HashMap<String, Object>> list = s.query(requestMap);
			responseMap.put("parameters", list);
		}else if ("ReportDeviceRes".equals(command)){
			ReportDeviceRes s = new ReportDeviceRes();
			responseMap =s.report(requestMap);
		}else if ("ReportAlarmRes".equals(command)){
			ReportAlarmRes s = new ReportAlarmRes();
			responseMap = s.report(requestMap);
		}else if ("ReportAlarmInfo".equals(command)){
			ReportAlarmInfo s = new ReportAlarmInfo();
			responseMap = s.report(requestMap);
		}else if ("ReportSwitchPortInfo".equals(command)){
			ReportSwitchPortInfo s = new ReportSwitchPortInfo();  //交换机端口上报未做
			responseMap = s.report(requestMap);
		}else{
			SIPSocketUtil.addResult(responseMap,"28","Unrecognized command operation type");
		}
		return responseMap;
	}
	/**处理response
	 * 
	 * @param requestMap
	 */
	private void processResponse(Map<String, Object> responseMap){
		String command =(String) responseMap.get("@command");
		if ("QueryCoderState".equals(command) || "QueryIPCamState".equals(command)){
			responseMap.put("flag", "1");  //标记是主动查询
			QueryEncoderStateRecord s = new QueryEncoderStateRecord();
			responseMap = s.query(responseMap);
		}else if ("QueryServerState".equals(command)){
			responseMap.put("flag", "1");  //标记是主动查询
			QueryServerStateRecord s = new QueryServerStateRecord();
			responseMap = s.query(responseMap);
		}else if ("QuerySwitchState".equals(command)){
			responseMap.put("flag", "1");  //标记是主动查询
			QuerySwitchStateRecord s = new QuerySwitchStateRecord();
			responseMap = s.query(responseMap);
		}else if ("QueryDiskState".equals(command)){
			responseMap.put("flag", "1");  //标记是主动查询
			QueryDiskStateRecord s = new QueryDiskStateRecord();
			responseMap = s.query(responseMap);
		}
	}
	private List<HashMap<String, Object>> packet(List<HashMap<String, Object>> list,String command){
		List<HashMap<String, Object>> responseList = new ArrayList<HashMap<String, Object>>();
		int num = ResourceUtil.getPacketNum();
		int total = list.size();
		if (total>num){
			int totalPacketNum = total / num ;
			if (total % num >0) 
				totalPacketNum +=1;
			for (int i = 0 ; i < totalPacketNum;i++){
				HashMap<String, Object> response = new HashMap<String, Object>();
				List<HashMap<String, Object>> urlList = new ArrayList<HashMap<String, Object>>();
				HashMap<String,Object> param = new HashMap<String,Object> ();				
				for (int j = num * i ; j < num * (i+1)  && j< total ; j ++){					
					HashMap<String, Object> recordMap = list.get(j);
					urlList.add(recordMap);
				}
				HashMap<String,Object> url2 = new HashMap<String,Object> ();
				url2.put("URL", urlList);		
				param.put("group", url2);
				param.put("totalPacketNum", totalPacketNum);
				param.put("curPacketNum", i+1);					
				param.put("dmuId","");
				param.put("userId","");
				param.put("userName","");
				param.put("userLevel","");
				SIPSocketUtil.addResult(response,"0","success");		
				response.put("@command", command);
				response.put("parameters", param);
				responseList.add(response);
				
			}
		}else{	
				if (list !=null && list.size()>0){
					HashMap<String, Object> response = new HashMap<String, Object>();
					HashMap<String,Object> url2 = new HashMap<String,Object> ();
					HashMap<String,Object> param = new HashMap<String,Object> ();
					url2.put("URL", list);					
					param.put("group", url2);
					param.put("totalPacketNum", 1);
					param.put("curPacketNum", 1);				
					param.put("dmuId","");
					param.put("userId","");
					param.put("userName","");
					param.put("userLevel","");
					SIPSocketUtil.addResult(response,"0","success");		
					response.put("@command", command);
					response.put("parameters", param);
					responseList.add(response);
				}else{
					HashMap<String, Object> response = new HashMap<String, Object>();
					response.put("@command", command);
					SIPSocketUtil.addResult(response,"47","no result");   //无数据
				}
				
		}
		return responseList;
	}
	/**
	 * 登录/离线通知更改设备状态
	 * @param id  设备ID
	 * @param status 状态 0 离线 1在线
	 */
	public void notify(String id,String status){
		ServerBpo serverBpo =(ServerBpo)SpringInit.getApplicationContext().getBean("serverBpo");
		serverBpo.updateStatus(id, status);
	}
	
	
}
