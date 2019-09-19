package com.beyeon.general.common.control.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.bpo.BaseBpo;

/**
 * User: Administrator
 * Date: 2016/3/16
 * Time: 17:02
 */
@Component("practicalAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PracticalAction extends BaseAction {
	private static Map<String,BaseBpo> uniqueCheckBpoMap = new HashMap<String,BaseBpo>();

	public String download(){
		String reqFile = this.getReqParam("url");
		try {
			ServletActionContext.getResponse().setHeader("Content-Disposition",
					"attachment;filename=" + new String(reqFile.substring(reqFile.lastIndexOf("/")+1).getBytes(),
							"iso8859-1"));
		} catch (UnsupportedEncodingException e) {
		}
		outputStreamFile(reqFile);
		return null;
	}

	public String checkUnique(){
		String fieldId = this.getReqParam("fieldId");
		String value = this.getReqParam("fieldValue");
		String bussName = fieldId.split("_")[0]+"Bpo";
		String attrName = fieldId.split("_")[1];
		String id = "";
		if(fieldId.split("_").length>2){
			id = fieldId.split("_")[2];
		}
		BaseBpo baseBpo = uniqueCheckBpoMap.get(bussName);
		if (null == baseBpo){
			baseBpo = (BaseBpo) SpringUtil.getBean(bussName);
			uniqueCheckBpoMap.put(bussName, baseBpo);
		}
		List list = new ArrayList();
		list.add(fieldId);
		list.add(baseBpo.checkUnique(attrName, value, id));
		responseJSON(list);
		return null;
	}

}
