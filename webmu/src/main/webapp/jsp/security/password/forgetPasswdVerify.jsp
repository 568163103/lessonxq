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
		function reForgetPasswd(){
			$.post("${ctx}/security/password!reForgetPasswd.do?"+Math.random(),function(data){
				alert(data);
			})
		}
	</script>
</head>
<body class="main_bg">
	<form id="pageForm" name="pageForm" action="${ctx}/security/password!forgetPasswdVerify.do" method="post">
		<div id="main" class="clearfix">
			<div id="main1" class="clearfix">
				<ul class="m1_one clearfix" id="m1_one">
					<li class="li_frist">1</li>
					<li class="li_hover1">2</li>
					<li>3</li>
					<li>4</li>
				</ul>
				<ul class="m1_two clearfix" id="m1_two">
					<li class="li_frist" id="t1">选择认证途径</li>
					<li id="t2" class="li_hover2">输入验证码</li>
					<li id="li_three t3">重新设置密码</li>
					<li id="t4">成功获取密码</li>
				</ul>
			</div>
			<div id="main2">
				<div class="m2_user">
					<span class="name">请输入验证码：</span>
					<input type="text" class="validate[required]" name="verifyMessage" id="verifyMessage"/>
					<a href="javascript:void(0)" onclick="reForgetPasswd()">重新获取</a>
				</div>
				<a class="a_button btn" style="left: 595px;top: 150px" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>下一步</span></a>
				<a class="a_button btn" style="left: 695px;top: 150px" href="${ctx}/jsp/security/password/forgetPasswd.jsp"><span>返回</span></a>
			</div>
		</div>

		<div id="footer">
			<div id="copyright">${app_copyright_name}</div>
		</div>
	</form>
</body>
</html>