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
		<span class="win-header-title">设备管理 &gt; 其他设备管理 &gt; 编辑其他设备</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/device/equipment!update.do" method="post">
			<input type="hidden" id="id" name="id" value="${equipment.id}">
			<table width="90%" class="inputTable">
				<tr>
					<td style="text-align:right;" width="10%"><label class="field-request">* </label>其他设备类型：</td>
					<td width="20%">
						<select class="validate[required]" name="equipment.type" id="type" disabled="disabled">
							<option value="">请选择</option>
							<c:forEach var="type" items="${equipmentTypes}">
								<option value="${type.key}" <c:if test="${equipment.type eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right;" width="10%"><label class="field-request">* </label>其他设备名称：</td>
					<td width="20%"><input type="text" value="${equipment.name}" class="validate[required,ajax[checkUnique]]" name="equipment.name" id="equipment_name_${equipment.id}" maxlength="32"/></td>
				</tr>
				<tr>
					<td style="text-align: right;"><label class="field-request">* </label>ip：</td>
					<td><input type="text" value="${equipment.ip}" class="validate[required,custom[ipv4]]" name="equipment.ip" id="ip" /></td>
					<td style="text-align: right;">端口：</td>
					<td><input type="text" value="${equipment.port}" name="equipment.port" id="port" /></td>
				</tr>				
				
				<tr>
					<td style="text-align:right;">物理位置：</td>
					<td>
						<input type="text" name="equipment.pos" id="pos" value="${equipment.pos}">
					</td>
					<td style="text-align:right;">厂家：</td>
					<td width="20%">
						<select class="validate[required]" name="equipment.corp" id="corp" >
							<option value="">请选择</option>
							<c:forEach var="corp" items="${equipmentCorps}">
								<option value="${corp.key}" <c:if test="${equipment.corp eq corp.key}">selected</c:if>>${corp.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">设备型号：</td>
					<td>
						<input type="text" name="equipment.version" id="version" value="${equipment.version}">
					</td>
					<td style="text-align:right;">MAC地址：</td>
					<td>
						<input type="text" name="equipment.mac" id="mac" value="${equipment.mac}">
					</td>
				</tr>
				<tr>
					
					<td style="text-align:right;">备注(最多256字符)：</td>
					<td colspan ="3">
						<textarea name="equipment.remark" id="remark" value="${equipment.remark}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${equipment.remark}</textarea>
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