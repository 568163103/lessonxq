package com.beyeon.common.security.spring.access.authentication;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.beyeon.general.security.model.bpo.UserBpo;
import com.beyeon.hibernate.fivsdb.TUserTrace;
import com.beyeon.hibernate.fivsdb.UserInfo;
import com.beyeon.nvss.log.model.bpo.UserTraceBpo;
import com.socket.sip.SpringInit;

/**
 * User: lwf
 * Date: 12-5-31
 * Time: 下午3:00
 */
public class SLLogoutSuccessHandler implements LogoutSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private UrlJumpContainer urlJumpContainer;
	private static HashMap<String,TUserTrace> sessionMap = new HashMap<String,TUserTrace>();
	
	public static void putSession(String session,TUserTrace tUserTrace){
		sessionMap.put(session, tUserTrace);
	}
	public static TUserTrace getUserTrace(String session){
		return sessionMap.get(session);
	}
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	public void setUrlJumpContainer(UrlJumpContainer urlJumpContainer) {
		this.urlJumpContainer = urlJumpContainer;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		this.clearAuthenticationAttributes(request);
		this.redirectStrategy.sendRedirect(request, response, this.urlJumpContainer.getInputUrl(request));
	}


	/**
	 * Removes temporary authentication-related data which may have been stored in the session
	 * during the authentication process.
	 */
	protected final void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		try {
			TUserTrace login = getUserTrace(request.getRequestedSessionId());
			if (login != null){
				UserTraceBpo userTraceBpo = (UserTraceBpo)SpringInit.getApplicationContext().getBean("userTraceBpo");
				TUserTrace trace  = new TUserTrace();
				trace.setAmid(login.getAmid());
				trace.setMenuName("logout");
				trace.setMid(1);
				trace.setOperateDate(new Date());
				trace.setOperateStatus((short) 1);
				trace.setUserName(login.getUserName());
				trace.setUserTrace("用户退出--账户"+login.getUserName());
				trace.setTerminalIp(login.getTerminalIp());
				trace.setUserType((short) 1);
				userTraceBpo.save(trace);
			}
			
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (session == null) {
			return;
		}
		
		
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
