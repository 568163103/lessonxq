package com.beyeon.common.security.spring.access.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.beyeon.common.cache.CacheSupport;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.security.spring.access.authentication.UrlJumpContainer;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.hibernate.fivsdb.SystemConfig;
import com.beyeon.hibernate.fivsdb.UserSecurity;
import com.beyeon.nvss.log.model.bpo.UserTraceBpo;
import com.beyeon.security.util.SystemSnUtils;
import com.socket.sip.SpringInit;

public class AuthenticationHandlerImpl extends OncePerRequestFilter implements AuthenticationHandler {
	private Logger logger =LoggerFactory.getLogger(this.getClass());
	private Boolean isNeedValidateCodeHandle = true;
	private Boolean isNeedLoginTimeHandle = true;
	private Boolean isOnlyOne = true;
	private UrlJumpContainer urlJumpContainer;
	public static CacheSupport cacheSupport;

	public void setUrlJumpContainer(UrlJumpContainer urlJumpContainer) {
		this.urlJumpContainer = urlJumpContainer;
	}

	public void setCacheSupport(CacheSupport cacheSupport) {
		AuthenticationHandlerImpl.cacheSupport = cacheSupport;
	}

	public Boolean getNeedValidateCodeHandle() {
		return isNeedValidateCodeHandle;
	}

	public void setNeedValidateCodeHandle(Boolean needValidateCodeHandle) {
		isNeedValidateCodeHandle = needValidateCodeHandle;
	}

	public Boolean getNeedLoginTimeHandle() {
		return isNeedLoginTimeHandle;
	}

	public void setNeedLoginTimeHandle(Boolean needLoginTimeHandle) {
	}

	public void setOnlyOne(Boolean isOnlyOne) {
		this.isOnlyOne = isOnlyOne;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		authentication(request, response, filterChain);
	}

	public Boolean systemSnHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
		if (!SystemSnUtils.isAvailable()){
			httpRequest.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new Exception("请更新使用许可"));
			httpRequest.getRequestDispatcher(urlJumpContainer.getInputUrl(httpRequest)+"?login_error=1").forward(httpRequest, httpResponse);
			return false;
		}
		return true;
	}

	public Boolean validateCodeHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
		String validateCode = httpRequest.getParameter("validateCode");
		if (StringUtils.isBlank(validateCode)
				|| !validateCode.equals(httpRequest.getSession().getAttribute("validateCode"))) {
			httpRequest.setAttribute("loginTime",0);
			httpRequest.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new Exception("验证码有误"));
			httpRequest.getRequestDispatcher(urlJumpContainer.getInputUrl(httpRequest)+"?login_error=1").forward(httpRequest, httpResponse);
			return false;
		}
		httpRequest.getSession().removeAttribute("validateCode");
		ResourceUtil.setRequestContext("signCode", validateCode);
		return true;
	}

	public Boolean loginTimeHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
		String userName = httpRequest.getParameter("j_username");
		UserTraceBpo userTraceBpo = (UserTraceBpo)SpringInit.getApplicationContext().getBean("userTraceBpo");
		UserSecurity userSecurity = userTraceBpo.findUserSecurity(userName);
		int loginTime = userSecurity !=null ? userSecurity.getErrorCount() : 0 ;
		int max = SystemConfig.getConfig().getMaxErrorCount();
		int flag = SystemConfig.getConfig().getErrorLoginConfig();
		boolean status = true;
		if( flag == 1){
			if (loginTime >= max ){
				String lastErrorTime = userSecurity.getLastErrorLoginTime();
				Date s = DateUtil.parse(lastErrorTime, DateUtil.yyyyMMddHHmmssSpt);
				Date nt = DateUtil.addSeconds(s, SystemConfig.getConfig().getUserLockTime());
				if (nt.getTime() > new Date().getTime()){
					loginTime = 0;
					httpRequest.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new Exception("登录连续错误达到上限，账号已被冻结，<br>请在"+SystemConfig.getConfig().getUserLockTime()+"秒后再次登录"));
					httpRequest.getRequestDispatcher(urlJumpContainer.getInputUrl(httpRequest)+"?login_error=1").forward(httpRequest, httpResponse);
					status = false;
				}
			}
		}else{
			loginTime = 0 ;
		}
//		loginTime += "0";
		httpRequest.setAttribute("max",max);
		httpRequest.setAttribute("loginTime",loginTime);
		httpRequest.setAttribute("invalidTime",SystemConfig.getConfig().getUserLockTime());
		return status;
	}

	@Override
	public Boolean authentication(ServletRequest request, ServletResponse response, Object object) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		UrlJumpContainer.setAppType(httpRequest,httpResponse);

//		if (!systemSnHandle(httpRequest, httpResponse)){
//			return false;
//		}

		if(isNeedValidateCodeHandle && !validateCodeHandle(httpRequest,httpResponse)){
			return false;
		}
		if(isNeedLoginTimeHandle && !loginTimeHandle(httpRequest, httpResponse)){
			return false;
		}

		doAuthentication(request, response,object);

		if (isOnlyOne) {
			afterAuthentication(httpRequest, httpResponse);
		}
		return true;
	}

	public void doAuthentication(ServletRequest request, ServletResponse response,Object object) throws IOException, ServletException {
	}

	public void afterAuthentication(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse) throws IOException, ServletException {
		String userName = httpRequest.getParameter("j_username");
		SecurityContext context = SecurityContextHolder.getContext();
		if (null != context && null != context.getAuthentication()) {
			//全局执行（必须）
			UserTraceBpo userTraceBpo = (UserTraceBpo)SpringInit.getApplicationContext().getBean("userTraceBpo");
			userTraceBpo.resetUserSecurity(userName);
			final String hgCode = ResourceUtil.getHgCode();
			final String appType = UrlJumpContainer.getAppType(httpRequest);
			final String lastOnlineUserName = cacheSupport.get("login.id.online." + userName);
			final String currOnlineUserName = ResourceUtil.getPublicConf("self.server.path")+"/j_logout.auth;jsessionid="+httpRequest.getSession().getId();
			cacheSupport.setex("login.id.online." + userName, 8 * 60 * 60, currOnlineUserName);
			if(StringUtils.isNotBlank(lastOnlineUserName) && !lastOnlineUserName.equals(currOnlineUserName)){
				try {
					if (!lastOnlineUserName.contains("jsessionid=")){
						return;
					}
					Map params = new HashMap();
					params.put("j_apptype",StringUtils.isNotBlank(appType)?appType:UrlJumpContainer.APP_TYPE_SUPER);
					Map heads = new HashMap();
					String jsessionid = lastOnlineUserName.split("jsessionid=")[1];
					StringBuilder cookies = new StringBuilder();
					cookies.append("JSESSIONID=").append(jsessionid);
					Cookie routeidCookie = WebUtils.getCookie(httpRequest,"ROUTEID");
					if(null != routeidCookie){
						cookies.append(";ROUTEID=").append(routeidCookie.getValue());
					}
					heads.put("Cookie", cookies.toString());
					String flag = HttpClientUtil.getHttpClientWrapper().get(lastOnlineUserName,params,heads);
					if (flag.contains("j_username")) {
						ResourceUtil.setRequestContext("app_hg_code", hgCode);
						cacheSupport.setex("login.id.offline." + jsessionid, 29 * 60, "");
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			doAfterAuthentication(httpRequest, httpResponse);
		}
	}

	public void doAfterAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
	}
}
