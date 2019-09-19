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
			alert("请至少选择要删除终端。");
			return false;
		}
		msg = "确认要删除所选终端？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/device/terminal!delete.do");
		}
		$("#pageForm").submit();
	}

	function editTerminal(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/device/terminal!beforeUpdate.do");
		$("#pageForm").submit();
	}


	function exportList(){
		$("#pageForm").attr("action","${ctx}/device/terminal!exportList.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/device/terminal!findPage.do");
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 终端管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/terminal!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">终端ID：</td>
					<td><input type="text" id="pageObject.params.id" name="pageObject.params.id" value="${pageObject.params.id}"></td>
					<td style="text-align: right">终端名称：</td>
					<td><input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}"></td>
					<td style="text-align: right">终端ip：</td>
					<td><input type="text" id="pageObject.params.ip" name="pageObject.params.ip" value="${pageObject.params.ip}"></td>
				</tr>
				<tr>
					<td style="text-align: right">位置：</td>
					<td>
						<select name="pageObject.params.position" id="pageObject.params.position">
							<option value="">全部</option>
							<c:forEach var="type" items="${positions}">
								<option value="${type.key}" <c:if test="${pageObject.params.position eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>

<%--					<td style="text-align: right">是否启用：</td>--%>
<%--					<td>--%>
<%--						<select id="pageObject.params.enabled" name="pageObject.params.enabled">--%>
<%--							<option value="">全部</option>--%>
<%--							<option value="true" <c:if test="${pageObject.params.enabled eq 'true'}">selected</c:if>>是</option>--%>
<%--							<option value="false" <c:if test="${pageObject.params.enabled eq 'false'}">selected</c:if>>否</option>--%>
<%--						</select>--%>
<%--					</td>--%>
					<td></td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th>终端ID</th>
						<th>终端名称</th>
						<th>位置</th>
						<th>ip</th>
						<th width="10%">物理位置</th>
<%--						<th>是否启用</th>--%>
						<th width="35%">描述</th>
						<th width="5%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="terminal" items="${pageObject.resultList}">
			    	<tr <c:if test="${!terminal.enabled}">class="font-red" </c:if>>
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${terminal.id}"/>
				    	</td>
				    	<td>${terminal.id}</td>
				    	<td>${terminal.name}</td>
						<td>${terminal.positionZH}</td>
						<td>${terminal.ip}</td>
						<td>${terminal.address}</td>
<%--				    	<td>${terminal.enabledZh}</td>--%>
						<td>${terminal.description}</td>
				    	<td>
					    	<a href="javascript:editTerminal('${terminal.id}')">编辑</a>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="8">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="8" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:location.href='${ctx}/device/terminal!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a>
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