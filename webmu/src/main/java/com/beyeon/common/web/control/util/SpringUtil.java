package com.beyeon.common.web.control.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.beyeon.common.security.spring.detail.UserDetail;

public class SpringUtil {
	private static WebApplicationContext wac = null;

	/**
	 * @param beanName 要获取的bean名称
	 * @return
	 */
	public static Object getBean(String beanName){
		if(null == wac){
			wac = ContextLoader.getCurrentWebApplicationContext();
		}
		return wac.getBean(beanName);
	}

	public static UserDetail getCurrUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context.getAuthentication() == null || context.getAuthentication() instanceof AnonymousAuthenticationToken)
			return null;
		return (UserDetail) context.getAuthentication().getPrincipal();
	}
}
