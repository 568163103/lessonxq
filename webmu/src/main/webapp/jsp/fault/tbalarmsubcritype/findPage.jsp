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
			alert("请至少选择要删除告警配置。");
			return false;
		}
		msg = "确认要删除所选告警配置？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/fault/tbAlarmsubcriType!delete.do");
		}
		$("#pageForm").submit();
	}

	function editServer(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/fault/tbAlarmsubcriType!beforeUpdate.do");
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 告警配置 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/fault/tbAlarmsubcriType!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td>告警类型：
						<select class="validate[required]" name="pageObject.params.alarmType" id="alarmType" value="${pageObject.params.alarmType }">
							<option value="">请选择</option>
							<c:forEach var="type" items="${alarmTypes}">
								<option value="${type.key}" <c:if test="${pageObject.params.alarmType eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
					<td>开始时间：
						<input type="text" onclick="WdatePicker({readOnly:'true',dateFmt:'HH:mm:ss' ,maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate" style="width:145px;" id="beginTime" name="pageObject.params.beginTime" value="${pageObject.params.beginTime }"/>
					</td>
					<td>结束时间：
						<input type="text"  onclick="WdatePicker({readOnly:'true',dateFmt:'HH:mm:ss' ,minDate:'#F{$dp.$D(\'beginTime\')}' })" class="Wdate" style="width:145px;" id="endTime" name="pageObject.params.endTime" value="${pageObject.params.endTime }"/>
					</td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="2%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="10%">告警类型</th>
						<th width="10%">告警布防时间</th>
						<th width="10%">告警撤防时间</th>
						<th width="20%">描述</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="tbAlarmsubcriType" items="${pageObject.resultList}">
			    	<tr >
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${tbAlarmsubcriType.alarmType}"/>
				    	</td>
				    	<td>${tbAlarmsubcriType.alarmTypeZh}</td>
						<td>${tbAlarmsubcriType.beginTime}</td>
				    	<td>${tbAlarmsubcriType.endTime}</td>
						<td>${tbAlarmsubcriType.description}</td>
				    	<td>
					    	<a href="javascript:editServer('${tbAlarmsubcriType.alarmType}')">编辑</a>
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
							<a class="a_button" href="javascript:location.href='${ctx}/fault/tbAlarmsubcriType!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a>&nbsp;&nbsp;
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>