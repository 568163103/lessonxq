<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/head.jsp"%>
	<link type="text/css" href="${ctx}/css/common/pwd.css" rel="stylesheet" />
<title>${global_app_name}</title>
	<script type="text/javascript">
		$(document).ready(function(){
		    setTimeout(function () {
				window.location.href="../../../";
			}, 3000);
		});
	</script>
</head>
<body class="main_bg">
	<div id="main" class="clearfix">
		<div id="main1" class="clearfix">
			<ul class="m1_one clearfix" id="m1_one">
				<li class="li_frist">1</li>
				<li id="li_two">2</li>
				<li>3</li>
				<li class="li_hover1">4</li>
			</ul>
			<ul class="m1_two clearfix" id="m1_two">
				<li class="li_frist" id="t1">选择认证途径</li>
				<li id="t2">输入验证码</li>
				<li id="li_three t3">重新设置密码</li>
				<li id="t4" class="li_hover2">成功获取密码</li>
			</ul>
		</div>
		<div id="main2">
			<br/><br/><br/>
			<div align="center">重置密码成功，三秒钟后自动跳转到首页。</div>
		</div>
		<div id="footer">
			<div id="copyright">${app_copyright_name}</div>
		</div>
	</div>
</body>
</html>