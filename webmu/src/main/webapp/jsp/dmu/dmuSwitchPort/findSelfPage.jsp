<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	function edit(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/dmu/dmuSwitchPort!beforeUpdate.do");
		$("#pageForm").submit();
	}

	function view(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/dmu/dmuSwitchPort!beforeUpdate.do");
		$("#pageForm").submit();
	}
	function beforeSave() {
		$("#pageForm").attr("action","${ctx}/dmu/dmuSwitchPort!beforeSave.do");
		$("#pageForm").submit();
	}
	var isSetFlash = true;
	function checkedItems() {
		var flag = false;
		$("#pageForm").find("[name=items]").each(function() {
			if (this.checked) {
				flag = true;
			}
		});
		return flag;
	}
	function deleteSelect() {
		if (!checkedItems()) {
			alert("请选择要删除的端口信息。");
			return false;
		}
		msg = "确认要删除所选端口信息？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/dmu/dmuSwitchPort!delete.do");
			$("#pageForm").submit();
		}
	}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">网管管理 &gt; 交换机端口查询 </span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/dmu/dmuSwitchPort!findPage.do" method="post">
		<input type="hidden" name="id">
		<input type="hidden" name="serverId" id="serverId" value="${serverId }">

		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="80%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="4%">序号</th>
						<th width="10%"><div>交换机编号</div></th>
						<th width="10%">端口</th>
						<th width="10%">IP地址</th>
						<th width="10%">操作</th>

					</tr>
				</thead>
				<tbody>
				<c:forEach var="data" items="${pageObject.resultList}">
			    	<tr>
						<td class="center">
							<input type="checkbox" name="items" class="items" value="${data.id}"/>
						</td>
				    	<td>${data.id}</td>
						<td>${data.switchid}</td>
				    	<td>${data.port}</td>
						<td>${data.ip}</td>
						<td>
					    	<a href="javascript:edit('${data.id}')">
							编辑</a>
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
						<a class="a_button" href="javascript:void(0);" onclick="return beforeSave();"><span>添加</span></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0);" onclick="return deleteSelect();"><span>删除</span></a></a>&nbsp;&nbsp;
						<a class="a_button" href="${ctx}/dmu/dmuEquipment!findSwitchPage.do"><span>返回</span></a>
					</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>