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
			$("#pageForm").attr("action","${ctx}/bussiness/recordplan!delete.do");
		}
		$("#pageForm").submit();
	}
	
	function editRecordPlan(id) {
		$("#pageForm").find("[name=oname]").val(id);
		$("#pageForm").attr("action","${ctx}/bussiness/recordplan!beforeUpdate.do");
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 存储方案管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/recordplan!findPage.do" method="post">
		<input type="hidden" name="oname">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">方案名：</td>
					<td>
						<input type="text" name="pageObject.params.name" id="pageObject.params.name" value="${pageObject.params.name}">
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
					<th width="15%"><div>方案名称</div></th>
					<th width="5%">分辨率</th>
					<th width="5%">帧率</th>
					<th width="5%">覆盖天数</th>
					<th width="65%">描述</th>
					<th width="2%">操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="recordPlan" items="${pageObject.resultList}">
					<tr>
						<td class="center">
							<input type="checkbox" name="items" class="items" value="${recordPlan.name}"/>
						</td>
						<td>${recordPlan.name}</td>
						<td>${recordPlan.resolutionName}</td>
						<td>${recordPlan.frametype}</td>
						<td>${recordPlan.cycleDate}</td>
						<td>${recordPlan.description}</td>
						<td>
							<a href="javascript:editRecordPlan('${recordPlan.name}')">编辑</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
				<tr>
					<td colspan="7">
						<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
					</td>
				</tr>
				<tr>
					<td colspan="7" style="text-align: center;vertical-align: top;height: 35px">
						<a class="a_button" href="javascript:location.href='${ctx}/bussiness/recordplan!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
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