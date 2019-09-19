package com.beyeon.nvss.performance.control.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.general.security.model.SecurityFacade;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.ServerStatus;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.nvss.bussiness.model.dto.ServerStatusDto;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.performance.model.dto.ReqResUserDto;
import com.beyeon.nvss.performance.model.dto.ReqServerResDto;
import com.beyeon.nvss.performance.model.dto.ReqServerResDto.ReqServerRes;

/**
 * User: Administrator
 * Date: 2015/12/30
 * Time: 14:26
 */
@Component("devicePerformanceAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DevicePerformanceAction extends BaseAction {
	private static Map<String,Deque<ServerStatus>> serverStatusMap = new HashMap<String,Deque<ServerStatus>>();
	private DeviceFacade deviceFacade;
	private SecurityFacade securityFacade;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public void setSecurityFacade(SecurityFacade securityFacade) {
		this.securityFacade = securityFacade;
	}

	private synchronized void addServerStatus(ServerStatus serverStatus){
		Deque<ServerStatus> serverStatuses = serverStatusMap.get(serverStatus.getServerId());
		if (null == serverStatuses){
			serverStatuses = new LinkedList<ServerStatus>();
			serverStatusMap.put(serverStatus.getServerId(),serverStatuses);
		}
		if (serverStatuses.size() > ServerStatusDto.XSIZE -1){
			serverStatuses.remove();
		}
		serverStatuses.add(serverStatus);
	}
	
	public String findPage(){
		deviceFacade.getServerBpo().find(getPageObject());
		return "findPage";
	}

	private Object getServerStatus(String serverId){
		StringBuilder cmuUrl = new StringBuilder();
		cmuUrl.append("http://").append(deviceFacade.getServerBpo().findServerIp(ServerType.CMU)).append(":")
				.append(ResourceUtil.getServerConf("cmu.server.port")).append("/ReqServerStatus");
		String result = HttpClientUtil.post(cmuUrl.toString(), "{\"serverId\":\""+serverId+"\"}");
		logger.debug(result);
		ServerStatus serverStatus = JSON.parseObject(result, ServerStatus.class);
		if (serverStatus.getResult()==0) {
			addServerStatus(serverStatus);
		}
		List<ServerStatusDto> serverStatusDtoes = new ArrayList<ServerStatusDto>();
		serverStatusDtoes.add(new ServerStatusDto("cpu","CPU使用情况",0,100,"%",new String[]{"当前进程使用","主机整体使用"}));
		serverStatusDtoes.add(new ServerStatusDto("memory","内存使用情况",0,16000,"M",new String[]{"当前进程使用","所属主机可用"}));
		serverStatusDtoes.add(new ServerStatusDto("net","网卡流量使用情况",0,12000,"kbps",new String[]{"网卡上行流量","网卡下行流量"}));
		serverStatusDtoes.add(new ServerStatusDto("user","在线用户或服务情况",0,100,"个",new String[]{"在线用户数目","在线服务数目"}));
		String[] diskHeads = new String[0];	Integer maxDisk = 1000;
		if (null != serverStatus.getDiskItemList()){
			diskHeads = new String[serverStatus.getDiskItemList().size()];
			for (int i = 0;i < serverStatus.getDiskItemList().size();i++){
				diskHeads[i] = (String) ((Map)serverStatus.getDiskItemList().get(i)).get("name");
				Integer currDisk = Integer.parseInt((String) ((Map)serverStatus.getDiskItemList().get(i)).get("free"));
				if (currDisk > maxDisk){
					maxDisk = currDisk;
				}
			}
		}
		serverStatusDtoes.add(new ServerStatusDto("disk","硬盘使用情况",0,maxDisk*2,"M",diskHeads));
		Deque<ServerStatus> serverStatuses = serverStatusMap.get(serverId);
		for (int i = 0; null != serverStatuses && i < serverStatuses.size(); i++) {
			serverStatus = ((List<ServerStatus>)serverStatuses).get(i);
			
			serverStatusDtoes.get(0).getChildDataes().get(0).set(ServerStatusDto.XSIZE - serverStatuses.size() + i, Float.valueOf(serverStatus.getCpu()));
			serverStatusDtoes.get(0).getChildDataes().get(1).set(ServerStatusDto.XSIZE -serverStatuses.size()+i,Float.valueOf(serverStatus.getCpuTotal()));
			serverStatusDtoes.get(1).setYmax(Integer.valueOf(((List<ServerStatus>) serverStatuses).get(i).getMemoryTotal()));
			serverStatusDtoes.get(1).getChildDataes().get(0).set(ServerStatusDto.XSIZE -serverStatuses.size()+i,Integer.valueOf(serverStatus.getMemory()));
			serverStatusDtoes.get(1).getChildDataes().get(1).set(ServerStatusDto.XSIZE -serverStatuses.size()+i,Integer.valueOf(serverStatus.getMemoryFree()));
			
			String value = serverStatus.getNetworkIn().isEmpty() ? "0" : serverStatus.getNetworkIn();
			serverStatusDtoes.get(2).getChildDataes().get(0).set(ServerStatusDto.XSIZE -serverStatuses.size()+i,Integer.valueOf(value));
			
			value = serverStatus.getNetworkOut().isEmpty() ? "0" : serverStatus.getNetworkOut();
			serverStatusDtoes.get(2).getChildDataes().get(1).set(ServerStatusDto.XSIZE -serverStatuses.size()+i,Integer.valueOf(value));
			
			serverStatusDtoes.get(3).getChildDataes().get(0).set(ServerStatusDto.XSIZE -serverStatuses.size()+i,Integer.valueOf(serverStatus.getOnlineClient()));
			serverStatusDtoes.get(3).getChildDataes().get(1).set(ServerStatusDto.XSIZE -serverStatuses.size()+i,Integer.valueOf(serverStatus.getOnlineServer()));
			for (int j = 0; j < diskHeads.length; j++) {
				serverStatusDtoes.get(4).getChildDataes().get(j).set(ServerStatusDto.XSIZE -serverStatuses.size()+i,Integer.valueOf((String)((Map)serverStatus.getDiskItemList().get(j)).get("free")));
			}
		}
		return serverStatusDtoes;
	}
	
	public String viewServerStatus(){
		String serverId = this.getReqParam("serverId");
		this.setReqAttr("serverName", Server.getObjectName(serverId));
		this.setReqAttr("result",toJSON(getServerStatus(serverId)));
		return "viewServerStatus";
	}

	public String getServerStatus(){
		String serverId = this.getReqParam("serverId");
		responseJSON(getServerStatus(serverId));
		return null;
	}

	public String operateServer(){
		String serverId = this.getReqParam("serverId");
		StringBuilder cmuUrl = new StringBuilder();
		cmuUrl.append("http://").append(deviceFacade.getServerBpo().findServerIp(ServerType.CMU)).append(":")
				.append(ResourceUtil.getServerConf("cmu.server.port")).append("/RebootServer");
		String result = HttpClientUtil.post(cmuUrl.toString(), "{\"serverId\":\""+serverId+"\"}");
		logger.debug(result);
		return findPage();
	}

	public String getReqServerRes(){
		String serverId = this.getReqParam("serverId");
		StringBuilder cmuUrl = new StringBuilder();
		cmuUrl.append("http://").append(deviceFacade.getServerBpo().findServerIp(ServerType.CMU)).append(":")
				.append(ResourceUtil.getServerConf("cmu.server.port")).append("/ReqServerRes");
		String result = HttpClientUtil.post(cmuUrl.toString(), "{\"serverId\":\""+serverId+"\"}");
		logger.debug(result);
		ReqServerResDto reqServerResDto = JSON.parseObject(result,ReqServerResDto.class);
		
		// 数据处理
		int normalCount = 0;
		int cfgTime = ResourceUtil.getCoreConfInt("app.res.time");
		Date currentDate = new Date();
		
		List<ReqServerRes> ls = reqServerResDto.getItemList();
		if(ls!=null){
			for (int idx = 0; idx < ls.size(); ++idx) {
				String endDate = ls.get(idx).getEndTime();
				if (StringUtils.isNotBlank(endDate)) {
					Date d = DateUtil.parse(endDate, DateUtil.yyyyMMddHHmmssSpt);
					long min = (currentDate.getTime() - d.getTime()) / (1000 * 60);
					if (min <= cfgTime) {
						++normalCount;
						ls.get(idx).setValid(true);
					}
				} else if ("".equals(endDate)) {
					++normalCount;
					ls.get(idx).setValid(true);
				}
			}
			this.setReqAttr("totalCount", ls.size());
			this.setReqAttr("invalidCount", ls.size() - normalCount);
		}
		this.setReqAttr("normalCount", normalCount);
		this.setReqAttr("reqServerResDto",reqServerResDto);
		return "reqServerRes";
	}

	public String getReqResUser(){
		String serverId = this.getReqParam("serverId");
		String resId = this.getReqParam("resId");
		StringBuilder cmuUrl = new StringBuilder();
		cmuUrl.append("http://").append(deviceFacade.getServerBpo().findServerIp(ServerType.CMU)).append(":")
				.append(ResourceUtil.getServerConf("cmu.server.port")).append("/ReqServerUser");
		String result = HttpClientUtil.post(cmuUrl.toString(), "{\"serverId\":\""+serverId+"\",\"resId\":\""+resId+"\"}");
		logger.debug(result);
		ReqResUserDto reqResUserDto = JSON.parseObject(result,ReqResUserDto.class);
		if (null != reqResUserDto.getItemList()){
			List<String> uids = new ArrayList<String>();
			List<Map> users = new ArrayList<Map>();
			for (ReqResUserDto.ReqResUser reqResUser : reqResUserDto.getItemList()) {
				if (!reqResUser.getUserId().contains("everybody")) {
					uids.add(reqResUser.getUserId());
				} else {
					Map user = new HashMap();
					user.put("id",reqResUser.getUserId());
					user.put("name","everybody");
					users.add(user);
				}
			}
			if (uids.size() > 0) {
				users.addAll(securityFacade.getUserBpo().findUserNameAndId(uids));
			}
			this.setReqAttr("users",users);
		}
		return "reqResUser";
	}
}
