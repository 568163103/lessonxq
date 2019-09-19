package com.beyeon.common.security.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.beyeon.common.exception.AppExceptionRtImpl;

/**
 * 授权管理
 *
 * @author liwf
 */
public class SecurityHelper {

	/**
	 * 新增或删除多对多关系
	 */
	public static void saveAuth(Collection authSet, Object authObj,boolean isAuthorized) {
		if (isAuthorized) {
			if (authSet.contains(authObj)) {
				authSet.remove(authObj);
			}
		} else {
			if (!authSet.contains(authObj)) {
				authSet.add(authObj);
			}
		}
	}
	
	/**
	 * 由GrantedAuthority Collection 转为 GrantedAuthority 数组
	 * @param auths
	 * @return
	 */
	public static GrantedAuthority[] convertToGrantedAuthority(Collection auths){
		return (GrantedAuthority[]) auths.toArray(new GrantedAuthority[auths.size()]);
	}
	
	/**
	 * 把Bean中的某个属性的值转化为GrantedAuthority
	 * @param list Collection 一组Bean
	 * @param propertyName 属性名
	 * @return GrantedAuthority[] GrantedAuthority数组
	 */
	public static GrantedAuthority[] convertToGrantedAuthority(Collection list, String propertyName){
		Set authorities = new HashSet();
		try {
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				String authority = BeanUtils.getProperty(iter.next(), propertyName);
				authorities.add(new SimpleGrantedAuthority(authority));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return convertToGrantedAuthority(authorities);
	}
	
	/**
	 * 把权限组转为 ConfigAttribute
	 * @param authorities 权限
	 * @param isProtectAllResource 是否保护所有资源，true，则所有资源默认为受保护， false则只有声明了并且与权限挂钩了的资源才会受保护 
	 * @return
	 */
	public static Collection getCadByAuthorities(Collection authorities, boolean isProtectAllResource ){
		Collection col = new ArrayList();
		if (authorities == null){
			if(isProtectAllResource){
				col.add(new SecurityConfig("public"));
				return col;
			} else {
				return null;
			}
		}

		for (Iterator iter = authorities.iterator(); iter.hasNext();) {
			GrantedAuthority authority = (GrantedAuthority) iter.next();
			col.add(new SecurityConfig(authority.getAuthority()));
		}

		return col;
	}
	
	public static String getLoginId(){
		String loginId = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null){
		} else if (auth.getPrincipal() == null) {
		} else if (auth.getPrincipal() instanceof UserDetails) {
		    loginId = ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			loginId = auth.getPrincipal().toString();
		}
		return loginId;
	}
	
	public static boolean authorizeUsingUrlCheck(String url){
		return getPrivilegeEvaluator().isAllowed((ServletActionContext.getRequest()).getContextPath(),
				url, "post", SecurityContextHolder.getContext().getAuthentication());
	}
	
	private static WebInvocationPrivilegeEvaluator getPrivilegeEvaluator() {
		ServletContext servletContext = ServletActionContext.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		Map<String, WebInvocationPrivilegeEvaluator> wipes = ctx.getBeansOfType(WebInvocationPrivilegeEvaluator.class);

		if (wipes.size() == 0) {
			throw new AppExceptionRtImpl("判断权限异常！");
		}

		return (WebInvocationPrivilegeEvaluator) wipes.values().toArray()[0];
	}
	
}