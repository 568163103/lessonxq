package com.beyeon.common.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.beyeon.common.cache.CacheSupport;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.security.spring.detail.UserDetail;
import com.beyeon.common.web.control.util.SpringUtil;

public class SLContextInitListener implements ServletContextListener,HttpSessionListener {
	private CacheSupport cacheSupport;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("global_app_name", ResourceUtil.getAppName());
		sce.getServletContext().setAttribute("app_copyright_name", ResourceUtil.getCopyrightName());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void sessionCreated(HttpSessionEvent se) {
		
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		if(null == cacheSupport){
			cacheSupport = (CacheSupport) SpringUtil.getBean("cacheSupport");
		}
		UserDetail ud = null;
		if (null != se.getSession()) {
			Object context = se.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
			if (null != context && context instanceof SecurityContext) {
				Authentication authentication = ((SecurityContext) context).getAuthentication();
				if (null != authentication && !(authentication instanceof AnonymousAuthenticationToken)) {
					ud = (UserDetail) authentication.getPrincipal();
				}
			}
		}
		if(null != ud){
			String userName = ud.getUsername();
			String onlineUserName = cacheSupport.get("login.id.online." + userName);
			if(null != onlineUserName && onlineUserName.contains(se.getSession().getId())){
				cacheSupport.del("login.id.online." + userName);
			}
		}
	}

}
