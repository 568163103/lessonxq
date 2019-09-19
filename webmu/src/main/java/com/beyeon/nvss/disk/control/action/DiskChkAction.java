package com.beyeon.nvss.disk.control.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.hibernate.fivsdb.Encoder;
import com.beyeon.hibernate.fivsdb.EncoderDisk;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.disk.model.bpo.EncoderDiskBpo;
import com.beyeon.nvss.disk.model.dto.EncoderDiskStatusDto;

@Component("diskChkAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DiskChkAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(DiskChkAction.class);
	private DeviceFacade deviceFacade;
	private EncoderDiskBpo encoderDiskBpo;

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}
	
	public void setEncoderDiskBpo(EncoderDiskBpo encoderDiskBpo) {
		this.encoderDiskBpo = encoderDiskBpo;
	}

	public String findPage() {
		List encoders = deviceFacade.getEncoderBpo().findAllEncoder();
		String result = toJSON(encoders);
		this.setReqAttr("encoders", result);
		return "findPage";
	}
	
	public String getAllEncoders() {
		List encoders = deviceFacade.getEncoderBpo().findAllEncoder();
		this.responseJSON(encoders);
		return null;
	}
	
	public String getChildDiskInfo() {
		String id = this.getReqParam("id");
		EncoderDisk ed = this.encoderDiskBpo.find(id);
		this.responseJSON(ed);
		return null;
	}
	
	public String getChildEncoder() {
		String id = this.getReqParam("id");
		Encoder e = deviceFacade.getEncoderBpo().findById(id);
		this.responseJSON(e);
		return null;
	}
	
	public String getDiskStatus() {
		String id = this.getReqParam("id");
		String name = this.getReqParam("name");
		String tag = this.getReqParam("tag"); // 是否为下级
		String ip = this.getReqParam("ip");
		
		this.setReqAttr("id", id);
		this.setReqAttr("name", name);
		
		EncoderDisk ed = null;
		
		if (!tag.equals("1")) {
			ed = this.encoderDiskBpo.find(id);
		} else {
			StringBuilder url = new StringBuilder();
			url.append("http://").append(ip).append(":18080/thirdparty/basicFormInterface!execute.do");
			logger.debug(url.toString());
			
			Map<String,String> params = new HashMap<String,String>();
			params.put("cmd", "150104");
			params.put("id", id);
			String result = HttpClientUtil.get(url.toString(), params);
			logger.debug(result);
			if (result == null || result.equals("null"))
				return "diskNoData";

			JSONObject oBean = JSON.parseObject(result);
			ed = oBean.toJavaObject(EncoderDisk.class);
		}
		
		// 处理数据
		if (ed != null) {
			this.setReqAttr("lastTime", ed.getLastTime());
			
			JSONObject o = (JSONObject)JSON.parse(ed.getDiskInfo());
			if (o != null) {
				JSONArray array = o.getJSONArray("DiskList");
				List<EncoderDiskStatusDto> diskList = new ArrayList<EncoderDiskStatusDto>();
				
				for (int idx = 0; idx < array.size(); ++idx) {
					JSONObject obj = (JSONObject)array.get(idx);
					Integer freeSpace = obj.getInteger("FSpace");
					Integer totalSpace = obj.getInteger("TSpace");
					EncoderDiskStatusDto eds = new EncoderDiskStatusDto(
							new String[]{"已用空间（MB）", "剩余空间（MB）"},
							new Integer[]{totalSpace - freeSpace, freeSpace});
					eds.setNum(obj.getString("Num"));
					eds.setStatus(obj.getString("Status"));
					eds.setFreeSpace(freeSpace);
					eds.setTotalSpace(totalSpace);
					diskList.add(eds);
				}
				this.setReqAttr("result", toJSON(diskList));
				this.setReqAttr("diskList", diskList);
			}
			this.setReqAttr("description", ed.getDescription());
		} else {
			return "diskNoData";
		}
		
		return "getDiskStatus";
	}
	
	public String getChildren() {
		String id = this.getReqParam("id");
		String ip = this.getReqParam("ip");
		
		StringBuilder url = new StringBuilder();
		url.append("http://").append(ip).append(":18080/thirdparty/basicFormInterface!execute.do");
		logger.debug(url.toString());
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("cmd", "150103");
		String result = HttpClientUtil.get(url.toString(), params);
		logger.debug(result);
	
		Object o = JSON.parse(result);
		this.responseJSON(o);
		
		return null;
	}
	
}
