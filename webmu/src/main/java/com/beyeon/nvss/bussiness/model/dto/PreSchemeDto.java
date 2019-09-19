package com.beyeon.nvss.bussiness.model.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.hibernate.fivsdb.Action;
import com.beyeon.hibernate.fivsdb.ActionType;
import com.beyeon.hibernate.fivsdb.PreScheme;

/**
 * User: Administrator
 * Date: 2015/9/19
 * Time: 15:29
 */
public class PreSchemeDto {
	private PreScheme preScheme;
	private Action actionObject;
	private List<Action> actionObjects = new ArrayList<Action>();

	private Integer id ;
	private String name;
	private Map<String,String> actionTypeNum;

	public PreScheme getPreScheme() {
		return preScheme;
	}

	public void setPreScheme(PreScheme preScheme) {
		this.preScheme = preScheme;
	}

	public Action getActionObject() {
		return actionObject;
	}

	public void setActionObject(Action actionObject) {
		this.actionObject = actionObject;
	}

	public List<Action> getActionObjects() {
		return actionObjects;
	}

	public void setActionObjects(List<Action> actionObjects) {
		this.actionObjects = actionObjects;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String,String> getActionTypeNum() {
		return actionTypeNum;
	}

	public String getActionTypes() {
		return "";
	}

	public void setActionTypes(String actionType) {
		actionTypeNum = new HashMap<String, String>();
		for (AutoCompleteDto key : ActionType.getTypes()) {
			actionTypeNum.put(key.getValue(), getStringTimes(actionType, key.getValue()).toString());
		}
	}

	public Integer getStringTimes(String original,String target) {
		if (StringUtils.isBlank(original)||StringUtils.isBlank(target)){
			return 0;
		}
		return (original.length()-original.replaceAll(target,"").length())/target.length();
	}
}
