<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	
	
	function edit() {
			$("#pageForm").attr("action","${ctx}/system/systemConfig!beforeUpdate.do");
			$("#pageForm").submit();



	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">系统管理  </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/equipment!findPage.do" method="post">
		<input type="hidden" name="id" >		
		<table width="90%" class="inputTable">
				<tr>
					<td style="text-align:right;" width="20%"><label class="field-request">* </label>密码最大错误次数：</td>
					<td width="30%"><input type="text" value="${systemConfig.maxErrorCount}"  class="validate[required,custom[onlyNumberSp]]" name="systemConfig.maxErrorCount" id="systemConfig.maxErrorCount" maxlength="32"  readonly = "true"/></td>
					<td style="text-align: right;" width="20%">错误锁定时间(秒)：</td>
					<td width="30%"><input type="text" value="${systemConfig.userLockTime}"  class="validate[required,custom[onlyNumberSp]]" name="systemConfig.userLockTime" id="systemConfig.userLockTime" maxlength="32" readonly = "true"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">是否开启登录失败策略：</td>
					<td >
						<select class="validate[required]" name="systemConfig.errorLoginConfig" id="systemConfig.errorLoginConfig" disabled = "disabled">
							<option value="">请选择</option>
							<option value="0" <c:if test="${systemConfig.errorLoginConfig == '0'}">selected</c:if>>关闭</option>
							<option value="1" <c:if test="${systemConfig.errorLoginConfig == '1'}">selected</c:if>>开启</option>
						</select>
					</td>	
					<td style="text-align: right;">是否开启密码定时失效策略：</td>
					<td >
						<select class="validate[required]" name="systemConfig.pwdResetFlag" id="systemConfig.pwdResetFlag" disabled = "disabled">
							<option value="">请选择</option>
							<option value="0" <c:if test="${systemConfig.pwdResetFlag == '0'}">selected</c:if>>关闭</option>
							<option value="1" <c:if test="${systemConfig.pwdResetFlag == '1'}">selected</c:if>>开启</option>
						</select>
					</td>						
				</tr>				
						
					
				<tr>
					<td style="text-align: right;">密码有效期限(分钟)：</td>
					<td width="20%"><input type="text" value="${systemConfig.keepPasswordTime}"  class="validate[required,custom[onlyNumberSp]]" name="systemConfig.keepPasswordTime" id="systemConfig.keepPasswordTime" maxlength="32" readonly = "true"/></td>
					<td style="text-align: right;">密码失效提前提醒时间(分钟)：</td>
					<td width="20%"><input type="text" value="${systemConfig.pwdRemaindTime}"  class="validate[required,custom[onlyNumberSp]]" name="systemConfig.pwdRemaindTime" id="systemConfig.pwdRemaindTime" maxlength="32" readonly = "true"/></td>
				</tr>
				
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>编辑</span></a>&nbsp;&nbsp;
				</tr>
			</table>
	</form>
	</div>
</body>
</html>