<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headForm.jsp"%>
	<link type="text/css" href="${ctx}/css/common/pwd.css" rel="stylesheet" />
<title>${global_app_name}</title>
	<script type="text/javascript">
		$(document).ready(function(){
		    $("#pageForm").validationEngine();
		});
	</script>
</head>
<body class="main_bg">
	<form id="pageForm" name="pageForm" action="${ctx}/security/password!restPassword.do" method="post">
		<div id="main" class="clearfix">
			<div id="main1" class="clearfix">
				<ul class="m1_one clearfix" id="m1_one">
					<li class="li_frist">1</li>
					<li id="li_two">2</li>
					<li class="li_hover1">3</li>
					<li>4</li>
				</ul>
				<ul class="m1_two clearfix" id="m1_two">
					<li class="li_frist" id="t1">选择认证途径</li>
					<li id="t2">输入验证码</li>
					<li id="li_three t3" class="li_hover2">重新设置密码</li>
					<li id="t4">成功获取密码</li>
				</ul>
			</div>
			<div id="main2">
				<div class="m2_user">
					<span class="name">新&nbsp;&nbsp;密&nbsp;&nbsp;码：</span><input type="password" class="validate[required,minSize[6],maxSize[64],custom[eanRange]]" name="passwd" id="passwd"/>
				</div>
				<div class="m2_user" style="padding-top: 52px">
					<span class="name">确认密码：</span>
					<input type="password" class="validate[required,equals[passwd]]" name="passwd1" id="passwd1"/>
				</div>
				<a class="a_button btn" style="top: 250px" href="javascript:void(0)" onclick="return $('#pageForm').submit()"><span>下一步</span></a>
				<a class="a_button btn1" style="top: 250px" href="${ctx}/security/password!forgetPasswdVerify.do"><span>返回</span></a>
			</div>
		</div>

		<div id="footer">
			<div id="copyright">${app_copyright_name}</div>
		</div>
	</form>
</body>
</html>