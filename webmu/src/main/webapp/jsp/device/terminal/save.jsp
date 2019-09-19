<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#pageForm").validationEngine();
	});

	function edit() {
		if (confirm("确认要新增？")) {
			$("#pageForm").submit();
		}
	}
</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 终端管理 &gt; 添加终端</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/terminal!save.do" method="post">
		<s:token/>
		<table width="90%" class="inputTable">
			<tr>
				<td style="text-align: right"><label class="field-request">* </label>位置：</td>
				<td width="20%">
					<select class="validate[required]" name="terminal.position" id="position">
						
						<c:forEach var="type" items="${positions}">
							<option value="${type.key}" <c:if test="${terminal.position eq type.key}">selected</c:if>>${type.value}</option>
						</c:forEach>
					</select>
				</td>
				<td style="text-align: right;" width="10%"><label class="field-request">* </label>终端名称：</td>
				<td width="20%"><input type="text" value="${terminal.name}" class="validate[required,ajax[checkUnique]]" name="terminal.name" id="terminal_name" /></td>
			
			</tr>
			<tr>
				<td style="text-align: right;"  width="10%"><label class="field-request">* </label>ip：</td>
				<td width="20%"><input type="text" value="${terminal.ip}" class="validate[required,custom[ipv4]]" name="terminal.ip" id="ip" /></td>
				<td style="text-align:right;">物理位置：</td>
				<td >
					<input type="text" name="terminal.address" id="address" value="${terminal.address}">
				</td>
			
				
				
			</tr>
<%--			<tr>--%>
<%--				<td style="text-align: right;">是否启用：</td>--%>
<%--				<td colspan="3"><input type="radio" name="terminal.enabled" id="enabled1" value="true"/> 是 <input type="radio" name="terminal.enabled" id="enabled2" value="false"/> 否</td>--%>
<%--			</tr>--%>
			<tr>
				<td style="text-align:right;">备注(最多256字符)：</td>
				<td colspan ="3">
					<textarea name="terminal.description" id="description" value="${terminal.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${terminal.description}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center;">
					<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>添加</span></a>&nbsp;&nbsp;
					<a class="a_button" href="${ctx}/device/terminal!findPage.do"><span>返回</span></a></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>