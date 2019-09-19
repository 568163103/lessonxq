<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headForm.jsp"%>
<title>${global_app_name}</title>
	<script type="text/javascript">
		$(document).ready(function(){
		    $("#pageForm").validationEngine();
		});
	</script>
</head>
<body class="main_bg">
	<form id="pageForm" name="pageForm" action="${ctx}/security/password!forgetPasswdMobileVerify.do" method="post">
		<table width="100%">
			<tr>
				<td style="text-align:right;">验证码：</td>
				<td><input type="text" class="validate[required]" name="verifyMessage" id="verifyMessage"/></td>
			</tr>
			<tr>
				<td style="text-align:center;">
					<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>下一步</span></a>
				</td>
				<td>				
					<a class="a_button" href="javascript:history.back()"><span>返回</span></a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>