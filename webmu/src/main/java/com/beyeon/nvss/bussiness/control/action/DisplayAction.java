package com.beyeon.nvss.bussiness.control.action;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.hibernate.fivsdb.Channel;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.nvss.bussiness.model.BussinessFacade;
import com.beyeon.nvss.bussiness.model.dto.DisplayDto;
import com.beyeon.nvss.bussiness.model.dto.DisplayResultDto;
import com.beyeon.nvss.device.model.DeviceFacade;

@Component("displayAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DisplayAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(DisplayAction.class);
	private DeviceFacade deviceFacade;
	private BussinessFacade bussinessFacade;
	private DisplayDto displayDto;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public void setBussinessFacade(BussinessFacade bussinessFacade) {
		this.bussinessFacade = bussinessFacade;
	}

	public DisplayDto getDisplayDto() {
		return displayDto;
	}

	public void setDisplayDto(DisplayDto displayDto) {
		this.displayDto = displayDto;
	}

	private String getUserId(){
		String uid = null;
		if (null != this.getCurrentUser()){
			uid = this.getCurrentUser().getId();
		}
		if (StringUtils.isBlank(uid)){
			String userName = this.getReqParam("u");
			if (StringUtils.isNotBlank(userName)) {
				uid = bussinessFacade.getUserTreeBpo().findUid(userName);
			}
		}
		if (StringUtils.isBlank(uid)){
			uid = bussinessFacade.getUserTreeBpo().findUid("everybody");
		}
		return uid;
	}

	public String beforeLiveDisplay(){
		String channelCount = this.getReqParam("cc");
		String result = "liveDisplay";
		if (StringUtils.isNotBlank(channelCount)){
			result = result + channelCount;
		}
		String channelId = this.getReqParam("id");
		if (StringUtils.isBlank(channelId)){
			List<TreeNode> userResourceTree = bussinessFacade.getUserTreeBpo().findUserResourceTree(getUserId());
			this.setReqAttr("userResourceTree", toJSON(userResourceTree));
		} else if (StringUtils.isNotBlank(channelCount) && StringUtils.isNotBlank(channelId)) {
			List<TreeNode> userResourceTree = bussinessFacade.getUserTreeBpo().findUserResourceTree(getUserId());
			// 过虑树中不需要节点
			TreeNode tn = findTreeNode(userResourceTree.get(0), channelId);
			reduceTreeNode(userResourceTree.get(0), tn.getKey());
			this.setReqAttr("userResourceTree", toJSON(userResourceTree));
			this.setReqAttr("channelId", channelId);
			this.setReqAttr("channelName", Channel.getObjectName(channelId));
			result = "livePalyer";
		} else {
			this.setReqAttr("channelId", channelId);
			this.setReqAttr("channelName", Channel.getObjectName(channelId));
			result = "player";
		}
		this.setReqAttr("channelValue", "120");
		deviceFacade.findMduIpAndPort();
		return result;
	}

	public String operateChannelPtz(){
		String channelId = this.getReqParam("channelId");
		String action = this.getReqParam("action");
		String step = this.getReqParam("step");
		String result =HttpClientUtil.get("http://"+deviceFacade.findServerIp(ServerType.MDU)+":"+ResourceUtil.getServerConf("mdu.server.port")+"/webcontrol/ptzcontrol/" + channelId + "/" + action + "/" + step);
		this.responseHTML(result);
		return null;
	}

	public String beforeHistoryDisplay(){
		beforeLiveDisplay();
		String startTime = this.getReqParam("s"),startTimeOfDay = null;
		String endTime = this.getReqParam("e"),endTimeOfDay = null;
		if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)){
			Map<String,String> days = new LinkedHashMap<String, String>();
			for (int i = 0; i < 7; i++) {
				Date date = DateUtil.subDays(new Date(), i);
				String sd = DateUtil.format(date, DateUtil.yyyyMMddSpt);
				days.put(DateUtil.format(date, "MM月dd日 E"), DateUtil.parse(sd + " 00:00:00", DateUtil.yyyyMMddHHmmssSpt).getTime()/1000+"--"+DateUtil.parse(sd + " 23:59:59", DateUtil.yyyyMMddHHmmssSpt).getTime()/1000);
			}
			this.setReqAttr("days",days);
			String day = days.values().iterator().next();
			startTime = startTimeOfDay = day.split("--")[0];
			endTime = endTimeOfDay = day.split("--")[1];
		} else {
			Date day = DateUtil.parse(DateUtil.format(new Date(Long.valueOf(endTime) * 1000), DateUtil.yyyyMMddSpt), DateUtil.yyyyMMddSpt);
			startTimeOfDay = String.valueOf(day.getTime() / 1000);
			endTimeOfDay = String.valueOf(DateUtil.addDays(day,1).getTime()/1000-1);
		}
		this.setReqAttr("currQueryDate",startTime+"--"+endTime);
		this.setReqAttr("startTimeOfDay",startTimeOfDay);
		this.setReqAttr("endTimeOfDay",endTimeOfDay);
		String recordPath = this.getReqParam("recordPath");
		if (StringUtils.isBlank(recordPath)){
			recordPath = this.getReqParam("p");
		}
		if (StringUtils.isBlank(recordPath)){
			recordPath = "0";
		}
		this.setReqAttr("recordPath", recordPath);
		return "historyDisplay";
	}

	private String percent(long second){
		double dsecond = second*1.0;
		dsecond=dsecond/(24*60*60);
		DecimalFormat df1 = new DecimalFormat("0.0000%");
		return df1.format(dsecond);
	}

	public String queryHistoryDisplay(){
		DisplayResultDto result = new DisplayResultDto();
		if (null != displayDto) {
			logger.debug(toJSON(displayDto));
			String rs = HttpClientUtil.post("http://" + deviceFacade.findServerIp(ServerType.MDU) + ":" + ResourceUtil.getServerConf("mdu.server.port") + "/webcontrol/queryRecord/", toJSON(displayDto));
			logger.debug(rs);
			result = JSON.parseObject(rs, DisplayResultDto.class);
			if ("OK".equals(result.getErrorMsg())){
				if (null != result.getItemList()){
					Date day = displayDto.getBeginTime();
					long startTime = day.getTime();
					long endTime = DateUtil.addDays(day,1).getTime()-1;
					long currStartTime = startTime,currEndTime = 0;
					for (DisplayDto display : result.getItemList()) {
						long second = (display.getStartTime().getTime()-currStartTime)/1000;
						if (second > 0){
							Map<String,Object> videoInfo = new HashMap<String,Object>();
							videoInfo.put("hasVideo",false);
							videoInfo.put("percent",percent(second));
							videoInfo.put("startTime",(currStartTime-startTime)/1000);
							videoInfo.put("endTime",(display.getStartTime().getTime()-startTime)/1000);
							result.getVideoInfos().add(videoInfo);
							currStartTime = display.getStartTime().getTime();
						}
						currEndTime = display.getEndTime().getTime();
						second = (currEndTime-endTime);
						if (second > 0){
							currEndTime = endTime;
						}
						second = (currEndTime-currStartTime)/1000;
						if (second > 0){
							Map<String,Object> videoInfo = new HashMap<String,Object>();
							videoInfo.put("hasVideo",true);
							videoInfo.put("percent",percent(second));
							videoInfo.put("startTime",(currStartTime-startTime)/1000);
							videoInfo.put("endTime",(currEndTime-startTime)/1000);
							result.getVideoInfos().add(videoInfo);
							currStartTime = currEndTime;
						}
					}
					long second = (endTime-currStartTime)/1000;
					if (second > 0){
						Map<String,Object> videoInfo = new HashMap<String,Object>();
						videoInfo.put("hasVideo",false);
						videoInfo.put("percent",percent(second));
						videoInfo.put("startTime",(currStartTime-startTime)/1000);
						videoInfo.put("endTime",(endTime-startTime)/1000);
						result.getVideoInfos().add(videoInfo);
					}
				}
			}
			logger.debug(toJSON(result.getVideoInfos()));
		}
		this.responseJSON(result);
		return null;
	}

	public String playLive(){
		return beforeLiveDisplay();
	}

	public String playVod(){
		return beforeHistoryDisplay();
	}

	private List<Map> findImsGisInfo(){
		String longitude = this.getReqParam("longitude");
		String latitude = this.getReqParam("latitude");
		List<Map> imsGisInfos = null;
		if (StringUtils.isBlank(longitude) || StringUtils.isBlank(latitude)){
			String action = this.getReqParam("a");
			if (StringUtils.isNotBlank(action) && "edit".equals(action)) {
				this.getPageObject().setPageSize(1000000);
			} else {
				this.getPageObject().setPageSize(200);
			}
			imsGisInfos = this.deviceFacade.getChannelBpo().findGisInfos(this.getPageObject(), getUserId());
		} else {
			Map params = new HashMap();params.put("longitude",longitude);params.put("latitude",latitude);
			imsGisInfos = this.deviceFacade.getChannelBpo().findGisInfos(params, getUserId());
		}
		return imsGisInfos;
	}

	public String mapForPlay(){
		this.setReqAttr("gisInfos",toJSON(findImsGisInfo()));
		String action = this.getReqParam("a");
		if (StringUtils.isNotBlank(action) && "edit".equals(action)){
			List<TreeNode> userResourceTree = bussinessFacade.getUserTreeBpo().findUserResourceTree(getUserId());
			this.setReqAttr("userResourceTree", toJSON(userResourceTree));
			this.setReqAttr("channelValue", "120");
		}
		return "mapForPlay";
	}

	public String moveMapForPlay(){
		this.responseJSON(findImsGisInfo());
		return null;
	}

	public String updateChannelPosition(){
		String channelId = this.getReqParam("id");
		String lng = this.getReqParam("lng");
		String lat = this.getReqParam("lat");
		this.deviceFacade.getChannelBpo().updateChannelPosition(channelId, lng,lat);
		this.responseHTML("ok");
		return null;
	}
	
	public TreeNode findTreeNode(TreeNode tnParent, String resId) {
		if (tnParent == null) return null;
		if (tnParent.getKey().indexOf(resId) != -1)
			return tnParent;
		
		TreeNode tnRet = null;
		for (TreeNode tn : tnParent.getChildren()) {
			tnRet = findTreeNode(tn, resId);
			if (tnRet != null)
				break;
		}
		return tnRet;
	}
	
	public void reduceTreeNode(TreeNode tnParent, String resId) {
		List<TreeNode> childs = tnParent.getChildren();
		if (childs != null) {
			for (int idx = 0; idx < childs.size(); ++idx) {
				TreeNode tn = childs.get(idx);
				if (resId.indexOf(tn.getKey()) == -1) {
					childs.remove(idx);
				} else {
					reduceTreeNode(tn, resId);
				}
			}
		}
	}

	public String listLiveDisplay(){
		this.deviceFacade.findMduIpAndPort();
		try {
			String devs = this.getReqParam("devs");
			logger.debug(devs);
			this.setReqAttr("devices",JSON.parse(devs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "listLiveDisplay";
	}
}
