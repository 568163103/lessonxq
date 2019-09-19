package com.beyeon.nvss.storage.control.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.dto.EncoderChannelTreeDto;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.device.model.dto.ChannelDto;
import com.beyeon.nvss.storage.model.dto.StorageDto;

@Component("storageAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StorageAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(StorageAction.class);
	private DeviceFacade deviceFacade;
	private static String CENTRAL_STORAGE = "1";
	private static String FRONT_STORAGE = "2";
	
	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}
	
	public String findPage() {
		String flag = this.getReqParam("flag");
		
		List encoders = deviceFacade.getEncoderBpo().findAllEncoder();
		List channels = null;
		
		if (flag.equals(CENTRAL_STORAGE)) {
			channels = deviceFacade.getChannelBpo().findAllByMsuChannel();
		} else {
			channels = deviceFacade.getChannelBpo().findAllChannel();
		}
		
		EncoderChannelTreeDto ectree = new EncoderChannelTreeDto();
		ectree.AddEncodersAndChannels(encoders, channels);
		ectree.filter();
		
		String result = toJSON(ectree);
		this.setReqAttr("ectree", result);
		this.setReqAttr("flag", this.getReqParam("flag"));
		return "findPage";
	}
	
	public String getStorageView() {
		String id = this.getReqParam("id");
		String flag = this.getReqParam("flag");
		
		String ip = "";
		String port = "";
		String message = "获取服务器地址失败，请检查配置是否正确！";
		
		if (flag.equals(CENTRAL_STORAGE)) {
			ChannelDto cd = deviceFacade.getChannelBpo().get(id);
			String serverId = cd.getChannel().getServerId();
			
			if (StringUtils.isNotEmpty(serverId)) {
				Server s = deviceFacade.getServerBpo().get(serverId).getServer();
				if (s != null) {
					ip = s.getIp(); 
				}
			}
			port = ResourceUtil.getServerConf("msu.server.port");
			
		} else if (flag.equals(FRONT_STORAGE)) {
			ip = deviceFacade.getServerBpo().findServerIp(ServerType.CMU);
			port = ResourceUtil.getServerConf("cmu.server.port");
		}
		
		if (StringUtils.isNotEmpty(ip) && StringUtils.isNotEmpty(port)) {
			StringBuilder cmuUrl = new StringBuilder();
			cmuUrl.append("http://").append(ip).append(":").append(port).append("/ReqStorageInterval");
			String result = HttpClientUtil.post(cmuUrl.toString(), "{\"id\":\""+id+"\"}");
			logger.debug(result);
			
			if (StringUtils.isNotEmpty(result)) {
				StorageDto storage = JSON.parseObject(result, StorageDto.class);
				if (storage.getResult() == 0) {
					storage.makeStorageGroup();
					this.setReqAttr("flag", this.getReqParam("flag"));
					this.setReqAttr("isError", false);
					this.setReqAttr("storage", storage);
					return "storageView";
				}
			} 
			
			message = "获取数据失败！";
		}
		
		this.setReqAttr("flag", this.getReqParam("flag"));
		this.setReqAttr("isError", true);
		this.setReqAttr("message", message);
		
		return "storageView";
	}
}
