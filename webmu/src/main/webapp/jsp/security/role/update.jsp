<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${global_app_name}</title>
<%@ include file="/jsp/common/head/headForm.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
    $("#pageForm").validationEngine();
    $("#role\\.roleType").val('${role.roleType}');
    $("#role\\.status").val('${role.status}');
	$("#role\\.isPublic").val('${role.isPublic}');
});

		function edit() {
			if (confirm("确认要更新？")) {
			$("#pageForm").submit();
			}
		}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">权限管理 &gt; 角色管理 &gt; 编辑角色</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/security/role!update.do" method="post">
		<input type="hidden" id="rid" name="rid" value="${role.rid }">
		<table class="inputTable" width="100%">
			<tr>
				<td style="text-align:right;">角色名称：</td>
				<td><input type="text" value="${role.rname}" name="role.rname" id="role_rname_${role.rid}" class="validate[required,ajax[checkUnique]]"/></td>
			</tr>
			<tr>
				<td style="text-align:right;">角色类型：</td>
				<td>
					<select id="role.roleType" name="role.roleType">
						<option value="1">公共管理员</option>
						<option value="2">私有管理员</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align:right;">状态：</td>
				<td>
					<select id="role.status" name="role.status">
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align:right;">是否公用：</td>
				<td>
					<select id="role.isPublic" name="role.isPublic">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</td>
			</tr>
			</acf:display>
			<tr>
				<td colspan="2" style="text-align:center;">
					<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
					<a class="a_button" href="${ctx}/security/role!findPage.do"><span>返回</span></a>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>