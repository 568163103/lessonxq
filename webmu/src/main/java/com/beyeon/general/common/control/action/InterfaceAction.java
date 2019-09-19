package com.beyeon.general.common.control.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.general.common.model.dto.TransferData;

/**
 * User: Administrator
 * Date: 2016/3/16
 * Time: 17:02
 */
@Component("interfaceAction")
public class InterfaceAction extends BaseAction {
	private Logger logger = LoggerFactory.getLogger(InterfaceAction.class);
	public String execute() throws Exception {
		TransferData transferData = new TransferData("none");
		try {
			JSONObject reqData = JSON.parseObject(getReqParamJson());
			transferData.setCmd(reqData.getString("cmd"));
			if(reqData.size()==2 && null != reqData.getJSONObject("data")) {
				transferData.setData(reqData.getJSONObject("data"));
			} else {
				transferData.setData(reqData);
			}
			this.setReqAttr("transferData", transferData);
			return transferData.getCmd();
		} catch (Exception e) {
			transferData.setMessage("请求参数异常");
			logger.debug(transferData.getMessage(),e);
			responseJSON(transferData);
			return null;
		}
	}

}
