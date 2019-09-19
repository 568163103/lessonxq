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
			alert("请至少选择要删除通道。");
			return false;
		}
		msg = "确认要删除所选通道？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/bussiness/alarmprescheme!delete.do");
		}
		$("#pageForm").submit();
	}
	
	function editAlarmPreScheme(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/bussiness/alarmprescheme!beforeUpdate.do");
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 预案关联管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/alarmprescheme!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">事件类型：</td>
					<td>
						<select name="pageObject.params.alarmType" id="alarmType">
							<option value="">请选择</option>
							<c:forEach var="alarmTypeEntity" items="${alarmTypes}">
								<option value="${alarmTypeEntity.value}" <c:if test="${pageObject.params.alarmType eq alarmTypeEntity.value}">selected</c:if>>${alarmTypeEntity.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="channlePageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
				<tr>
					<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
					<th width="15%"><div>事件源类型</div></th>
					<th width="12%">事件源</th>
					<th width="8%">预案</th>
					<th width="2%">操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="alarmPreScheme" items="${pageObject.resultList}">
					<tr>
						<td class="center">
							<input type="checkbox" name="items" class="items" value="${alarmPreScheme.alarmType},${alarmPreScheme.sourceId},${alarmPreScheme.schemeId}"/>
						</td>
						<td>${alarmPreScheme.alarmTypeName}</td>
						<td>${alarmPreScheme.sourceName}</td>
						<td>${alarmPreScheme.schemeName}</td>
						<td>
							<a href="javascript:editAlarmPreScheme('${alarmPreScheme.alarmType},${alarmPreScheme.sourceId},${alarmPreScheme.schemeId}')">编辑</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
				<tr>
					<td colspan="5">
						<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
					</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;vertical-align: top;height: 35px">
						<a class="a_button" href="javascript:location.href='${ctx}/bussiness/alarmprescheme!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a>
					</td>
				</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>

</body>
</html>