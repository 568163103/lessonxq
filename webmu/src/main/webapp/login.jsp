<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${global_app_name}-登录</title>
	<%@ include file="/jsp/common/head/headForm.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/common/login.css" />
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.md5.js"></script>
	<script type="text/javascript">
		<sec:authentication property ="principal" var ="SPRING_SECURITY_CONTEXT"/>
		<c:if test="${not empty SPRING_SECURITY_CONTEXT}">
		window.location.href='${ctx}/welcome.do';
		</c:if>
		$(document).ready(function(){
			$("#loginForm").submit(function(){
				if($("#loginForm").validationEngine("validate")) {
					var password = $("#password");
					password.val($.md5(password.val() + $("#validateCode").val()));
				}
			}).validationEngine();
		});
		function getMobileValidateCode(){
			if($("#username").val() == ""){
				alert("请输入帐号");
				return;
			}
			$.post("${ctx}/jsp/common/mobileValidateCode.jsp?"+Math.random(),{username:$("#username").val()},function(data){
				if(data && data.indexOf("ok") > -1){
					alert("验证码已发送至您手机，请查收！");
				} else {
					alert("获取验证码失败！");
				}
			})
		}
	</script>
</head>
<body>
	<div id="login_main">
		<div id="login_content" class="r-box" style="background:url(${ctx}/images/${app_identity_code}/Welcome.jpg) no-repeat scroll 50% 50% transparent";>
			<div id="login_box">
				<form id="loginForm" name="loginForm" action="${ctx}/j_login.auth" method="post" style="padding-top:40px;margin-top: 5px;margin-left: -45px;">
					<input type="hidden" id="j_logintype" name="j_apptype" value="1">
					<table border="0" align="center" cellpadding="0" cellspacing="0" class="tanchu_tab" >
						<tr>
							<td colspan="3" align="center">
								<c:if test="${param.login_error != null && param.login_error eq '1' }">
								<font color="red">
									<c:if test="${empty loginTime || empty max || loginTime < max - 2}">
										${SPRING_SECURITY_LAST_EXCEPTION.message}
									</c:if>
									<c:if test="${loginTime eq max - 2 }">
										${SPRING_SECURITY_LAST_EXCEPTION.message}；<br>连续密码错误，再有一次账号将被冻结
									</c:if>
									<c:if test="${loginTime > max - 2 && max > 0}">
										${SPRING_SECURITY_LAST_EXCEPTION.message}；连续错误${max}次，账号已被冻结，<br>请在${invalidTime}秒后再次登录
									</c:if>
								</font>
								</c:if>
								<c:if test="${param.login_error != null && param.login_error eq '2' }">
								<font color="red">
									对不起，尚未登录或登录超时，请重新登录。
								</font>
								</c:if>
								<c:if test="${param.login_error != null && param.login_error eq '3' }">
								<font color="red">
									对不起，您的用户名在异地登录，您已被迫下线。
								</font>
								</c:if>
							</td>
						</tr>
					</table>
					<div class="field">
						<label for="username">用户名：</label>
						<input class="login-text J-UserName J-Focused validate[required]" type="text" id="username" name="j_username" tabindex="1" maxlength="32" value="everybody"/>
					</div>
					<div class="field">
						<label id="password_label" for="password">密&#12288;码：</label>
						<input class="login-text validate[required]" type="password" name="j_password" id="password" tabindex="2" maxlength="64" value="everybody" autocomplete="off"/>
					 </div>
					<div id="l_f_code" class="field img-code">
						<label for="validateCode">验证码：</label>
						<input type="text" id="validateCode" name="validateCode" class="login-text checkcode validate[required]" tabindex="3" name="checkcode" maxlength="4"/>
						<img src="${ctx}/jsp/common/validateCode.jsp" id="dimg" class="check-code-img"/>
						<a href="#" onclick="$('#dimg').attr('src','${ctx}/jsp/common/validateCode.jsp?'+Math.random());">看不清？换一张</a>
					</div>
					<div class="submit">
						<button tabindex="5" class="btn-submit" type="submit" name="submit">登录</button>
						<a tabindex="6" class="forget-pw" id="forget_pw_safe" href="${ctx}/jsp/security/password/forgetPasswd.jsp">忘记密码？</a>
						<input id="_remember_me" name="remember-me" type="hidden" value="false"/>
					</div>
				</form>
			</div>
		<!--	<div><img style="width: 150px;height: 75px;margin-top: -275px;margin-left: 125px;" id="header-logo" src="${ctx}/css/common/images/logo${app_identity_code}.png" alt=""/></div>			
			<div id="bg_l" class="a-box bg-blank"></div>
			<div id="bg_r" class="a-box bg-blank"></div>   
			<h1 id="login_title" class="bg-blank">${global_app_name}</h1> -->
		</div>
		<div id="footer">
			<div id="copyright">${app_copyright_name}</div>
			<!-- 
			<div style="padding: 50px 0 0 0;font-size: 15px;">
				<a href="http://www.beyeon.com/download/cusetup.zip" target="_blank">pc版下载</a>&nbsp;&nbsp;&nbsp;
				<a href="http://www.beyeon.com/download/blueeyeand.apk" target="_blank">android版下载</a>&nbsp;&nbsp;&nbsp;
				<a href="http://itunes.apple.com/app/id1090328205" target="_blank">ios版下载</a>
			</div>
			<div style="padding: 5px 0 0 0;">
				<img src="${ctx}/images/pc_qrc.png" alt="" style="width: 100px;height: 100px;">
				<img src="${ctx}/images/android_qrc.png" alt="" style="width: 100px;height: 100px;">
				<img src="${ctx}/images/iphone_qrc.png" alt="" style="width: 100px;height: 100px;">
			</div>
			 -->
		</div>
	</div>
</body>
</html>