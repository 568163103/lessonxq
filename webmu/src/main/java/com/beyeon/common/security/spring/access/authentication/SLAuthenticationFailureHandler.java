package com.beyeon.common.security.spring.access.authentication;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.IpUtil;
import com.beyeon.general.security.model.bpo.UserBpo;
import com.beyeon.hibernate.fivsdb.TUserTrace;
import com.beyeon.hibernate.fivsdb.UserInfo;
import com.beyeon.hibernate.fivsdb.UserSecurity;
import com.beyeon.nvss.log.model.bpo.UserTraceBpo;
import com.socket.sip.SpringInit;

/**
 * User: lwf
 * Date: 12-5-29
 * Time: 下午4:27
 */
public class SLAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private UrlJumpContainer urlJumpContainer;

	public void setUrlJumpContainer(UrlJumpContainer urlJumpContainer) {
		this.urlJumpContainer = urlJumpContainer;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String failureUrl = urlJumpContainer.getInputUrl(request);
		String username =request.getParameter("j_username");
		String message = exception.getMessage();
		System.out.println("username======"+username);
		if ("输入密码不对".equals(message) || "Bad credentials".equals(message)){
			String ip = IpUtil.getClientIpAddr(request);
			UserTraceBpo userTraceBpo = (UserTraceBpo)SpringInit.getApplicationContext().getBean("userTraceBpo");
			UserSecurity userSecurity = userTraceBpo.findUserSecurity(username);
			int error = 0 ;
			if (userSecurity != null){
				Integer errorCount = userSecurity.getErrorCount();
				error = errorCount+1;
				userSecurity.setErrorCount(error);
				userSecurity.setLastErrorLoginTime(DateUtil.getTime());
				userTraceBpo.updateUserSecurity(userSecurity);
			}else{
				String userId = userTraceBpo.findUserIdByUserName(username);
				if (StringUtils.isNotBlank(userId)){
					error = 1;
					userSecurity = new UserSecurity();
					userSecurity.setUserId(userId);
					userSecurity.setErrorCount(1);
					userSecurity.setLastErrorLoginTime(DateUtil.getTime());
					userTraceBpo.saveUserSecurity(userSecurity);
				}
			}
			UserBpo userBpo = (UserBpo)SpringInit.getApplicationContext().getBean("userBpo");
			UserInfo user = userBpo.getUserInfoByUsername(username);
			TUserTrace trace  = new TUserTrace();
			trace.setAmid(user.getId());
			trace.setMenuName("loginError");
			trace.setMid(1);
			trace.setTerminalIp(ip);
			trace.setOperateDate(new Date());
			trace.setOperateStatus((short) 0);
			trace.setUserName(username);
			trace.setUserTrace("用户登录--账户"+username+"--密码连续错误次数："+error);
			trace.setUserType((short) 1);
			userTraceBpo.save(trace);
		}
		if (failureUrl == null) {
			logger.debug("No failure URL set, sending 401 Unauthorized error");

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
		} else {
			saveException(request, exception);
			failureUrl += "?login_error=1";
			if (super.isUseForward()) {
				logger.debug("Forwarding to " + failureUrl);

				request.getRequestDispatcher(failureUrl).forward(request, response);
			} else {
				logger.debug("Redirecting to " + failureUrl);
				super.getRedirectStrategy().sendRedirect(request, response, failureUrl);
			}
		}
	}
}
