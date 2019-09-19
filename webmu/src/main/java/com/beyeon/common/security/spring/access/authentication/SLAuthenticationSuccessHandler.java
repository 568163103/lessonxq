package com.beyeon.common.security.spring.access.authentication;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.beyeon.common.security.spring.access.filter.AuthenticationHandlerImpl;
import com.beyeon.common.util.IpUtil;
import com.beyeon.general.security.model.bpo.UserBpo;
import com.beyeon.hibernate.fivsdb.TUserTrace;
import com.beyeon.hibernate.fivsdb.UserInfo;
import com.beyeon.nvss.log.model.bpo.UserTraceBpo;
import com.socket.sip.SpringInit;

/**
 * User: lwf
 * Date: 12-5-17
 * Time: 下午3:17
 */
public class SLAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private UrlJumpContainer urlJumpContainer;

	public void setUrlJumpContainer(UrlJumpContainer urlJumpContainer) {
		this.urlJumpContainer = urlJumpContainer;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws ServletException, IOException {
		String moduleType = UrlJumpContainer.getModuleType(request);
		if(StringUtils.isNotBlank(moduleType) && "3,6".indexOf(moduleType) != -1){
			super.clearAuthenticationAttributes(request);
			request.getRequestDispatcher(urlJumpContainer.getTargetUrl(request)).forward(request, response);
			return;
		}
		try {
			
			
			String ip = IpUtil.getClientIpAddr(request);
			String username =request.getParameter("j_username");	
			UserTraceBpo userTraceBpo = (UserTraceBpo)SpringInit.getApplicationContext().getBean("userTraceBpo");
			userTraceBpo.resetUserSecurity(username);
			UserBpo userBpo = (UserBpo)SpringInit.getApplicationContext().getBean("userBpo");
			UserInfo user = userBpo.getUserInfoByUsername(username);
			TUserTrace trace  = new TUserTrace();
			trace.setAmid(user.getId());
			trace.setMenuName("login");
			trace.setMid(1);
			trace.setOperateDate(new Date());
			trace.setOperateStatus((short) 1);
			trace.setUserName(username);
			trace.setUserTrace("用户登录--账户"+username);
			trace.setTerminalIp(ip);
			trace.setUserType((short) 1);
			SLLogoutSuccessHandler.putSession(request.getRequestedSessionId(), trace);
			userTraceBpo.save(trace);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		super.onAuthenticationSuccess(request, response, authentication);
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		String targetUrl = super.determineTargetUrl(request, response);
		if(StringUtils.isNotBlank(targetUrl) && targetUrl != "/")
			return targetUrl;
		return urlJumpContainer.getTargetUrl(request);
	}
}
