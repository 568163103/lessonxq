package com.beyeon.common.security.spring.access.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.web.control.util.SpringUtil;

/**
 * User: lwf
 * Date: 14-1-14
 * Time: 下午5:38
 */
public class SLDelegatingFilterProxy extends DelegatingFilterProxy {
	private final PathMatcher pathMatcher = new AntPathMatcher();
	private final boolean isProtectAllResource = Boolean.valueOf(ResourceUtil.getCoreConf("app.protect.all.resource"));
	private final String[] excludeUrls = ResourceUtil.getGlobalPublicConf("app.auth.exclude.url").split(",");
	private SecurityContextPersistenceFilter securityContextPersistenceFilter = null;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if(isExcludeUrl(request, response,filterChain)){
			if (null == securityContextPersistenceFilter){
				securityContextPersistenceFilter = (SecurityContextPersistenceFilter) SpringUtil.getBean("securityContextPersistenceFilter");
			}
			securityContextPersistenceFilter.doFilter(request, response, filterChain);
			return;
		}
		super.doFilter(request, response, filterChain);
	}

	protected boolean isExcludeUrl(ServletRequest request, ServletResponse response, FilterChain filterChain){
		boolean isExclude = false;
		if (!isProtectAllResource) {
			return isExclude;
		}
		if (null != excludeUrls && StringUtils.isNotBlank(excludeUrls[0])){
			FilterInvocation f = new FilterInvocation(request,response,filterChain);
			for (String url : excludeUrls) {
				if (pathMatcher.match(url.replaceAll("\\?","P") + "*", f.getRequestUrl().replaceAll("\\?","P"))){
					isExclude = true;
					break;
				}
			}
		}
		return isExclude;
	}
}
