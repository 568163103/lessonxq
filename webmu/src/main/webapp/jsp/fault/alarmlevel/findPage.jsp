<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">

	function edit(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/fault/alarmLevel!beforeUpdate.do");
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">故障管理 &gt; 告警级别 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/fault/alarmLevel!findPage.do" method="post">
		<input type="hidden" name="id">
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th width="15%">告警类型ID</th>
						<th width="20%">告警类型名称</th>
						<th width="15%">告警级别</th>
						<th width="45%">描述</th>
						<th width="5%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="alarmtype" items="${pageObject.resultList}">
				    	<td>${alarmtype.typeId}</td>
				    	<td>${alarmtype.alarmTypeZh}</td>
						<td>${alarmtype.alarmLevelZh}</td>
						<td>${alarmtype.description}</td>
				    	<td>
					    	<a href="javascript:edit('${alarmtype.id}')">编辑</a>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="14">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>