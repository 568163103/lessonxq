package com.beyeon.common.security.spring.detail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.beyeon.common.security.spring.access.authentication.UrlJumpContainer;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.hibernate.fivsdb.TPrivData;
import com.beyeon.hibernate.fivsdb.TUserLink;

public class UserDetail extends User {
	private static final long serialVersionUID = -7926993835510670700L;
	private String id;
	private String name;
	private String mobile;
	private String mail;
	//管理员关系表
	private List<TUserLink> userLinks = new ArrayList<TUserLink>();
	//数据权限信息表
	private List<TPrivData> privDatas = new ArrayList<TPrivData>();
	private List<TPrivData> tempPrivDatas = new ArrayList<TPrivData>();
	
	public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<TUserLink> getUserLinks() {
		return userLinks;
	}

	public void setUserLinks(List<TUserLink> userLinks) {
		this.userLinks = userLinks;
	}

	public List<TPrivData> getPrivDatas() {
		if(tempPrivDatas.size()>0){
			return tempPrivDatas;
		}
		return privDatas;
	}

	public void setPrivDatas(List<TPrivData> privDatas) {
		this.privDatas = privDatas;
	}
	
	public void addPrivDatas(TPrivData privData){
		this.tempPrivDatas.add(privData);
	}
	
	public void cleanPrivDatas(){
		this.tempPrivDatas.clear();
	}
	
	public void cleanPrivDatas(int dataType){
		List list = new ArrayList();
		for (TPrivData object : this.tempPrivDatas) {
			if(object.getPrivType()==(dataType)){
				list.add(object);
			}
		}
		this.tempPrivDatas.removeAll(list);
	}
	
	public List<String> getPrivDatas(int dataType){
		List<String> list = new ArrayList<String>();
		for (TPrivData object : this.tempPrivDatas) {
			if(object.getPrivType()==(dataType)){
				list.add(object.getPrivCode());
			}
		}
		if(list.size()>0){
			return list;
		}
		for (TPrivData object : this.privDatas) {
			if(object.getPrivType()==(dataType)){
				list.add(object.getPrivCode());
			}
		}
		return list;
	}

	public boolean isSelfManager(){
		boolean flag = true;
		for (Iterator<GrantedAuthority> iterator = getAuthorities().iterator(); iterator.hasNext(); ) {
			GrantedAuthority grantedAuthorityTemp =  iterator.next();
			if(grantedAuthorityTemp.getAuthority().endsWith("-"+UrlJumpContainer.APP_TYPE_SUPER)){
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	public boolean isNoTopManager(){
		List<TUserLink> linkusers = SpringUtil.getCurrUser().getUserLinks();
		return linkusers.size() > 0 && linkusers.get(0).getFullAmid().split("-").length > 1;
	}
}
