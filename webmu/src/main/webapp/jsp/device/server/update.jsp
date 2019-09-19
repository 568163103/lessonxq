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
	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 服务器管理 &gt; 编辑服务器</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/device/server!update.do" method="post">
			<input type="hidden" id="id" name="id" value="${server.id}">
			<table width="90%" class="inputTable">
				<tr>
					<td style="text-align:right;" width="10%"><label class="field-request">* </label>服务器类型：</td>
					<td width="20%">
						<select class="validate[required]" name="server.type" id="type" disabled="disabled">
							<option value="">请选择</option>
							<c:forEach var="type" items="${types}">
								<option value="${type.key}" <c:if test="${server.type eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right;" width="10%"><label class="field-request">* </label>服务器名称：</td>
					<td width="20%"><input type="text" value="${server.name}" class="validate[required,ajax[checkUnique]]" name="server.name" id="server_name_${server.id}" maxlength="32"/></td>
				</tr>
				<tr>
					<td style="text-align: right;"><label class="field-request">* </label>ip：</td>
					<td><input type="text" value="${server.ip}" class="validate[required,custom[ipv4]]" name="server.ip" id="ip" /></td>
					<td style="text-align: right;"><label class="field-request">* </label>端口：</td>
					<td><input type="text" value="${server.port}" class="validate[required,custom[integer]]" name="server.port" id="port" /></td>
				</tr>
				<tr>
					<td style="text-align: right;"><label class="field-request">* </label>用户名：</td>
					<td><input type="text" value="${server.username}" class="validate[required]" name="server.username" id="username" /></td>
					<td style="text-align: right;"><label class="field-request">* </label>密码：</td>
					<td><input type="text" value="${server.password}" class="validate[required]" name="password" id="password" /></td>
				</tr>
				<tr>						
					
					<td style="text-align:right;">最大连接数：</td>
					<td><input type="text" value="${server.maxConnection}" name="server.maxConnection" id="maxConnection" /></td>
					<td style="text-align:right;">设备型号：</td>
					<td>
						<input type="text" name="serverExtra.version" id="version" value="${serverExtra.version}">
					</td>
				</tr>
				<tr>
					
					<td style="text-align:right;">物理位置：</td>
					<td>
						<input type="text" name="server.address" id="address" value="${server.address}">
					</td>
					<td style="text-align:right;">厂家：</td>
					<td >
						<input type="text" name="serverExtra.corp" id="corp" value="${serverExtra.corp}">
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">MAC地址：</td>
					<td>
						<input type="text" name="serverExtra.mac" id="mac" value="${serverExtra.mac}">
					</td>
				</tr>
				
				<tr>
					
					<td style="text-align:right;">备注(最多256字符)：</td>
					<td colspan="3">
						<textarea name="server.description" id="description" value="${server.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${server.description}</textarea>
					</td>
					
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