<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headForm.jsp"%>
<% String flag = request.getParameter("flag"); %>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common/style.css"/>
<title>${global_app_name}</title>
	<script type="text/javascript">
		$(document).ready(function(){
		    $("#pageForm").validationEngine();			
			 var resetflag = <%=flag%>;
			 if (resetflag == 1){
				 $("#show1").css("display","block");
			 }else if (resetflag == 2){
				  $("#show2").css("display","block");
			 }
		});
		
		function save(){
			var pwd = $("#pageForm").find("[name=newPasswd]").val();
			var reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/
			if (!reg.test(pwd)){
				 $("#show3").css("display","block");
				return;
			}
			$('#pageForm').submit();
		}
	</script>
</head>
<body class="main_bg" style="text-align: center">
<div class="list_table">
	<div class="win-header">
		<span class="win-header-title">修改密码 </span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/security/password!modifyPasswd.do" method="post">		
			<table>
			<div id="show1" style="display:none;text-align:center;"><p style="color:red;">提示：首次登陆请修改密码</p></div>
			<div id="show2" style="display:none;text-align:center;"><p style="color:red;">提示：密码已失效，请修改密码</p></div>
			<div id="show3" style="display:none;text-align:center;"><p style="color:red;">密码必须包含1个数字、1个大写字母、1个小写字母</p></div>
			</table>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align:right; width: 45%">旧密码：</td>
					<td><input type="password" class="validate[required]" name="passwd" id="passwd"/></td>
				</tr>
				<tr>
					<td style="text-align:right;">新密码：</td>
					<td><input type="password" class="validate[required,pwcheck,minSize[8],maxSize[64]]" name="newPasswd" id="newPasswd" autocomplete="off"/></td>
				</tr>
				<tr>
					<td style="text-align:right;">确认密码：</td>
					<td><input type="password" class="validate[required,equals[newPasswd]]" name="newPasswd1" id="newPasswd1" autocomplete="off"/></td>
				</tr>
				<tr>
					<td style="text-align:center;" colspan="2">
						<a class="a_button" href="javascript:void(0)" onclick="save()"><span>保存</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0)" onclick="history.go(-1);"><span>返回</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
				
			
		</form>
	</div>
</div>
</body>
</html>