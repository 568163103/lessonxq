package com.beyeon.common.security.spring.detail;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * @author liwf
 */
public class ResourceDetail implements Serializable {
	
	private static final long serialVersionUID = 4640497640533200574L;

	public static final String RESOURCE_TYPE_URL = "URL";

	public static final String RESOURCE_TYPE_METHOD = "METHOD";

	public static final String RESOURCE_TYPE_TAG = "TAG";

	private Integer mid;

	private String url;

	private String resType;

	private GrantedAuthority[] authorities;

	public ResourceDetail(Integer mid, String resType,String url, GrantedAuthority[] authorities) {
		this.mid = mid;
		this.resType = resType;
		this.url = url;
		setAuthorities(authorities);
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(GrantedAuthority[] authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority array");
		for (int i = 0; i < authorities.length; i++) {
			Assert.notNull(authorities[i],
					"Granted authority element " + i + " is null - GrantedAuthority[] cannot contain any null elements");
		}
		this.authorities = authorities;	
	}

	public boolean equals(Object rhs) {
		if (!(rhs instanceof ResourceDetail))
			return false;
		ResourceDetail resauth = (ResourceDetail) rhs;
		if(!getMid().equals(resauth.getMid()))
			return false;
		if(!getResType().equals(resauth.getResType()))
			return false;
		if (resauth.getAuthorities().length != getAuthorities().length)
			return false;
		for (int i = 0; i < getAuthorities().length; i++) {
			if (!getAuthorities()[i].equals(resauth.getAuthorities()[i]))
				return false;
		}
		return  true ;
	}

	public int hashCode() {
		int code = 168;
		if (getAuthorities() != null) {
			for (int i = 0; i < getAuthorities().length; i++)
				code *= getAuthorities()[i].hashCode() % 7;
		}
		if (getMid() != null)
			code *= getMid().hashCode() % 7;
		return code;
	}

}
