<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.beyeon.common.util.GeneralUtil" %>
<%@ page import="com.beyeon.common.web.control.util.SpringUtil" %>
<%@ page import="com.beyeon.general.security.model.bpo.UserBpo" %>
<%@ page import="com.beyeon.hibernate.fivsdb.UserInfo" %>
<%
	String username = request.getParameter("username");
	UserInfo user = ((UserBpo)SpringUtil.getBean("userBpo")).getUserInfoByUsername(username);
	if(null == user){
		out.print("fail");
		return;
	}
	String sRand = GeneralUtil.randomNum(4);
	GeneralUtil.sendShortMsg(user.getPhone(),"验证码为：" + sRand + "，请您在30分钟内登录网络视频监控系统。");
	session.setAttribute("validateCode", sRand);
	out.print("ok");
%>
