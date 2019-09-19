package com.beyeon.nvss.bussiness.control.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.hibernate.fivsdb.ActionType;
import com.beyeon.hibernate.fivsdb.Channel;
import com.beyeon.hibernate.fivsdb.User;
import com.beyeon.nvss.bussiness.model.BussinessFacade;
import com.beyeon.nvss.bussiness.model.dto.PreSchemeDto;

@Component("preSchemeAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PreSchemeAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(PreSchemeAction.class);
	private BussinessFacade bussinessFacade;
	private PreSchemeDto preSchemeDto;

	public Object getModel() {
		if(null == preSchemeDto){
			String id = this.getReqParam("id");
			String actionId = this.getReqParam("actionId");
			if(StringUtils.isNotBlank(id)){
				preSchemeDto = bussinessFacade.getPreSchemeBpo().get(Integer.valueOf(id),actionId);
			} else {
				preSchemeDto = new PreSchemeDto();
			}
		}
		return preSchemeDto;
	}
	
	public PreSchemeDto getPreSchemeDto() {
		return preSchemeDto;
	}
	public void setPreSchemeDto(PreSchemeDto preSchemeDto) {
		this.preSchemeDto = preSchemeDto;
	}

	public void setBussinessFacade(BussinessFacade bussinessFacade) {
		this.bussinessFacade = bussinessFacade;
	}

	public String beforeUpdate(){
		this.setReqAttr("actionTypes", ActionType.getTypes());
		this.setReqAttr("channels", Channel.getObjectMap());
		this.setReqAttr("users", User.getObjectMap());
		return "beforeUpdate";
	}
	public String updateAction(){
		this.bussinessFacade.getPreSchemeBpo().updateAction(preSchemeDto);
		return "reBeforeSave";
	}

	public String beforeSave(){
		this.setReqAttr("actionTypes", ActionType.getTypes());
		this.setReqAttr("channels", Channel.getObjectMap());
		this.setReqAttr("users", User.getObjectMap());
		this.preSchemeDto.setActionObject(null);
		return "beforeSave";
	}
	
	public String saveAction(){
		this.bussinessFacade.getPreSchemeBpo().saveAction(preSchemeDto);
		return "reBeforeSave";
	}
	
	public String delete(){
		String[] preScheme = this.getReqParams("items");
		this.bussinessFacade.getPreSchemeBpo().delete(preScheme);
		return findPage();
	}
	
	public String findPage(){
		this.bussinessFacade.getPreSchemeBpo().find(this.getPageObject());
		return "findPage";
	}

	public String savePreScheme(){
		this.bussinessFacade.getPreSchemeBpo().savePreScheme(preSchemeDto);
		return "reBeforeSave";
	}

	public String getPreset() throws Exception {
		String channelId = this.getReqParam("channelId");
		List list = this.bussinessFacade.getPreSchemeBpo().getPresetByChannelId(channelId);
		responseJSON(list);
		return null;
	}

	public String deleteAction(){
		String[] aid = this.getReqParams("items");
		this.bussinessFacade.getPreSchemeBpo().deleteAction(aid);
		return "reBeforeSave";
	}

}
