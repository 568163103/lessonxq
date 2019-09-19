package com.beyeon.common.security.spring.access.authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.util.WebUtils;

import com.beyeon.common.cache.CacheSupport;

/**
 * User: lwf
 * Date: 12-6-4
 * Time: 下午5:09
 */
public class SLLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	private static Logger logger =LoggerFactory.getLogger(SLLoginUrlAuthenticationEntryPoint.class);
	private UrlJumpContainer urlJumpContainer;
	private CacheSupport cacheSupport;

	public SLLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	public void setUrlJumpContainer(UrlJumpContainer urlJumpContainer) {
		this.urlJumpContainer = urlJumpContainer;
	}

	public void setCacheSupport(CacheSupport cacheSupport) {
		this.cacheSupport = cacheSupport;
	}

	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
		Cookie sidCookie = WebUtils.getCookie(request, "JSESSIONID");
		if(null != sidCookie){
			String jsessionid = sidCookie.getValue();
			if(0 < cacheSupport.del("login.id.offline." + jsessionid)){
				return urlJumpContainer.getInputUrl(request)+"?login_error=3";
			}
		}
		return urlJumpContainer.getInputUrl(request)+"?login_error=2";
	}
}
