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
			alert("请至少选择要删除告警资源。");
			return false;
		}
		msg = "确认要删除所选告警资源？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/fault/alarmRes!delete.do");
		}
		$("#pageForm").submit();
	}
	
	<%--function save(resId,alarmType,name,sid,description) {--%>
	<%--	$("#pageForm").find("[name=resId]").val(resId);--%>
	<%--	$("#pageForm").find("[name=alarmType]").val(alarmType);--%>
	<%--	$("#pageForm").find("[name=name]").val(name);--%>
	<%--	$("#pageForm").find("[name=sid]").val(sid);--%>
	<%--	$("#pageForm").find("[name=description]").val(description);--%>
	<%--	$("#pageForm").attr("action","${ctx}/fault/alarmRes!save.do");--%>
	<%--	$("#pageForm").submit();--%>
	<%--}--%>


	function save() {
		if (!checkedItems()) {
			alert("请至少选择一个告警资源。");
			return false;
		}
		msg = "确认要添加所选告警资源？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/fault/alarmRes!save.do");
		}
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 告警资源 &gt; 添加告警资源 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/fault/alarmRes!findRes.do" method="post">
		<input type="hidden" name="resId">
		<input type="hidden" name="alarmType">
		<input type="hidden" name="name">
		<input type="hidden" name="sid">
		<input type="hidden" name="description">
		
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">设备资源ID：</td>
					<td><input type="text" id="pageObject.params.resId" name="pageObject.params.resId" value="${pageObject.params.resId}"></td>
					<td style="text-align: right">告警资源名称：</td>
					<td><input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}"></td>
					<td>设备类型：
						<select class="validate[required]" name="pageObject.params.type" id="type">
							<option value="">请选择</option>
							<c:forEach var="type" items="${types}">
								<option value="${type.value}" <c:if test="${pageObject.params.type eq type.value}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
					<td>故障类型：
						<select class="validate[required]" name="pageObject.params.alarmType" id="alarmType">
							<option value="">请选择</option>
							<c:forEach var="type" items="${alarmTypes}">
								<option value="${type.value}" <c:if test="${pageObject.params.alarmType eq type.value}">selected</c:if>>${type.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>				
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="10%">设备ID</th>
						<th width="10%">告警类型</th>
						<th width="20%">告警资源名称</th>
						<th width="10%">所属平台ID</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="alarmRes" items="${pageObject.resultList}">
			    	<tr >
						<td class="center">
							<input type="checkbox" name="items" class="items" value="${alarmRes.resId}|${alarmRes.alarmType}|${alarmRes.name}|${alarmRes.sid}|${alarmRes.description}"/>
						</td>
				    	<td>${alarmRes.resId}</td>
						<td>${alarmRes.alarmTypeName}</td>
				    	<td>${alarmRes.name}</td>
						<td>${alarmRes.sid}</td>
				    	<td>
<%--					    	<a href="javascript:save('${alarmRes.resId}','${alarmRes.alarmType}','${alarmRes.name}','${alarmRes.sid}','${alarmRes.description}')">添加</a>--%>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="6">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="6" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:void(0);" onclick="return save();"><span>添加</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a></td>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>