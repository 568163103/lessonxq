<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	function checkedItems() {
		var flag = false;
		$("#pageForm").find("[name=items]").each(function() {
			if (this.checked) {
				flag = true;
			}
		});
		return flag;
	}
	
	function deleteAll() {
		if (!checkedItems()) {
			alert("请至少选择要删除其他设备。");
			return false;
		}
		msg = "确认要删除所选其他设备？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/device/equipment!delete.do");
		}
		$("#pageForm").submit();
	}
	
	function exportList(){
		$("#pageForm").attr("action","${ctx}/device/equipment!exportList.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/device/equipment!findPage.do");
	}
	
	function editEquipment(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/device/equipment!beforeUpdate.do");
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 其他设备管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/equipment!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">其他设备ID：</td>
					<td><input type="text" id="pageObject.params.id" name="pageObject.params.id" value="${pageObject.params.id}"></td>
					<td style="text-align: right">其他设备名称：</td>
					<td><input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}"></td>
					<td style="text-align: right">位置：</td>
					<td>
						<select name="pageObject.params.position" id="pageObject.params.position">
							<option value="">全部</option>
							<c:forEach var="type" items="${positions}">
								<option value="${type.key}" <c:if test="${pageObject.params.position eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>			
					<td style="text-align: right">其他设备ip：</td>
					<td><input type="text" id="pageObject.params.ip" name="pageObject.params.ip" value="${pageObject.params.ip}"></td>
					
					<td style="text-align: right">其他设备类型：</td>
					<td>
						<select name="pageObject.params.type" id="pageObject.params.type">
							<option value="">全部</option>
							<c:forEach var="type" items="${types}">
								<option value="${type.key}" <c:if test="${pageObject.params.type eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
					<td></td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="4%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="12%">其他设备ID</th>
						<th width="14%">其他设备名称</th>
						<th width="7%">位置</th>
						<th width="7%">其他设备类别</th>
						<th width="10%">其他设备型号</th>
						<th width="10%">ip</th>
						<th width="5%">端口</th>
						<th width="10%">物理位置</th>
						<th width="15%">描述</th>
						<th width="5%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="equipment" items="${pageObject.resultList}">
			    	<tr> <td class="center">
				    		<input type="checkbox" name="items" class="items" value="${equipment.id}"/>
				    	</td>
				    	<td>${equipment.id}</td>
				    	<td>${equipment.name}</td>
						<td>${equipment.positionZH}</td>
						<td>${equipment.typeName}</td>
						<td>${equipment.version}</td>
						<td>${equipment.ip}</td>
				    	<td>${equipment.port}</td>
						<td>${equipment.pos}</td>
						<td>${equipment.remark}</td>
				    	<td>
					    	<a href="javascript:editEquipment('${equipment.id}')">编辑</a>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="11">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="11" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:location.href='${ctx}/device/equipment!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return exportList();"><span>导出</span></a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>