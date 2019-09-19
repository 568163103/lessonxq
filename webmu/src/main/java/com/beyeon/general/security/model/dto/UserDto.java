package com.beyeon.general.security.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.beyeon.hibernate.fivsdb.TPrivData;
import com.beyeon.hibernate.fivsdb.TUserRole;
import com.beyeon.hibernate.fivsdb.UserInfo;

public class UserDto implements Serializable{
	private UserInfo userInfo;
	private List<TPrivData> privDatas = new ArrayList<TPrivData>();
	private List<TUserRole> roleMaps = new ArrayList<TUserRole>();
	private String topManager = "0";
	private String corpId = "";
	
	public UserDto() {
		super();
	}
	
	public UserDto(UserInfo userInfo) {
		super();
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<TPrivData> getPrivDatas() {
		return privDatas;
	}

	public void setPrivDatas(List<TPrivData> privDatas) {
		this.privDatas = privDatas;
		for (int i = 0; i < privDatas.size(); i++) {
			TPrivData privData = privDatas.get(i);
			if (privData.getPrivType() == TPrivData.PRIV_TYPE_CORP) {
				corpId = privData.getPrivCode();
				return;
			}
		}
	}

	public List<TUserRole> getRoleMaps() {
		return roleMaps;
	}

	public void setRoleMaps(List<TUserRole> roleMaps) {
		this.roleMaps = roleMaps;
	}

	public void setAmidToRole(){
		for (TUserRole roleMap : roleMaps) {
			roleMap.getId().setAmid(userInfo.getId());
		}
	}

	public String getTopManager() {
		return topManager;
	}

	public void setTopManager(String topManager) {
		this.topManager = topManager;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
}
