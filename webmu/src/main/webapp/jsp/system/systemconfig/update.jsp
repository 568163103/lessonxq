<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
	<script type="text/javascript">
		function edit() {
			if (confirm("确认要更新？")) {
				$("#pageForm").submit();
			}
		}
		$(function(){
			$("#pageForm").validationEngine();
		});
		
		var enables = $("[name='systemConfig.errorLoginConfig']");
		enables.each(function(){
			if($(this).val()=='${systemConfig.errorLoginConfig}') {
				$(this).attr("checked",true);
			} else {
				$(this).attr("checked",false);
			}
		});
	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">系统设置</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/system/systemConfig!update.do" method="post">
			<input type="hidden" id="id" name="systemConfig.id" value="${systemConfig.id}">
			<input type="hidden" id="exceptionUser" name="systemConfig.exceptionUser" value="${systemConfig.exceptionUser}">
			<table width="90%" class="inputTable">
				<tr>
					<td style="text-align:right;" width="20%"><label class="field-request">* </label>密码最大错误次数：</td>
					<td width="30%"><input type="text" value="${systemConfig.maxErrorCount}"  class="validate[required,custom[onlyNumberSp]]" name="systemConfig.maxErrorCount" id="systemConfig.maxErrorCount" maxlength="32"/></td>
					<td style="text-align: right;" width="20%">错误锁定时间(秒)：</td>
					<td width="30%"><input type="text" value="${systemConfig.userLockTime}"  class="validate[required,custom[onlyNumberSp],min[60]]" name="systemConfig.userLockTime" id="systemConfig.userLockTime" maxlength="32"/></td>
				</tr>
							
						
				<tr>
					<td style="text-align: right;">是否开启登录失败策略：</td>
					<td >
						<select class="validate[required]" name="systemConfig.errorLoginConfig" id="systemConfig.errorLoginConfig" >
							<option value="">请选择</option>
							<option value="0" <c:if test="${systemConfig.errorLoginConfig == '0'}">selected</c:if>>关闭</option>
							<option value="1" <c:if test="${systemConfig.errorLoginConfig == '1'}">selected</c:if>>开启</option>
						</select>
					</td>		
					<td style="text-align: right;">是否开启密码定时失效策略：</td>
					<td >
						<select class="validate[required]" name="systemConfig.pwdResetFlag" id="systemConfig.pwdResetFlag" >
							<option value="">请选择</option>
							<option value="0" <c:if test="${systemConfig.pwdResetFlag == '0'}">selected</c:if>>关闭</option>
							<option value="1" <c:if test="${systemConfig.pwdResetFlag == '1'}">selected</c:if>>开启</option>
						</select>
					</td>				
				</tr>	
				<tr><td style="text-align: right;">密码有效期限(分钟)：</td>
					<td width="20%"><input type="text" value="${systemConfig.keepPasswordTime}"  class="validate[required,custom[onlyNumberSp,min[60]]]" name="systemConfig.keepPasswordTime" id="systemConfig.keepPasswordTime" maxlength="32"/></td>
					<td style="text-align: right;">密码失效提前提醒时间(分钟)：</td>
					<td width="20%"><input type="text" value="${systemConfig.pwdRemaindTime}"  class="validate[required,custom[onlyNumberSp,min[60]]]" name="systemConfig.pwdRemaindTime" id="systemConfig.pwdRemaindTime" maxlength="32"/></td>
				</tr>
				<tr>
					<td colspan = "4" style="text-align:center;" ><p style="color:red;">登录失败策略如下： 1.结束会话；  2.限制非法登录次数；  3.登录连接超时自动退出</p></td>
					
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a></td>
				</tr>
				
			</table>
		</form>
	</div>
</body>
</html>